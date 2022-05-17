package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.Member;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/member")
@Slf4j
//@RequiredArgsConstructor
public class MemberController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtServiceImpl jwtService;
	
	
	@PostMapping
	public ResponseEntity<String> register(@RequestBody Member member) throws Exception {
		log.debug("회원가입 member info : {}", member);
		log.debug("registerMember - 호출");
		if(memberService.idCheck(member.getUserid()) == 1) {
			return new ResponseEntity<String>("아이디가 중복입니다.", HttpStatus.NO_CONTENT);
		} else {
			if(memberService.registerMember(member)) {
				return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
	}
	
	@PostMapping("{userid}")
	public ResponseEntity<String> login(@RequestBody Member member, HttpServletRequest request) throws Exception {
		log.debug("로그인시도 member info : {}", member);
		Member loginmember = memberService.login(member);
		log.debug("로그인완료 member : {}", loginmember);
		HttpSession session = request.getSession();
		if(loginmember == null) return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		
//			HttpSession session = request.getSession();
			log.debug("login세션 : {}",session);
			session.setAttribute("loginmember", loginmember);
			log.debug("loginmember 세션 값 : {}" , session.getAttribute("loginmember"));
//			Cookie cookie = new Cookie("loginmember", loginmember.getUserid());
//			cookie.setPath("/");
//			if ("saveok".equals(map.get("idsave"))) {
//				cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
//			} else {
//				cookie.setMaxAge(0);
//			}
//			response.addCookie(cookie);
			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);		
	}
	
	
	@GetMapping("/check")
	public ResponseEntity<String> logincheck(HttpServletRequest request) {
		log.debug("로그인체크 수행");
		HttpSession session = request.getSession(false);
		log.debug("세션 : {}" ,session);
		if(session == null) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		Member loginmember = (Member) session.getAttribute("loginmember");
		
		log.debug("loginmember 세션 : {}" ,loginmember);
		if(loginmember == null) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT); 
		else return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		
	}
	
	@GetMapping("/logout")
	public void logout(HttpServletRequest request) {
		log.debug("로그아웃");
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			log.debug("세션삭제");
		}
	}
	
	

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) Member member) {
		Map<String, Object> resultMap = new HashMap<>();
		log.debug("로그인요청 member : {}" , member);
		HttpStatus status = null;
		try {
			Member loginUser = memberService.login(member);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				log.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			log.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		log.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				Member member = memberService.userInfo(userid);
				resultMap.put("userInfo", member);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
}

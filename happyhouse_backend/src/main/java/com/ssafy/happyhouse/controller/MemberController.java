package com.ssafy.happyhouse.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.Member;
import com.ssafy.happyhouse.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/member")
@Slf4j
//@RequiredArgsConstructor
public class MemberController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	// private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	
	@Autowired
	private MemberService memberService;
	// private final MemberService memberService;
	
	
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
	
}

package com.ssafy.happyhouse.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.Member;
import com.ssafy.happyhouse.model.service.MemberService;

import lombok.RequiredArgsConstructor;
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
//		logger.debug("member info : {}", member);
//		logger.debug("registerMember - 호출");
		if(memberService.registerMember(member)) {
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("{userid}")
	public ResponseEntity<Member> login(@RequestBody Member member, HttpServletRequest request) throws Exception {
		log.debug("로그인 member info : {}", member);
		
		Member loginmember = memberService.login(member);
		if (loginmember != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginmember", loginmember);
			Cookie cookie = new Cookie("ssafy_id", loginmember.getUserid());
//			cookie.setPath("/");
//			if ("saveok".equals(map.get("idsave"))) {
//				cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
//			} else {
//				cookie.setMaxAge(0);
//			}
//			response.addCookie(cookie);
			
			return new ResponseEntity<Member>(loginmember, HttpStatus.OK);
		} else {
			return new ResponseEntity<Member>(loginmember, HttpStatus.NO_CONTENT);
		}
	}
	
}

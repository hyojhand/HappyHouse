package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.Member;

@RestController
//@RequestMapping("/check")
public class HomeController {
	
//	@GetMapping
//	public ResponseEntity<String> logincheck(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		Member loginmember = (Member) session.getAttribute("loginmember");
//		if(loginmember == null) return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT); 
//		else return new ResponseEntity<String>("success",HttpStatus.OK);
//	}

}

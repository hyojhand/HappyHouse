package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.AptSearchCnt;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.Member;
import com.ssafy.happyhouse.model.service.HouseMapService;
import com.ssafy.happyhouse.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MemberService memberService;
	@Autowired
	private HouseMapService houseService;

    @ApiOperation(value = "해당 메시지 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/member")
	public ResponseEntity<List<Member>> listMember() throws Exception {
		log.debug("listMember - 호출");
		return new ResponseEntity<List<Member>>(memberService.listMember(), HttpStatus.OK);
	}
    
    @ApiOperation(value = "해당 메시지 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/member/search/{keyword}")
	public ResponseEntity<List<Member>> searchMember(@PathVariable String keyword) throws Exception {
		log.debug("searchMember - 호출");
		return new ResponseEntity<List<Member>>(memberService.searchMember(keyword), HttpStatus.OK);
	}
    
    @ApiOperation(value = "해당 메시지 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping("/search")
    public ResponseEntity<List<AptSearchCnt>> searchApt() throws Exception {
    	log.debug("searchApt - 호출");
    	return new ResponseEntity<List<AptSearchCnt>>(houseService.searchApt(), HttpStatus.OK);
    }
    
    @ApiOperation(value = "해당 메시지 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping("/bookmark")
    public ResponseEntity<List<HouseInfoDto>> searchBookmark() throws Exception {
    	log.debug("searchBookmark - 호출");
    	return new ResponseEntity<List<HouseInfoDto>>(houseService.getMostBookmark(), HttpStatus.OK);
    }

}
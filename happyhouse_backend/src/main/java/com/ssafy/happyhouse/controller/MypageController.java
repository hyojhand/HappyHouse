package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Message;
import com.ssafy.happyhouse.model.Reply;
import com.ssafy.happyhouse.model.service.MypageService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/mypage")
@Slf4j
public class MypageController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MypageService mypageService;
	
    @ApiOperation(value = "해당 유저가 주고 받은 메시지 정보를 반환한다.", response = List.class)
	@GetMapping("/message/{userid}")
	public ResponseEntity<List<Message>> retrieveMessage(@PathVariable String userid) throws Exception {
		log.debug("retrieveReceiveMessage - 호출");
		return new ResponseEntity<List<Message>>(mypageService.retrieveMessage(userid), HttpStatus.OK);
	}	
    
    @ApiOperation(value = "해당 유저가 쓴 게시글 정보를 반환한다.", response = List.class)
    @GetMapping("/board/{userid}")
    public ResponseEntity<List<BoardDto>> retrieveBoard(@PathVariable String userid) throws Exception {
    	log.debug("retrieveBoard - 호출");
    	return new ResponseEntity<List<BoardDto>>(mypageService.retrieveBoard(userid), HttpStatus.OK);
    }
    
    @ApiOperation(value = "해당 유저가 쓴 댓글 정보를 반환한다.", response = List.class)
    @GetMapping("/reply/{userid}")
    public ResponseEntity<List<Reply>> retrieveReply(@PathVariable String userid) throws Exception {
    	log.debug("retrieveReply - 호출");
    	return new ResponseEntity<List<Reply>>(mypageService.retrieveReply(userid), HttpStatus.OK);
    }
    
    @ApiOperation(value = "해당 유저가 좋아요를 누른 글 정보를 반환한다.", response = List.class)
    @GetMapping("/like/{userid}")
    public ResponseEntity<List<BoardDto>> retrieveLike(@PathVariable String userid) throws Exception {
    	log.debug("retrieveReply - 호출");
    	return new ResponseEntity<List<BoardDto>>(mypageService.retrieveLike(userid), HttpStatus.OK);
    }
    
    @ApiOperation(value = "해당 메시지를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/message")
    public ResponseEntity<String> deleteMessage(@RequestBody Message message) throws Exception {
    	log.debug("deleteMessage - 호출");
    	if (mypageService.deleteMessage(message) == 1) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

}
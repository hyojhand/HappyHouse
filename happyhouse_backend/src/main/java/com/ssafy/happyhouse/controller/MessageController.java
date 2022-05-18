package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.Message;
import com.ssafy.happyhouse.model.Reply;
import com.ssafy.happyhouse.model.service.MessageService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MessageService messageService;

    @ApiOperation(value = "해당 게시글의 모든 댓글 정보를 반환한다.", response = List.class)
	@PostMapping
	public ResponseEntity<String> writeMessage(@RequestBody Message msg) throws Exception {
		log.debug("writeMessage - 호출");
		log.debug(msg.toString());
		if (messageService.writeMessage(msg)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
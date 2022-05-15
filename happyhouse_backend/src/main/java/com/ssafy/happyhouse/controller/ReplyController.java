package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.Reply;
import com.ssafy.happyhouse.model.service.ReplyService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/reply")
@Slf4j
public class ReplyController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ReplyService replyService;

    @ApiOperation(value = "해당 게시글의 모든 댓글 정보를 반환한다.", response = List.class)
	@GetMapping("{articleno}")
	public ResponseEntity<List<Reply>> retrieveReply(@PathVariable int articleno) throws Exception {
		log.debug("retrieveReply - 호출");
		return new ResponseEntity<List<Reply>>(replyService.retrieveReply(articleno), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 댓글 정보를 입력한다. 그리고 DB입력 성공시 모든 댓글 정보를 반환, 실패시 'fail' 문자열을 반환한다.", response = Object.class)
	@PostMapping("{articleno}")
	public ResponseEntity<?> writeReply(@RequestBody Reply reply, @PathVariable int articleno) {
		log.debug("writeReply - 호출");
		if (replyService.writeReply(reply)) {
			return new ResponseEntity<List<Reply>>(replyService.retrieveReply(articleno), HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "댓글 정보를 수정한다. 그리고 DB수정 성공시 모든 댓글 정보를 반환, 실패시 'fail' 문자열을 반환한다.", response = Object.class)
	@PutMapping
	public ResponseEntity<?> updateReply(@RequestBody Reply reply) {
		log.debug("updateReply - 호출");
		if (replyService.updateReply(reply)) {
			return new ResponseEntity<List<Reply>>(replyService.retrieveReply(reply.getArticleno()), HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "댓글 정보를 삭제한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{replyid}")
	public ResponseEntity<?> deleteReply(@PathVariable int replyid) {
		log.debug("deleteReply - 호출");
		if (replyService.deleteReply(replyid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
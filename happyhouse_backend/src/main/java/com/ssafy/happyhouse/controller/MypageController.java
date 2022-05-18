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

    @ApiOperation(value = "해당 유저가 받은 메시지 정보를 반환한다.", response = List.class)
	@GetMapping("/receive/{userid}")
	public ResponseEntity<List<Message>> retrieveReceiveMessage(@PathVariable String userid) throws Exception {
		log.debug("retrieveReceiveMessage - 호출");
		return new ResponseEntity<List<Message>>(mypageService.retrieveReceiveMessage(userid), HttpStatus.OK);
	}
    
    @ApiOperation(value = "해당 유저가 받은 메시지 정보를 반환한다.", response = List.class)
    @GetMapping("/send/{userid}")
    public ResponseEntity<List<Message>> retrieveSendMessage(@PathVariable String userid) throws Exception {
    	log.debug("retrieveSendMessage - 호출");
    	return new ResponseEntity<List<Message>>(mypageService.retrieveSendMessage(userid), HttpStatus.OK);
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

//    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)    
//	@GetMapping("{articleno}")
//	public ResponseEntity<BoardDto> detailBoard(@PathVariable int articleno) {
//		log.debug("detailBoard - 호출");
//		return new ResponseEntity<BoardDto>(boardService.detailBoard(articleno), HttpStatus.OK);
//	}
//
//    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PostMapping
//	public ResponseEntity<String> writeBoard(@RequestBody BoardDto board) {
//		log.debug("writeBoard - 호출");
//		if (boardService.writeBoard(board)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
//
//    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PutMapping("{articleno}")
//	public ResponseEntity<String> updateBoard(@RequestBody BoardDto board) {
//		log.debug("updateBoard - 호출");
//		log.debug("" + board);
//		
//		if (boardService.updateBoard(board)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
//
//    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@DeleteMapping("{articleno}")
//	public ResponseEntity<String> deleteBoard(@PathVariable int articleno) {
//		log.debug("deleteBoard - 호출");
//		if (boardService.deleteBoard(articleno)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
//    
//    @ApiOperation(value = "게시글 좋아요를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@GetMapping("/like/{articleno}")
//	public ResponseEntity<Boolean> isLike(@PathVariable int articleno, HttpSession session) {
//		log.debug("like - 호출");
//		Likey likey = new Likey();
////		likey.setUserid((String)session.getAttribute(""));
//		likey.setUserid("ssafy");
//		likey.setArticleno(articleno);
//		return new ResponseEntity<Boolean>(boardService.isLike(likey), HttpStatus.OK);
//	}
//    
//    @ApiOperation(value = "게시글 좋아요를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//    @PutMapping("/like/{articleno}")
//    public ResponseEntity<String> like(@PathVariable int articleno, HttpSession session) {
//    	log.debug("like - 호출");
//    	Likey likey = new Likey();
////		likey.setUserid((String)session.getAttribute(""));
//    	likey.setUserid("ssafy");
//    	likey.setArticleno(articleno);
//    	if (boardService.like(likey)) {
//    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//    	}
//    	return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//    }
//    
//    @ApiOperation(value = "게시글의 좋아요를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@DeleteMapping("/dislike/{articleno}")
//	public ResponseEntity<String> dislike(@PathVariable int articleno) {
//		log.debug("dislike - 호출");
//		Likey likey = new Likey();
////		likey.setUserid((String)session.getAttribute(""));
//		likey.setUserid("ssafy");
//		likey.setArticleno(articleno);
//		if (boardService.dislike(likey)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
}
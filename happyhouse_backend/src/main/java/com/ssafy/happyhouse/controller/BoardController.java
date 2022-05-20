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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Likey;
import com.ssafy.happyhouse.model.service.BoardService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

//	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;

    @ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list/{type}")
	public ResponseEntity<List<BoardDto>> retrieveBoard(@PathVariable String type) throws Exception {
		log.debug("retrieveBoard - 호출, type : " + type);
		return new ResponseEntity<List<BoardDto>>(boardService.retrieveBoard(type), HttpStatus.OK);
	}
    
    @ApiOperation(value = "해당 키워드를 포함한 모든 게시글의 정보를 반환한다.", response = List.class)
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BoardDto>> retrieveKeywordBoard(@PathVariable String keyword) throws Exception {
    	log.debug("retrieveKeywordBoard - 호출, keyword : " + keyword);
    	return new ResponseEntity<List<BoardDto>>(boardService.retrieveKeywordBoard(keyword), HttpStatus.OK);
    }
    
    @ApiOperation(value = "해당 키워드를 포함한 모든 게시글의 정보를 반환한다.", response = List.class)
    @GetMapping("/writer/{writer}")
    public ResponseEntity<List<BoardDto>> retrieveWriterBoard(@PathVariable String writer) throws Exception {
    	log.debug("retrieveWriterBoard - 호출, writer : " + writer);
    	return new ResponseEntity<List<BoardDto>>(boardService.retrieveWriterBoard(writer), HttpStatus.OK);
    }

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)    
	@GetMapping("{articleno}")
	public ResponseEntity<BoardDto> detailBoard(@PathVariable int articleno) {
		log.debug("detailBoard - 호출");
		return new ResponseEntity<BoardDto>(boardService.detailBoard(articleno), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeBoard(@RequestBody BoardDto board) {
		log.debug("writeBoard - 호출");
		if (boardService.writeBoard(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{articleno}")
	public ResponseEntity<String> updateBoard(@RequestBody BoardDto board) {
		log.debug("updateBoard - 호출");
		log.debug("" + board);
		
		if (boardService.updateBoard(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{articleno}")
	public ResponseEntity<String> deleteBoard(@PathVariable int articleno) {
		log.debug("deleteBoard - 호출");
		if (boardService.deleteBoard(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    @ApiOperation(value = "게시글 좋아요를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = Boolean.class)
	@PostMapping("/like")
	public ResponseEntity<Boolean> isLike(@RequestBody Likey likey) {
		log.debug("like - 호출");
		return new ResponseEntity<Boolean>(boardService.isLike(likey), HttpStatus.OK);
	}
   
    @ApiOperation(value = "게시글 좋아요를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/like")
    public ResponseEntity<String> like(@RequestBody Likey likey) {
    	log.debug("like - 호출");
    	if (boardService.like(likey)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
    
    @ApiOperation(value = "게시글의 좋아요를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/dislike")
	public ResponseEntity<String> dislike(@RequestParam String userid, @RequestParam int articleno) {
		log.debug("dislike - 호출");
		Likey likey = new Likey();
		likey.setUserid(userid);
		likey.setArticleno(articleno);
		if (boardService.dislike(likey)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
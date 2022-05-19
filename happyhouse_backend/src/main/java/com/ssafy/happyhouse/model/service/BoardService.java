package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Likey;

public interface BoardService {
	public List<BoardDto> retrieveBoard(String type);
	public List<BoardDto> retrieveKeywordBoard(String keyword);
	public List<BoardDto> retrieveWriterBoard(String writer);
	public BoardDto detailBoard(int articleno);
	public boolean writeBoard(BoardDto board);
	public boolean updateBoard(BoardDto board);
	public boolean deleteBoard(int articleno);
	public boolean isLike(Likey likey);
	public boolean like(Likey likey);
	public boolean dislike(Likey likey);
}

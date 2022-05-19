package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Likey;

@Mapper
public interface BoardMapper {
 	public List<BoardDto> selectBoardRecent();
	public List<BoardDto> selectBoardLike();
	public List<BoardDto> selectBoardReply();
	public List<BoardDto> selectKeywordBoard(String keyword);
	public List<BoardDto> selectWriterBoard(String writer);
	public BoardDto selectBoardByNo(int articleno);
	public int insertBoard(BoardDto board);
	public int updateBoard(BoardDto board);
	public int deleteBoard(int articleno);
	public Likey isLike(Likey likey);
	public int insertLikey(Likey likey);
	public int addLikey(int articleno);
	public int deleteLikey(Likey likey);
	public int subLikey(int articleno);
}
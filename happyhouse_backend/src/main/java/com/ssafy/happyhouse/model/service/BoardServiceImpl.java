package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Likey;
import com.ssafy.happyhouse.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
    @Autowired
	private BoardMapper boardMapper;

    @Override
	public List<BoardDto> retrieveBoard(String type) {
    	if (type.equals("recent"))
    		return boardMapper.selectBoardRecent();
    	else if (type.equals("like"))
    		return boardMapper.selectBoardLike();
    	else
    		return boardMapper.selectBoardReply();
	}
    
  	@Override
	public boolean writeBoard(BoardDto board) {
		return boardMapper.insertBoard(board) == 1;
	}

	@Override
	public BoardDto detailBoard(int articleno) {
		return boardMapper.selectBoardByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateBoard(BoardDto board) {
		return boardMapper.updateBoard(board) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBoard(int articleno) {
		return boardMapper.deleteBoard(articleno) == 1;
	}
	
	@Override
	public boolean isLike(Likey likey) {
		return boardMapper.isLike(likey) != null;
	}

	@Override
	public boolean like(Likey likey) {
		if (boardMapper.insertLikey(likey) == 1) {
			boardMapper.addLikey(likey.getArticleno());
			return true;
		}
		return false;
	}

	@Override
	public boolean dislike(Likey likey) {
		if (boardMapper.deleteLikey(likey) == 1) {
			boardMapper.subLikey(likey.getArticleno());
			return true;
		}
		return false;
	}

}

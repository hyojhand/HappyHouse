package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Message;
import com.ssafy.happyhouse.model.Reply;
import com.ssafy.happyhouse.model.mapper.MypageMapper;

@Service
public class MypageServiceImpl implements MypageService {
	
    @Autowired
	private MypageMapper mypageMapper;

    @Override
	public List<Message> retrieveMessage(String userid) {
    	return mypageMapper.selectMessage(userid);
	}
    
    @Override
    public List<BoardDto> retrieveBoard(String userid) {
    	return mypageMapper.selectBoard(userid);
    }
    
    @Override
    public List<Reply> retrieveReply(String userid) {
    	return mypageMapper.selectReply(userid);
    }
    
    @Override
    public List<BoardDto> retrieveLike(String userid) {
    	return mypageMapper.selectLike(userid);
    }

}

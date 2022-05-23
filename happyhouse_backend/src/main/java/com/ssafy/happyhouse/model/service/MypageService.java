package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Message;
import com.ssafy.happyhouse.model.Reply;

public interface MypageService {
	
	public List<Message> retrieveMessage(String userid);
	public List<BoardDto> retrieveBoard(String userid);
	public List<Reply> retrieveReply(String userid);
	public List<BoardDto> retrieveLike(String userid);
	public int deleteMessage(Message message);
	
}

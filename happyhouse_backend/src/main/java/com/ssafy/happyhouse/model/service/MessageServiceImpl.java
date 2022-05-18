package com.ssafy.happyhouse.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.Message;
import com.ssafy.happyhouse.model.mapper.MessageMapper;

@Service
public class MessageServiceImpl implements MessageService {
	
    @Autowired
	private MessageMapper messageMapper;

  	@Override
	public boolean writeMessage(Message msg) {
		return messageMapper.insertMessage(msg) == 1;
	}

}

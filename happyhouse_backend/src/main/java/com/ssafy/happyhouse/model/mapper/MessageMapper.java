package com.ssafy.happyhouse.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.Message;

@Mapper
public interface MessageMapper {
	public int insertMessage(Message msg);
	public Message selectSendMessage(Message msg);
	public Message selectReceiveMessage(Message msg);
	public int deleteMessage(Message msg);
}
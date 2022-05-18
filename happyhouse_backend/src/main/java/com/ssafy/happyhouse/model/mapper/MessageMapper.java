package com.ssafy.happyhouse.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.Message;

@Mapper
public interface MessageMapper {
	public int insertMessage(Message msg);
}
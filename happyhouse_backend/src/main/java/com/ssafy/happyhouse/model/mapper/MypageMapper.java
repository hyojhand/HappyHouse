package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Message;
import com.ssafy.happyhouse.model.Reply;

@Mapper
public interface MypageMapper {
 	public List<Message> selectMessage(String userid);
 	public List<BoardDto> selectBoard(String userid);
 	public List<Reply> selectReply(String userid);
 	public List<BoardDto> selectLike(String userid);
}
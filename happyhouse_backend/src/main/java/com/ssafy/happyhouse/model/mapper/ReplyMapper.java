package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.Reply;

@Mapper
public interface ReplyMapper {
	public List<Reply> selectReply(int articleno);
	public int insertReply(Reply reply);
	public int updateReply(Reply reply);
	public int deleteReply(int replyid);
	public int findArticleByReply(int replyid);
}
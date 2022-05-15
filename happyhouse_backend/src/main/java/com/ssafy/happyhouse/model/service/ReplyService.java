package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Reply;

public interface ReplyService {
	public List<Reply> retrieveReply(int articleno);
	public boolean writeReply(Reply reply);
	public boolean updateReply(Reply reply);
	public boolean deleteReply(int replyid);
	public int findArticleByReply(int replyid);
}

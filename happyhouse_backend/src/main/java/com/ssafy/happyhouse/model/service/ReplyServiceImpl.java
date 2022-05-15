package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.Reply;
import com.ssafy.happyhouse.model.mapper.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService {
	
    @Autowired
	private ReplyMapper replyMapper;

    @Override
	public List<Reply> retrieveReply(int articleno) {
		return replyMapper.selectReply(articleno);
	}
    
  	@Override
	public boolean writeReply(Reply reply) {
		return replyMapper.insertReply(reply) == 1;
	}

	@Override
	@Transactional
	public boolean updateReply(Reply reply) {
		return replyMapper.updateReply(reply) == 1;
	}

	@Override
	@Transactional
	public boolean deleteReply(int replyid) {
		return replyMapper.deleteReply(replyid) == 1;
	}

	@Override
	public int findArticleByReply(int replyid) {
		return replyMapper.findArticleByReply(replyid);
	}

}

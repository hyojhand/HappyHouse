package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.Member;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public boolean registerMember(Member member) throws Exception {
		return memberMapper.registerMember(member) == 1;
	}
	
	@Override
	public Member login(Member member) throws Exception {
		return memberMapper.login(member);
	}
	
	@Override
	public Member userInfo(String userid) throws Exception {
		return memberMapper.userInfo(userid);
	}
	
	
	@Override
	public int idCheck(String checkId) throws Exception {
		return memberMapper.idCheck(checkId); // 0 or 1
	}
	
	@Override
	@Transactional
	public boolean updateMember(Member member) throws Exception {
		return memberMapper.updateMember(member) == 1;
	}

	@Override
	@Transactional
	public boolean deleteMember(String userId) throws Exception {
		return memberMapper.deleteMember(userId) == 1;
	}
	
	
	@Override
	public List<Member> listMember() throws Exception {
		return memberMapper.listMember();
	}

	@Override
	public List<Member> searchMember(String keyword) throws Exception {
		return memberMapper.searchMember(keyword);
	}
	
}

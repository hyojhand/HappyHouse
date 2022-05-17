package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.Member;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public boolean registerMember(Member member) {
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
	public List<Member> listMember() throws Exception {
		return memberMapper.listMember();
	}

	@Override
	public Member getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}

	@Override
	public void updateMember(Member member) throws Exception {
		memberMapper.updateMember(member);
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
	}
	
	 

}

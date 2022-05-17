package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.Member;

public interface MemberService {

	
	public boolean registerMember(Member member);
	Member login(Member member) throws Exception;
	Member userInfo(String userid) throws Exception;
	int idCheck(String checkId) throws Exception;
	
	
	
	List<Member> listMember() throws Exception;
	Member getMember(String userId) throws Exception;
	void updateMember(Member member) throws Exception;
	void deleteMember(String userId) throws Exception;
	
}

package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.Member;

public interface MemberService {

	
	public boolean registerMember(Member member) throws Exception;
	Member login(Member member) throws Exception;
	Member userInfo(String userid) throws Exception;
	int idCheck(String checkId) throws Exception;
	public boolean updateMember(Member member) throws Exception;
	public boolean deleteMember(String userId) throws Exception;
	public List<Member> listMember() throws Exception;
	public List<Member> searchMember(String keyword) throws Exception;
	
	
}

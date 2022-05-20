package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.Member;

@Mapper
public interface MemberMapper {
	
	public int registerMember(Member member) throws Exception;
	Member login(Member member) throws Exception;
	Member userInfo(String userid) throws Exception;
	int idCheck(String checkId) throws Exception;
	public int updateMember(Member member) throws Exception;
	public int deleteMember(String userId) throws Exception;
	public List<Member> listMember() throws Exception;
	public List<Member> searchMember(String keyword) throws Exception;
	
}

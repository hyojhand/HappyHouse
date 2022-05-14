package com.ssafy.happyhouse.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Member {
	
	private String userid;
	private String username;
	private String userpwd;
	private String email;
	private String joindate;
	private int isadmin;
	

}

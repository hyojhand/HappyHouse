package com.ssafy.happyhouse.model;

import lombok.Data;

@Data
public class Message {
	
	private int messageid;
	private String send;
	private String receive;
	private String title;
	private String content;
	private String regtime;
	private int isdeleted;

}

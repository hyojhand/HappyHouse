package com.ssafy.happyhouse.model;

import lombok.Data;

@Data
public class Reply {

	private int replyid;
	private int articleno;
	private String writer;
	private String content;
	private String regtime;
	private int isdeleted;

}

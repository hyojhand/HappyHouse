package com.ssafy.happyhouse.model;

import lombok.Data;

@Data
public class BoardDto {
	
	private int articleno;
	private String writer;
	private String title;
	private String content;
	private int likey;
	private String regtime;
	private int isdeleted;

}

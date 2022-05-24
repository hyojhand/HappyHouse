package com.ssafy.happyhouse.model;

import lombok.Data;

@Data
public class HouseDetail {
	
	private long aptCode;
	private String apartmentName;
	private String dongCode;
	private String dong;
	private String sidoName;
	private String gugunName;
	private int buildYear;
	private String recentPrice;
	private int recentYear;
	private int recentMonth;
	private int recentDay;
	private int area;
	private int floor;
	
	private String lat;
	private String lng;
	

}

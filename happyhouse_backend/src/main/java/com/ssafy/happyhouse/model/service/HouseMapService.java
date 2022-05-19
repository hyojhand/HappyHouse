package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.HouseDetail;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	HouseInfoDto getAptInfo(String aptcode) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseDetail> getAptDetail(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailDesc(String aptcode) throws SQLException;
	
	List<HouseDetail> getAptDetailYearSort(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailYearSortDesc(String aptcode) throws SQLException;
	
	List<HouseDetail> getAptDetailPriceSort(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailPriceSortDesc(String aptcode) throws SQLException;
	
	List<HouseDetail> getAptDetailAreaSort(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailAreaSortDesc(String aptcode) throws SQLException;
	
	List<HouseInfoDto> getAptWord(String word) throws Exception;
	
}

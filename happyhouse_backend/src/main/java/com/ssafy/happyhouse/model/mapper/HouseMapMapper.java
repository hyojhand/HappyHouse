package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.AptSearchCnt;
import com.ssafy.happyhouse.model.HouseDetail;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	HouseInfoDto getAptInfo(String aptcode) throws SQLException;
	int addSearchCnt(String aptcode) throws SQLException;
	List<AptSearchCnt> searchApt() throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	List<HouseDetail> getAptDetail(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailDesc(String aptcode) throws SQLException;
	
	List<HouseDetail> getAptDetailYearSort(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailYearSortDesc(String aptcode) throws SQLException;
	
	List<HouseDetail> getAptDetailPriceSort(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailPriceSortDesc(String aptcode) throws SQLException;
	
	List<HouseDetail> getAptDetailAreaSort(String aptcode) throws SQLException;
	List<HouseDetail> getAptDetailAreaSortDesc(String aptcode) throws SQLException;
	
	
	List<HouseInfoDto> getAptWord(String word) throws SQLException;
	
}

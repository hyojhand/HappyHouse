package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.AptSearchCnt;
import com.ssafy.happyhouse.model.Bookmark;
import com.ssafy.happyhouse.model.HouseDetail;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	HouseInfoDto getAptInfo(String aptcode) throws SQLException;
	int findSearchCnt(@Param("userid") String userid, @Param("aptcode") String aptcode) throws SQLException;
	int insertSearchCnt(@Param("userid") String userid, @Param("aptcode") String aptcode) throws SQLException;
	int addSearchCnt(@Param("userid") String userid, @Param("aptcode") String aptcode) throws SQLException;
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
	int insertBookmark(Bookmark bookmark) throws SQLException;
	int deleteBookmark(Bookmark bookmark) throws SQLException;
	List<Bookmark> selectBookmark(Bookmark bookmark) throws SQLException;
	List<HouseDetail> getBookmark(String userid) throws SQLException;
	List<HouseInfoDto> getMostBookmark() throws SQLException;
	String getMostAddress(String userid) throws SQLException;
	
}

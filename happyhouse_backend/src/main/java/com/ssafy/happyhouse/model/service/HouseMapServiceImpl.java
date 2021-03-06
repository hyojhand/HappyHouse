package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.AptSearchCnt;
import com.ssafy.happyhouse.model.Bookmark;
import com.ssafy.happyhouse.model.HouseDetail;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}
	
	@Override
	public HouseInfoDto getAptInfo(String aptcode) throws Exception {
		return houseMapMapper.getAptInfo(aptcode);
	}
	
	@Override
	public void addSearchCnt(String userid, String aptcode) throws Exception {
		if (houseMapMapper.findSearchCnt(userid, aptcode) == 0) {
			houseMapMapper.insertSearchCnt(userid, aptcode);
		} else {
			houseMapMapper.addSearchCnt(userid, aptcode);
		}
	}
	
	@Override
	public List<AptSearchCnt> searchApt() throws Exception {
		return houseMapMapper.searchApt();
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return houseMapMapper.getAptInDong(dong);
	}
	
	@Override
	public List<HouseDetail> getAptDetail(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetail(aptcode);
	}
	
	@Override
	public List<HouseDetail> getAptDetailDesc(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailDesc(aptcode);
	}

	@Override
	public List<HouseDetail> getAptDetailYearSort(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailYearSort(aptcode);
	}
	
	@Override
	public List<HouseDetail> getAptDetailYearSortDesc(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailYearSortDesc(aptcode);
	}

	@Override
	public List<HouseDetail> getAptDetailPriceSort(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailPriceSort(aptcode);
	}
	
	@Override
	public List<HouseDetail> getAptDetailPriceSortDesc(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailPriceSortDesc(aptcode);
	}
	
	@Override
	public List<HouseDetail> getAptDetailAreaSort(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailAreaSort(aptcode);
	}
	
	@Override
	public List<HouseDetail> getAptDetailAreaSortDesc(String aptcode) throws SQLException {
		return houseMapMapper.getAptDetailAreaSortDesc(aptcode);
	}
	

	@Override
	public int bookmark(Bookmark bookmark) throws Exception {
		return houseMapMapper.insertBookmark(bookmark);
	}
	
	@Override
	public int subBookmark(Bookmark bookmark) throws Exception {
		return houseMapMapper.deleteBookmark(bookmark);
	}
	
	@Override
	public boolean isBookmark(Bookmark bookmark) throws Exception {
		return houseMapMapper.selectBookmark(bookmark).size() == 1;
	}
	
	@Override
	public List<HouseDetail> retrieveBookmark(String userid) throws Exception {
		return houseMapMapper.getBookmark(userid);
	}

	@Override
	public List<HouseInfoDto> getMostBookmark() throws Exception {
		return houseMapMapper.getMostBookmark();
	}

	@Override
	public AptSearchCnt getMostAddress(String userid) throws Exception {
		return houseMapMapper.getMostAddress(userid);
	}

}

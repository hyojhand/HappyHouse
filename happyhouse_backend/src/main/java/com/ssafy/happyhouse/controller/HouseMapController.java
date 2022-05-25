package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.AptSearchCnt;
import com.ssafy.happyhouse.model.Bookmark;
import com.ssafy.happyhouse.model.HouseDetail;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HouseMapService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/map")
@Slf4j
public class HouseMapController {

	@Autowired
	private HouseMapService HouseMapService;
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@ApiOperation(value = "모든 시/도 정보를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		log.debug("sido : {}", HouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunCodeDto>>(HouseMapService.getSido(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 시/도에 있는 모든 구/군 정보를 반환한다.", response = List.class)
	@GetMapping("/gugun/{sido}")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@PathVariable String sido) throws Exception {
		log.debug("gugun : {}", HouseMapService.getGugunInSido(sido));
		return new ResponseEntity<List<SidoGugunCodeDto>>(HouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 구/군에 있는 모든 동 정보를 반환한다.", response = List.class)
	@GetMapping("/dong/{gugun}")
	public ResponseEntity<List<HouseInfoDto>> dong(@PathVariable String gugun) throws Exception {
		log.debug("dong : {}", HouseMapService.getDongInGugun(gugun));
		return new ResponseEntity<List<HouseInfoDto>>(HouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@ApiOperation(value = "특정 아파트의 정보를 반환한다. ", response = HouseInfoDto.class)
	@GetMapping("/aptinfo/{aptcode}")
	public ResponseEntity<HouseInfoDto> aptinfo(@PathVariable String aptcode) throws Exception {
		log.debug("aptinfo : {}", HouseMapService.getAptInfo(aptcode));
		return new ResponseEntity<HouseInfoDto>(HouseMapService.getAptInfo(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 아파트의 검색 횟수를 늘린다.")
	@PostMapping("/aptcnt")
	public ResponseEntity<Void> addSearch(@RequestBody AptSearchCnt searchcnt) throws Exception {
		log.debug("addSearch - 호출, aptcode : " + searchcnt);
		HouseMapService.addSearchCnt(searchcnt.getUserid(), searchcnt.getDongCode());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "특정 유저가 가장 많이 검색한 지역명을 반환한다.", response = String.class)
	@PostMapping("/apt/{userid}")
	public ResponseEntity<String> retrieveMostAddress(@PathVariable String userid) throws Exception {
		log.debug("retrieveMostAddress - 호출, userid : " + userid);
		return new ResponseEntity<String>(HouseMapService.getMostAddress(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 동의 모든 아파트를 반환한다.", response = List.class)
	@GetMapping("/apt/{aptcode}")
	public ResponseEntity<List<HouseInfoDto>> apt(@PathVariable String aptcode) throws Exception {
		log.debug("apt : {}", HouseMapService.getAptInDong(aptcode));
		return new ResponseEntity<List<HouseInfoDto>>(HouseMapService.getAptInDong(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "이름순으로 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetail/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetail(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail : {}", HouseMapService.getAptDetail(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetail(aptcode), HttpStatus.OK);
	}
	
	@GetMapping("/aptsort/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptsort(@PathVariable String aptcode, @RequestParam boolean order, @RequestParam String kind) throws Exception {
		List<HouseDetail> list;
		switch(kind) {
		case "이름":
			if(order) {
				list = HouseMapService.getAptDetailDesc(aptcode);				
			} else {
				list = HouseMapService.getAptDetail(aptcode);
			}
			break;
		case "건축년도":
			if(order) {
				list = HouseMapService.getAptDetailYearSortDesc(aptcode);				
			} else {
				list = HouseMapService.getAptDetailYearSort(aptcode);
			}
			break;
		case "거래가":
			if(order) {
				list = HouseMapService.getAptDetailPriceSortDesc(aptcode);				
			} else {
				list = HouseMapService.getAptDetailPriceSort(aptcode);
			}
			break;
		default:
			if(order) {
				list = HouseMapService.getAptDetailAreaSortDesc(aptcode);				
			} else {
				list = HouseMapService.getAptDetailAreaSort(aptcode);
			}
			break;
		}
		return new ResponseEntity<List<HouseDetail>>(list, HttpStatus.OK);
	}

//	@ApiOperation(value = "이름순으로 내림차순 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetailDesc/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailDesc(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 내림차순 : {}", HouseMapService.getAptDetailDesc(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailDesc(aptcode), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "건축년도 순으로 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetail/year/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailYearSort(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 건축년도순 정렬 : {}", HouseMapService.getAptDetailYearSort(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailYearSort(aptcode), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "건축년도 내림차순으로 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetailDesc/year/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailYearSortDesc(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 건축년도 내림차순 정렬 : {}", HouseMapService.getAptDetailYearSortDesc(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailYearSortDesc(aptcode), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "거래가 순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetail/price/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailPriceSort(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 거래가순 정렬 : {}", HouseMapService.getAptDetailPriceSort(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailPriceSort(aptcode), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "거래가 내림차순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetailDesc/price/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailPriceSortDesc(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 거래가 내림차순 정렬 : {}", HouseMapService.getAptDetailPriceSortDesc(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailPriceSortDesc(aptcode), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "평수 크기순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetail/area/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailAreaSort(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 평수 크기순 정렬 : {}", HouseMapService.getAptDetailAreaSort(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailAreaSort(aptcode), HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "평수 크기 내림차순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
//	@GetMapping("/aptdetailDesc/area/{aptcode}")
//	public ResponseEntity<List<HouseDetail>> aptdetailAreaSortDesc(@PathVariable String aptcode) throws Exception {
//		log.debug("aptdetail 평수 크기 내림차순 정렬 : {}", HouseMapService.getAptDetailAreaSortDesc(aptcode));
//		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailAreaSortDesc(aptcode), HttpStatus.OK);
//	}
	
	@ApiOperation(value = "해당 유저가 특정 아파트를 북마크했는지 여부를 반환한다.", response = Boolean.class)
	@GetMapping("/bookmark")
	public ResponseEntity<Boolean> isBookmark(@RequestParam String userid, @RequestParam long aptCode) throws Exception {
		return new ResponseEntity<Boolean>(HouseMapService.isBookmark(new Bookmark(userid, aptCode)), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 유저가 북마크한 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/bookmark")
	public ResponseEntity<String> addBookmark(@RequestBody Bookmark bookmark) throws Exception {
		if (HouseMapService.bookmark(bookmark) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "해당 유저의 특정 북마크를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/bookmark")
	public ResponseEntity<String> subBookmark(@RequestParam String userid, @RequestParam long aptCode) throws Exception {
		if (HouseMapService.subBookmark(new Bookmark(userid, aptCode)) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "해당 유저가 북마크한 모든 아파트 정보를 반환한다.", response = List.class)
	@GetMapping("/bookmark/list/{userid}")
	public ResponseEntity<List<HouseDetail>> retrieveBookmark(@PathVariable String userid) throws Exception {
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.retrieveBookmark(userid), HttpStatus.OK);
	}
	
}

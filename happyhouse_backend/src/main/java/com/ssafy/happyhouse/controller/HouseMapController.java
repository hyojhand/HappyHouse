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

import com.ssafy.happyhouse.model.Bookmark;
import com.ssafy.happyhouse.model.HouseDetail;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HouseMapService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/map")
@CrossOrigin("*")
@Slf4j
public class HouseMapController {

	@Autowired
	private HouseMapService HouseMapService;
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		log.debug("sido : {}", HouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunCodeDto>>(HouseMapService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun/{sido}")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@PathVariable String sido) throws Exception {
		log.debug("gugun : {}", HouseMapService.getGugunInSido(sido));
		return new ResponseEntity<List<SidoGugunCodeDto>>(HouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong/{gugun}")
	public ResponseEntity<List<HouseInfoDto>> dong(@PathVariable String gugun) throws Exception {
		log.debug("dong : {}", HouseMapService.getDongInGugun(gugun));
		return new ResponseEntity<List<HouseInfoDto>>(HouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색한 아파트의 정보를 반환한다. ", response = HouseInfoDto.class)
	@GetMapping("/aptinfo/{aptcode}")
	public ResponseEntity<HouseInfoDto> aptinfo(@PathVariable String aptcode) throws Exception {
		log.debug("aptinfo : {}", HouseMapService.getAptInfo(aptcode));
		return new ResponseEntity<HouseInfoDto>(HouseMapService.getAptInfo(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 아파트의 검색 횟수를 늘린다.")
	@PostMapping("/aptcnt/{aptcode}")
	public ResponseEntity<Void> addSearch(@PathVariable String aptcode) throws Exception {
		log.debug("addSearch - 호출, aptcode : " + aptcode);
		HouseMapService.addSearchCnt(aptcode);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
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
	
	@ApiOperation(value = "이름순으로 내림차순 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetailDesc/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailDesc(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 내림차순 : {}", HouseMapService.getAptDetailDesc(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailDesc(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "건축년도 순으로 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetail/year/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailYearSort(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 건축년도순 정렬 : {}", HouseMapService.getAptDetailYearSort(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailYearSort(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "건축년도 내림차순으로 정렬한 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetailDesc/year/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailYearSortDesc(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 건축년도 내림차순 정렬 : {}", HouseMapService.getAptDetailYearSortDesc(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailYearSortDesc(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "거래가 순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetail/price/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailPriceSort(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 거래가순 정렬 : {}", HouseMapService.getAptDetailPriceSort(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailPriceSort(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "거래가 내림차순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetailDesc/price/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailPriceSortDesc(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 거래가 내림차순 정렬 : {}", HouseMapService.getAptDetailPriceSortDesc(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailPriceSortDesc(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "평수 크기순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetail/area/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailAreaSort(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 평수 크기순 정렬 : {}", HouseMapService.getAptDetailAreaSort(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailAreaSort(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "평수 크기 내림차순으로 아파트의 정보를 반환한다. ", response = HouseDetail.class)
	@GetMapping("/aptdetailDesc/area/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetailAreaSortDesc(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail 평수 크기 내림차순 정렬 : {}", HouseMapService.getAptDetailAreaSortDesc(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetailAreaSortDesc(aptcode), HttpStatus.OK);
	}
	
	@GetMapping("/word")
	public ResponseEntity<List<HouseInfoDto>> word(@RequestParam("word") String word) throws Exception {
//		System.out.println("word 실행");
		return new ResponseEntity<List<HouseInfoDto>>(HouseMapService.getAptWord(word), HttpStatus.OK);
	}
	
	@GetMapping("/bookmark")
	public ResponseEntity<Boolean> isBookmark(@RequestParam String userid, @RequestParam long aptCode) throws Exception {
		return new ResponseEntity<Boolean>(HouseMapService.isBookmark(new Bookmark(userid, aptCode)), HttpStatus.OK);
	}
	
	@PostMapping("/bookmark")
	public ResponseEntity<String> addBookmark(@RequestBody Bookmark bookmark) throws Exception {
		if (HouseMapService.bookmark(bookmark) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/bookmark")
	public ResponseEntity<String> subBookmark(@RequestParam String userid, @RequestParam long aptCode) throws Exception {
		if (HouseMapService.subBookmark(new Bookmark(userid, aptCode)) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/bookmark/list/{userid}")
	public ResponseEntity<List<HouseDetail>> retrieveBookmark(@PathVariable String userid) throws Exception {
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.retrieveBookmark(userid), HttpStatus.OK);
	}
	
}

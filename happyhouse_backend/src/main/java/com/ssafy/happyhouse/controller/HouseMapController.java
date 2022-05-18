package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/apt/{aptcode}")
	public ResponseEntity<List<HouseInfoDto>> apt(@PathVariable String aptcode) throws Exception {
		log.debug("apt : {}", HouseMapService.getAptInDong(aptcode));
		return new ResponseEntity<List<HouseInfoDto>>(HouseMapService.getAptInDong(aptcode), HttpStatus.OK);
	}
	
	@GetMapping("/aptdetail/{aptcode}")
	public ResponseEntity<List<HouseDetail>> aptdetail(@PathVariable String aptcode) throws Exception {
		log.debug("aptdetail : {}", HouseMapService.getAptDetail(aptcode));
		return new ResponseEntity<List<HouseDetail>>(HouseMapService.getAptDetail(aptcode), HttpStatus.OK);
	}
	
	@GetMapping("/word")
	public ResponseEntity<List<HouseInfoDto>> word(@RequestParam("word") String word) throws Exception {
//		System.out.println("word 실행");
		return new ResponseEntity<List<HouseInfoDto>>(HouseMapService.getAptWord(word), HttpStatus.OK);
	}
	
}

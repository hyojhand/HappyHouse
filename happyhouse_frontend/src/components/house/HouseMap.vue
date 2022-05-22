<template>
  <div>
    <h3 class="map-title" style="z-index: 80">
      <b-icon icon="house-fill"></b-icon> {{ this.selectArea.sidoName }}
      {{ this.selectArea.gugunName }}
      {{ this.selectArea.dongName }}
      아파트 목록입니다.
    </h3>
    <b-row class="map-tool">
      <b-col>
        <b-form-group v-slot="{ ariaDescribedby }">
          <b-form-checkbox-group
            style="color: black; font-weight: bold"
            @change="selectCategory"
            v-model="selected"
            :options="options"
            :aria-describedby="ariaDescribedby"
            switches
            stacked
            size="lg"
          ></b-form-checkbox-group>
        </b-form-group>
        <p class="map-detail" role="button" @click="moveList()">
          아파트 상세정보 <b-icon icon="cursor"></b-icon>
        </p>
        <p class="map-detail" role="button" @click="moveSearch()">
          다시 검색 <b-icon icon="search"></b-icon>
        </p>
      </b-col>
      <b-col> </b-col>
    </b-row>

    <div id="map"></div>
    <br />
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";

export default {
  name: "HouseMap",
  data() {
    return {
      map: null,
      markers: [], // 마커를 담을 배열입니다
      markerPositions: [],
      // placeOverlay: "", // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이
      // contentNode: "", // 커스텀 오버레이의 컨텐츠 엘리먼트
      // currCategory: "", // 현재 선택된 카테고리를 가지고 있을 변수
      ps: "", // 장소 검색 객체
      infowindow: "", // 인포윈도우 객체
      categoryMarkers: [], // 선택한 카테고리 마커 담을 배열

      selected: [], // Must be an array reference!
      options: [
        { text: "Cafe", value: "cafe" },
        { text: "Bank", value: "bank" },
        { text: "Education", value: "education" },
        { text: "Hospital", value: "hospital" },
      ],
    };
  },
  computed: {
    ...mapState("houseStore", ["selectDong", "selectArea"]),
  },
  async created() {
    // 아파트 좌표 리스트 받아오기
    await http.get(`/map/apt/${this.selectDong}`).then(({ data }) => {
      this.markerPositions = data;
    });

    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      const MAP_API_KEY = process.env.VUE_APP_MAP_API_KEY;
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${MAP_API_KEY}&libraries=services`;
      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
      });
      document.head.appendChild(script);
    } else {
      this.initMap();
    }
  },
  methods: {
    async initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 8,
      };

      // 지도 생성
      this.map = new kakao.maps.Map(container, options);

      // // 인포윈도우 객체 생성
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      await this.displayMarkers();
    },
    displayMarkers() {
      var bounds = new kakao.maps.LatLngBounds();

      // 1. 표시되있는 marker있으면 map에서 삭제
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }

      // 1-2. 표시되어있는 카테고리 marker 삭제
      if (this.categoryMarkers.length > 0) {
        this.categoryMarkers.forEach((item) => {
          item.setMap(null);
        });
        this.categoryMarkers = [];
      }

      // 2. 아파트 마커의 개수만큼 아파트 마커 표시
      for (var i = 0; i < this.markerPositions.length; i++) {
        // 2-1. 마커 이미지 세팅
        var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png";
        var imageSize = new kakao.maps.Size(36, 37);
        var imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, i * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        };
        var markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        );

        // 2-2. 마커 포지션 좌표 세팅
        var markerPosition = new kakao.maps.LatLng(
          this.markerPositions[i].lat,
          this.markerPositions[i].lng
        );

        // 3. 마커 좌표, 이미지 세팅
        var marker = new kakao.maps.Marker({
          map: this.map,
          position: markerPosition,
          image: markerImage,
        });
        this.markers.push(marker);
        marker.setMap(this.map);

        // // 마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우
        // var iwContent = '<div style="padding:5px;">Hello World!</div>';
        // // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

        // // 인포윈도우를 생성
        // var infowindow = new kakao.maps.InfoWindow({
        //   content: iwContent,
        // });

        // // 마커에 마우스오버 이벤트 등록 후, 마커위에 인포윈도우 표시
        // kakao.maps.event.addListener(marker, "mouseover", function () {
        //   infowindow.open(this.map, marker);
        // });

        // // 마커에 마우스아웃 이벤트 등록 후, 인포윈도우 제거
        // kakao.maps.event.addListener(marker, "mouseout", function () {
        //   infowindow.close();
        // });

        // 4. 범위 재설정을 위해 LatLngBounds 객체에 좌표를 추가해서
        bounds.extend(markerPosition);
      }

      // 5. 검색된 위치들 기준으로 지도 범위 재설정
      this.map.setBounds(bounds);
    },
    // 카테고리 키워드 검색 후 마커찍는 메서드
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        for (var i = 0; i < data.length; i++) {
          var place = data[i];
          this.categoryMarkers = new kakao.maps.Marker({
            // new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(place.y, place.x),
          });

          // 인포윈도우
          // 마커에 클릭이벤트를 등록합니다
          kakao.maps.event.addListener(
            this.categoryMarkers,
            "click",
            function () {
              // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
              this.infowindow.setContent(
                '<div style="padding:5px;font-size:12px;">' +
                  place.place_name +
                  "</div>"
              );
              this.infowindow.open(this.map, this.categoryMarkers);
            }
          );
        }
      }
    },
    selectCategory() {
      if (this.categoryMarkers.length > 0) {
        this.categoryMarkers.forEach((item) => {
          item.setMap(null);
        });
        this.categoryMarkers = null;
      }

      console.log(this.selected);
      console.log(this.selected.length);
      console.log(this.categoryMarkers);

      // 장소 검색 객체
      this.ps = new kakao.maps.services.Places(this.map);

      for (var k = 0; k < this.selected.length; k++) {
        if (this.selected[k] == "cafe") {
          // 카페
          this.ps.categorySearch("CE7", this.placesSearchCB, {
            useMapBounds: true,
          });
        } else if (this.selected[k] == "bank") {
          // 은행
          this.ps.categorySearch("BK9", this.placesSearchCB, {
            useMapBounds: true,
          });
        } else if (this.selected[k] == "education") {
          //학교, 학원, 어린이집/유치원
          this.ps.categorySearch("SC4", this.placesSearchCB, {
            useMapBounds: true,
          });
          this.ps.categorySearch("AC5", this.placesSearchCB, {
            useMapBounds: true,
          });
          this.ps.categorySearch("PS3", this.placesSearchCB, {
            useMapBounds: true,
          });
        } else if (this.selected[k] == "hospital") {
          // 병원, 약국
          this.ps.categorySearch("HP8", this.placesSearchCB, {
            useMapBounds: true,
          });
          this.ps.categorySearch("PM9", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
      }

      /** 고민할것 */
      // CS2 편의점
      // CT1 문화시설

      // MT1 대형마트
      // PK6 주차장
      // OL7 주유소, 충전소
      // SW8 지하철역
      // AG2 중개업소
      // PO3 공공기관
      // AT4 관광명소
      // AD5 숙박
      // FD6 음식점
    },

    moveList() {
      this.$router.push({ name: "HouseList" });
    },
    moveSearch() {
      this.$router.push({ name: "HouseSearchBar" });
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  /* height: 700px; */
  height: 100vh;
  z-index: 5;
}
.map-title {
  position: absolute;
  padding: 10px;
  top: 20px;
  color: black;
  font-weight: bolder;
  display: inline-block;
  left: 50%;
  transform: translate(-50%, 0);
  border-bottom: 3px solid black;
  background-color: rgba(255, 255, 255, 0.5);
  /* margin: 30px 50px; */
}
.map-detail {
  color: black;
  font-weight: bold;
  font-size: 18px;
  width: 200px;
  z-index: 80;
}
.map-tool {
  position: absolute;
  padding: 10px;
  top: 10%;
  left: 50px;
  text-align: left;
  z-index: 80;
  width: 200px;
  border: 5px outset;
  background-color: rgba(255, 255, 255, 0.5);
}
</style>

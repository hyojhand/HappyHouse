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
            style="font-weight: bold"
            @change="selectCategory()"
            v-model="selected"
            :options="options"
            :aria-describedby="ariaDescribedby"
            switches
            stacked
            size="lg"
          ></b-form-checkbox-group>
        </b-form-group>
        <b-form-checkbox
          style="font-weight: bold"
          @change="changeTraffic()"
          v-model="trafficStatus"
          size="lg"
        >
          교통상황 보기
        </b-form-checkbox>
        <b-form-checkbox
          style="font-weight: bold"
          @change="changeBicycle()"
          v-model="bicycleStatus"
          size="lg"
        >
          자전거 도로
        </b-form-checkbox>
        <b-form-checkbox
          style="font-weight: bold"
          @change="changeRoadView()"
          v-model="roadViewStatus"
          size="lg"
        >
          로드뷰 보기
        </b-form-checkbox>

        <p class="map-detail" role="button" @click="moveList()">
          아파트 상세정보 <b-icon icon="cursor"></b-icon>
        </p>
        <p class="map-detail" role="button" @click="moveSearch()">
          다시 검색 <b-icon icon="search"></b-icon>
        </p>
      </b-col>
      <b-col> </b-col>
    </b-row>

    <div
      :class="{ roadViewMode: isRoadView, mapMode: isMapView }"
      style="height: 100vh; float: left"
    >
      <div id="map"></div>
    </div>
    <div
      :class="{ roadViewMode: isRoadView, notMapMode: isMapView }"
      style="height: 100vh; float: left"
    >
      <!-- <div id="rvWrapper" style="width: 50%; height: 100vh; float: left"> -->
      <div id="roadview" style="width: 100%; height: 100vh"></div>
    </div>
    <br />
  </div>
</template>

<script>
import http from "@/util/http-common";
import axios from "axios";
import { mapState, mapActions } from "vuex";

export default {
  name: "HouseMap",
  data() {
    return {
      map: null,
      apartMarkers: [], // 마커를 담을 배열입니다
      apartPositions: [],
      categoryMarkers: [], // 선택한 카테고리 마커 담을 배열
      customOverlay: [],
      category: ["CE7", "CS2", "HP8", "PM9", "SC4", "AC5", "PS3", "CT1", "AT4"],

      selected: [], // Must be an array reference!
      options: [
        { text: "카페", value: "cafe" },
        { text: "은행", value: "bank" },
        { text: "교육", value: "education" },
        { text: "병원", value: "hospital" },
        { text: "마트", value: "mart" },
        { text: "편의점", value: "conveni" },
        { text: "지하철", value: "subway" },
        { text: "문화,관광", value: "culture" },
      ],
      trafficStatus: "", // 교통상태 확인
      bicycleStatus: "", // 자전거 도로 확인
      roadViewStatus: "", // 로드뷰 보기
      rvContainer: "", // 로드뷰를 표시할 div
      rv: {}, // 로드뷰 객체
      rvClient: {}, // 좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
      rvMarker: "", // 로드뷰 마커
      isRoadView: false,
      isMapView: true,
    };
  },
  computed: {
    ...mapState("houseStore", ["selectDong", "selectArea"]),
  },
  async created() {
    // 아파트 좌표 리스트 받아오기
    await http.get(`/map/apt/${this.selectDong}`).then(({ data }) => {
      this.apartPositions = data;
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
    ...mapActions("houseStore", [
      "selectApt",
      "selectApartImgNum",
      "getCafe",
      "getConbi",
      "getHospital",
      "getEducation",
      "getCulture",
    ]),
    async initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 8,
      };

      // 지도 생성
      this.map = new kakao.maps.Map(container, options);

      await this.displayMarkers();
    },
    displayMarkers() {
      // var bounds = new kakao.maps.LatLngBounds();

      // 1. 표시되있는 아파트 마커 map에서 삭제
      if (this.apartMarkers.length > 0) {
        this.apartMarkers.forEach((item) => {
          item.setMap(null);
        });
      }

      // 1-2. 표시되어있는 카테고리 마커 삭제
      if (this.categoryMarkers.length > 0) {
        this.categoryMarkers.forEach((item) => {
          item.setMap(null);
        });
        this.categoryMarkers = [];
      }

      // this.createApartMarker(this.apartPositions, this.apartMarkers);
      // 2. 아파트 마커의 개수만큼 아파트 마커 표시
      var bounds = new kakao.maps.LatLngBounds();
      this.apartPositions.forEach((apart) => {
        // 2-1. 아파트 마커 이미지 세팅
        var imageSrc =
          "https://cdn-icons-png.flaticon.com/512/6984/6984891.png";
        var imageSize = new kakao.maps.Size(75, 75);
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 3. 아파트 마커 좌표, 이미지 세팅
        var marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(apart.lat, apart.lng),
          image: markerImage,
          clickable: true,
        });
        this.apartMarkers.push(marker);
        kakao.maps.event.addListener(marker, "click", () => {
          this.closeOverlay();
          this.setApartOverlay(apart);
        });
        marker.setMap(this.map);
        // 4. 범위 재설정을 위해 LatLngBounds 객체에 좌표를 추가해서
        bounds.extend(new kakao.maps.LatLng(apart.lat, apart.lng));
      });
      // 5. 검색된 위치들 기준으로 지도 범위 재설정
      this.map.setBounds(bounds);

      kakao.maps.event.addListener(this.map, "dragend", () => {
        this.selectCategory();
      });
      kakao.maps.event.addListener(this.map, "zoom_changed", () => {
        this.selectCategory();
      });
    },
    setApartOverlay(apart) {
      // var content =
      //   `<div style="padding:5px;font-size:12px; color:black;">` +
      //   apart.apartmentName +
      //   `</div>`;
      var content =
        `<div class="mapwrap">` +
        `    <div class="mapinfo">` +
        `        <div class="title">` +
        apart.apartmentName +
        '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
        `        </div>` +
        `        <div class="body">` +
        `            <div class="desc">` +
        `                <div class="ellipsis">주소 : ` +
        apart.sidoName +
        apart.gugunName +
        apart.dong +
        `</div>` +
        `                <div class="ellipsis">최근 거래가 : ` +
        apart.recentPrice +
        `</div>` +
        `                <div class="ellipsis">검색</div>` +
        `            </div>` +
        `        </div>` +
        `    </div>` +
        `</div>`;

      this.customOverlay.push(
        new kakao.maps.CustomOverlay({
          map: this.map,
          position: new kakao.maps.LatLng(apart.lat, apart.lng),
          content: content,
          xAnchor: 0.3,
          yAnchor: 0.91,
        })
      );
    },
    // 카테고리 키워드 검색 후 마커찍는 메서드
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        data.forEach((place) => {
          var imageSrc;
          var categoryCode = place.category_group_code;
          if (categoryCode === "CE7") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6984/6984949.png";
          } else if (categoryCode === "BK9") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6985/6985031.png";
          } else if (
            categoryCode === "SC4" ||
            categoryCode === "AC5" ||
            categoryCode === "PS3"
          ) {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6985/6985071.png";
          } else if (categoryCode === "HP8" || categoryCode === "PM9") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6985/6985037.png";
          } else if (categoryCode === "MT1") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6984/6984975.png";
          } else if (categoryCode === "CS2") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6985/6985066.png";
          } else if (categoryCode === "SW8") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6984/6984900.png";
          } else if (categoryCode === "CT1" || categoryCode === "AT4") {
            imageSrc =
              "https://cdn-icons-png.flaticon.com/512/6985/6985068.png";
          }

          var imageSize = new kakao.maps.Size(45, 45); // 마커 이미지의 크기
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

          var categoryMarker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(place.y, place.x),
            image: markerImage,
            clickable: true,
          });

          this.categoryMarkers.push(categoryMarker);

          kakao.maps.event.addListener(categoryMarker, "click", () => {
            this.closeOverlay();
            this.setOverlay(place);
          });
          categoryMarker.setMap(this.map);
        });
      }
    },
    closeOverlay() {
      this.customOverlay.forEach((item) => {
        item.setMap(null);
      });
    },
    setOverlay(place) {
      // var content =
      //   `<div style="padding:5px;font-size:12px;">` +
      //   place.place_name +
      //   `</div>`;

      var content = `<div style="padding:5px;font-size:12px;>하이요</div>`;

      this.customOverlay.push(
        new kakao.maps.CustomOverlay({
          map: this.map,
          position: new kakao.maps.LatLng(place.y, place.x),
          content: content,
          xAnchor: 0.3,
          yAnchor: 0.91,
        })
      );
    },
    // 카데고리 마커 찍는 메서드
    selectCategory() {
      if (this.categoryMarkers.length > 0) {
        this.categoryMarkers.forEach((item) => {
          item.setMap(null);
        });
      }

      // 장소 검색 객체
      var ps = new kakao.maps.services.Places(this.map);

      for (var k = 0; k < this.selected.length; k++) {
        // 카페
        if (this.selected[k] === "cafe") {
          ps.categorySearch("CE7", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        // 은행
        else if (this.selected[k] === "bank") {
          ps.categorySearch("BK9", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        //학교, 학원, 어린이집/유치원
        else if (this.selected[k] === "education") {
          ps.categorySearch("SC4", this.placesSearchCB, {
            useMapBounds: true,
          });
          ps.categorySearch("AC5", this.placesSearchCB, {
            useMapBounds: true,
          });
          ps.categorySearch("PS3", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        // 병원, 약국
        else if (this.selected[k] === "hospital") {
          ps.categorySearch("HP8", this.placesSearchCB, {
            useMapBounds: true,
          });
          ps.categorySearch("PM9", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        // 마트
        else if (this.selected[k] === "mart") {
          ps.categorySearch("MT1", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        // 편의점
        else if (this.selected[k] === "conveni") {
          ps.categorySearch("CS2", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        // 지하철
        else if (this.selected[k] === "subway") {
          ps.categorySearch("SW8", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
        // 문화,관광
        else if (this.selected[k] === "culture") {
          ps.categorySearch("CT1", this.placesSearchCB, {
            useMapBounds: true,
          });
          ps.categorySearch("AT4", this.placesSearchCB, {
            useMapBounds: true,
          });
        }
      }
    },
    changeTraffic() {
      if (this.trafficStatus) {
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      } else {
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      }
    },
    changeBicycle() {
      if (this.bicycleStatus) {
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.BICYCLE);
      } else {
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.BICYCLE);
      }
    },
    changeRoadView() {
      if (this.roadViewStatus) {
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
        this.setRoadView();
        this.toggleRoadView(this.map.getCenter());
        // this.toggleRoadView(
        //   new kakao.maps.LatLng(
        //     this.map.getCenter().La,
        //     this.map.getCenter().Ma
        //   )
        // );
      } else {
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
        this.rvMarker.setMap(null);

        this.toggleRoadView(new kakao.maps.LatLng(0, 0));
        this.map.relayout();
        // this.rvContainer = "";
        // this.rv = {};
        // this.rvClient = {};
      }
    },
    setRoadView() {
      this.rvContainer = document.getElementById("roadview"); //로드뷰를 표시할 div
      this.rv = new kakao.maps.Roadview(this.rvContainer); //로드뷰 객체
      this.rvClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
      var markImage = new kakao.maps.MarkerImage(
        "https://cdn-icons-png.flaticon.com/512/7509/7509327.png",
        new kakao.maps.Size(75, 75)
      );
      this.rvMarker = new kakao.maps.Marker({
        image: markImage,
        position: this.map.getCenter(),
        draggable: true,
        map: this.map,
      });

      // kakao.maps.event.addListener(this.rvMarker, "dragend", () => {
      //   var position = this.rvMarker.getPosition(); //현재 마커가 놓인 자리의 좌표
      //   this.toggleRoadview(new kakao.maps.LatLng(position.La, position.Ma)); //로드뷰를 토글합니다
      // });
      kakao.maps.event.addListener(this.map, "click", (mouseEvent) => {
        // 현재 클릭한 부분의 좌표를 리턴
        var position = mouseEvent.latLng;
        this.rvMarker.setPosition(
          new kakao.maps.LatLng(position.getLat(), position.getLng())
        );
        this.toggleRoadView(position);
      });
    },
    toggleRoadView(position) {
      //전달받은 좌표(position)에 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄웁니다
      this.rvClient.getNearestPanoId(position, 50, (panoId) => {
        console.log("panoID : " + panoId);
        if (panoId === null) {
          this.isRoadView = false;
          this.isMapView = true;
          this.rvContainer.style.display = "none"; //로드뷰를 넣은 컨테이너를 숨깁니다
          this.map.relayout();

          // this.map.setCenter();
          // this.map.setLevel();
        } else {
          if (this.roadViewStatus) {
            this.isRoadView = true;
            this.isMapView = false;
            this.rvContainer.style.display = "block"; //로드뷰를 넣은 컨테이너를 보이게합니다

            this.rv.setPanoId(panoId, position); //panoId를 통한 로드뷰 실행
            this.rv.relayout(); //로드뷰를 감싸고 있는 영역이 변경됨에 따라, 로드뷰를 재배열합니다
            this.map.relayout(); //지도를 감싸고 있는 영역이 변경됨에 따라, 지도를 재배열합니다
            // this.map.setCenter();
            // this.map.setLevel();
          }
        }
      });
    },
    async moveList() {
      console.log(this.apartPositions[0]);
      var lng = this.apartPositions[0].lng;
      var lat = this.apartPositions[0].lat;
      console.log(lng);
      console.log(lat);

      for (var i = 0; i < 9; i++) {
        var category_code = this.category[i];
        if (category_code === "CE7") {
          this.getCafe(await this.searchItem(lng, lat, 300, "CE7"));
        } else if (category_code === "CS2") {
          this.getConbi(await this.searchItem(lng, lat, 100, "CS2"));
        } else if (category_code === "HP8" || category_code === "PM9") {
          this.getHospital(
            await this.searchItem(lng, lat, 1000, category_code)
          );
        } else if (
          category_code === "SC4" ||
          category_code === "AC5" ||
          category_code === "PS3"
        ) {
          this.getEducation(
            await this.searchItem(lng, lat, 500, category_code)
          );
        } else if (category_code === "CT1" || category_code === "AT4") {
          this.getEducation(
            await this.searchItem(lng, lat, 1500, category_code)
          );
        }
      }

      console.log(await this.searchItem(lng, lat, 300, "CE7"));
      await this.$router.push({ name: "HouseList" });
    },
    // 주변 상가 정보 가져오기
    async searchItem(x, y, rad, code) {
      axios.defaults.headers["Authorization"] =
        "KakaoAK d00501781e125b07eeb9a328ebc287e6";
      var data = await axios.get(
        `https://dapi.kakao.com/v2/local/search/category.json?category_group_code=${code}&radius=${rad}&x=${x}&y=${y}`
      );
      return data.data.meta.total_count;
    },
    moveSearch() {
      this.$router.push({ name: "HouseSearchBar" });
    },
  },
};
</script>

<style scoped>
.mapMode {
  width: 100%;
}
.roadViewMode {
  width: 50%;
}
.notMapMode {
  width: 0%;
}
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
  font-weight: bold;
  font-size: 18px;
  width: 200px;
  /* z-index: 80; */
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

.mapwrap {
  /* position: absolute; */
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  overflow: hidden;
  font-size: 12px;
  line-height: 1.5;
}
.mapwrap * {
  padding: 0;
  margin: 0;
}
.mapwrap .mapinfo {
  /* width: 286px;
  height: 120px; */
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.mapwrap .mapinfo:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.mapinfo .title {
  font-size: 15px;
  text-align: center;
  margin: 0;
  line-height: 2rem;
  padding-top: 3px;
  font-family: "Gothic", "Arial Narrow", Arial, sans-serif;
}
.mapinfo .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.mapinfo .close:hover {
  cursor: pointer;
}
.mapinfo .body {
  position: relative;
  overflow: hidden;
  margin: 0 10px;
}
.mapinfo .desc {
  text-align: center;
  position: relative;
  margin: 13px 0 0 100px;
  height: 75px;
}
.desc .ellipsis {
  font-size: 10px!;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
/* .desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.mapinfo .img {
  position: absolute;
  margin: 0 10px;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  color: #888;
  overflow: hidden;
  text-align: center;
} */
.mapinfo:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.mapinfo .link {
  color: #5085bb;
}
.map-div {
  display: none;
}
</style>

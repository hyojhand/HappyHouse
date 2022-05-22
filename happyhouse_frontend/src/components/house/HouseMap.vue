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
          style="color: black; font-weight: bold"
          @change="changeTraffic()"
          v-model="trafficStatus"
          size="lg"
        >
          교통상황 보기
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
      apartMarkers: [], // 마커를 담을 배열입니다
      apartPositions: [],
      // placeOverlay: "", // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이
      // contentNode: "", // 커스텀 오버레이의 컨텐츠 엘리먼트
      // currCategory: "", // 현재 선택된 카테고리를 가지고 있을 변수

      // infowindow: "", // 인포윈도우 객체
      categoryMarkers: [], // 선택한 카테고리 마커 담을 배열

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
      var bounds = new kakao.maps.LatLngBounds();

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

      // 2. 아파트 마커의 개수만큼 아파트 마커 표시
      for (var i = 0; i < this.apartPositions.length; i++) {
        // 2-1. 아파트 마커 이미지 세팅
        var imageSrc =
          "https://cdn-icons-png.flaticon.com/512/6984/6984891.png";
        var imageSize = new kakao.maps.Size(60, 60);
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 2-2. 아파트 마커 포지션 좌표 세팅
        var apartPosition = new kakao.maps.LatLng(
          this.apartPositions[i].lat,
          this.apartPositions[i].lng
        );

        // 3. 아파트 마커 좌표, 이미지 세팅
        var marker = new kakao.maps.Marker({
          map: this.map,
          position: apartPosition,
          image: markerImage,
        });
        this.apartMarkers.push(marker);
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
        bounds.extend(apartPosition);
      }

      // 5. 검색된 위치들 기준으로 지도 범위 재설정
      this.map.setBounds(bounds);
    },
    addMarker() {},
    // 카테고리 키워드 검색 후 마커찍는 메서드
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // // 인포윈도우 객체 생성
        // var categoryInfowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

        for (var i = 0; i < data.length; i++) {
          var place = data[i];
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
            map: this.map,
            position: new kakao.maps.LatLng(place.y, place.x),
            image: markerImage,
          });

          // // // 마커에 클릭이벤트를 등록합니다
          // kakao.maps.event.addListener(categoryMarker, "click", function () {
          //   // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          //   categoryInfowindow.setContent(
          //     '<div style="padding:5px;font-size:12px;">' +
          //       place.place_name +
          //       "</div>"
          //   );
          //   categoryInfowindow.open(this.map, categoryMarker);
          // });

          categoryMarker.setMap(this.map);
          this.categoryMarkers.push(categoryMarker);
        }
      }
    },
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

      // console.log(this.categoryMarkers);
    },
    changeTraffic() {
      if (this.trafficStatus) {
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      } else {
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      }
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

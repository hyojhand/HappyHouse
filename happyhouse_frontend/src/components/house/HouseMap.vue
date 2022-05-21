<template>
  <b-container>
    <h3 class="underline-blue">
      <b-icon icon="house-fill"></b-icon> {{ sidoName }} {{ gugunName }}
      {{ dong }} 아파트 목록입니다.
    </h3>

    <b-form-group
      label="Select filter more Information"
      v-slot="{ ariaDescribedby }"
    >
      <b-form-checkbox-group
        v-model="selected"
        :options="options"
        :aria-describedby="ariaDescribedby"
        switches
      ></b-form-checkbox-group>
    </b-form-group>

    <div id="map"></div>
    <br />
    <b-button variant="info" @click="moveList()">아파트 상세정보 확인</b-button>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";

export default {
  name: "HouseMap",
  data() {
    return {
      sidoName: "",
      gugunName: "",
      dong: "",

      map: null,
      markers: [],
      markerPositions: [],

      selected: [], // Must be an array reference!
      options: [
        { text: "Apart", value: "apart" },
        { text: "Cafe", value: "cafe" },
        { text: "Market", value: "market" },
        { text: "Hospital", value: "hospital" },
      ],
    };
  },
  computed: {
    ...mapState(["storeList"]),
  },
  async created() {
    // 검색한 지역명 받아오기
    await http
      .get(`/map/aptinfo/${this.$route.params.aptcode}`)
      .then(({ data }) => {
        this.sidoName = data.sidoName;
        this.gugunName = data.gugunName;
        this.dong = data.dong;
      });
    // 아파트 좌표 리스트 받아오기
    await http
      .get(`/map/apt/${this.$route.params.aptcode}`)
      .then(({ data }) => {
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
      this.map = new kakao.maps.Map(container, options);
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

      for (var k = 0; k < this.storeList.length; k++) {
        // var imageSrcCafe = "https://i.dlpng.com/static/png/6478431_preview.png";
        var imageSrcCafe =
          "https://cdn-icons.flaticon.com/png/512/8/premium/8106.png?token=exp=1653119711~hmac=c5b90fab6ae5248e128c238d99df4dab";
        // var imageSrcCafe = "@/assets/cafe_marker.png";
        var imageSizeCafe = new kakao.maps.Size(36, 37);
        var imgOptionsCafe = {
          spriteSize: new kakao.maps.Size(36, 30), // 스프라이트 이미지의 크기
          //   spriteOrigin: new kakao.maps.Point(0, k * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        };
        var markerImageCafe = new kakao.maps.MarkerImage(
          imageSrcCafe,
          imageSizeCafe,
          imgOptionsCafe
        );

        var storePosition = new kakao.maps.LatLng(
          this.storeList[k].lat,
          this.storeList[k].lon
        );

        var markerCafe = new kakao.maps.Marker({
          map: this.map,
          position: storePosition,
          image: markerImageCafe,
        });
        this.markers.push(markerCafe);
        markerCafe.setMap(this.map);
      }

      // 2. 마커의 개수만큼 마커 표시
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
        // console.log(this.markerPositions[i].lat);
        // console.log(this.markerPositions[i].lng);

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
    moveList() {
      this.$router.push({
        name: "HouseList",
        params: {
          aptcode: this.$route.params.aptcode,
          sidoName: this.sidoName,
          gugunName: this.gugunName,
          dong: this.dong,
        },
      });
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 600px;
}
.underline-blue {
  display: inline-block;
  margin: 30px auto;
}
</style>

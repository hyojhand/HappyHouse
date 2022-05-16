<template>
  <b-container>
    <h3 class="underline-blue">
      <b-icon icon="house-fill"></b-icon> {{ sidoName }} {{ gugunName }}
      {{ dongName }} 주변 아파트 입니다.
    </h3>
    <div id="map"></div>
    <br />
    <b-button variant="info" @click="moveList()">아파트 리스트 확인</b-button>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "HouseMap",
  data() {
    return {
      sidoName: "",
      gugunName: "",
      dongName: "",

      map: null,
      markers: [],
      markerPositions: [],
    };
  },
  created() {
    // 검색한 지역명 받아오기
    http.get(`/map/aptinfo/${this.$route.params.aptcode}`).then(({ data }) => {
      this.sidoName = data.sidoName;
      this.gugunName = data.gugunName;
      this.dongName = data.dongName;
    });
    // 아파트 리스트 받아오기
    http.get(`/map/apt/${this.$route.params.aptcode}`).then(({ data }) => {
      // console.log(data);
      this.markerPositions = data;
      // console.log(this.markerPositions);
    });
  },
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=14569f72289f7b671811748997f96066&libraries=services";
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
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 8,
      };
      this.map = new kakao.maps.Map(container, options);
      this.displayMarkers();
    },
    displayMarkers() {
      var bounds = new kakao.maps.LatLngBounds();

      // 1. 표시되있는 marker있으면 map에서 삭제
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
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

        // 3. 마커 좌표, 이미지 세팅
        var marker = new kakao.maps.Marker({
          map: this.map,
          position: markerPosition,
          image: markerImage,
        });
        this.markers.push(marker);
        marker.setMap(this.map);

        // 4. 범위 재설정을 위해 LatLngBounds 객체에 좌표를 추가해서
        bounds.extend(markerPosition);
      }

      // 5. 검색된 위치들 기준으로 지도 범위 재설정
      this.map.setBounds(bounds);
    },
    moveList() {
      this.$router.push({
        name: "HouseList",
        params: { aptcode: this.$route.params.aptcode },
      });
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 500px;
}
.underline-blue {
  display: inline-block;
  margin: 30px auto;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, #b9f7e5 30%);
}
</style>

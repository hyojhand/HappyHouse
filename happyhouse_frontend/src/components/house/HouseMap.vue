<template>
  <b-container>
    <div>{{ sidoName }} {{ gugunName }} {{ dongName }} 검색 결과입니다.</div>
    <div id="map"></div>
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
      //   markers: [],
      //   latitude: 0,
      //   longitude: 0,
    };
  },
  created() {
    // 검색한 위치 받아오기
    http.get(`/map/aptinfo/${this.$route.params.aptcode}`).then(({ data }) => {
      this.sidoName = data.sidoName;
      this.gugunName = data.gugunName;
      this.dongName = data.dongName;
    });
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 8,
      };
      this.map = new kakao.maps.Map(container, options);
    },
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
      // console.log("이미 로딩됨 : ", window.kakao);
      this.initMap();
    }
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 500px;
}
</style>

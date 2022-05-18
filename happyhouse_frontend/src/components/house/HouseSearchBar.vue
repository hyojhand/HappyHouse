<template>
  <div class="form-group form-inline justify-content-center" id="searchbar">
    <div id="background"></div>

    <label class="mr-2" for="sido">시/도 : </label>
    <select class="form-control" id="sido" @change="selectCity($event)">
      <option value="0">선택</option>
      <option :value="city.sidoCode" v-for="city in cities" :key="city.sido">
        {{ city.sidoName }}
      </option>
    </select>
    <label class="mr-2 ml-3" for="gugun">구/군 : </label>
    <select class="form-control" id="gugun" @change="selectGugun($event)">
      <option value="0">선택</option>
      <option :value="gu.gugunCode" v-for="gu in guguns" :key="gu.gugun">
        {{ gu.gugunName }}
      </option>
    </select>

    <label class="mr-2 ml-3" for="dong">읍/면/동 : </label>
    <select class="form-control" id="dong" @change="selectDong($event)">
      <option value="0">선택</option>
      <option :value="don.dongCode" v-for="don in dongs" :key="don.dong">
        {{ don.dongName }}
      </option>
    </select>
  </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "HouseSearchBar",

  data() {
    return {
      cities: [],
      guguns: [],
      dongs: [],
    };
  },
  created() {
    http.get("/map/sido").then(({ data }) => {
      this.cities = data;
    });
  },
  methods: {
    selectCity(event) {
      // 도시선택 후, 해당 도시 구/군 데이터 가져오기
      http.get(`/map/gugun/${event.target.value}`).then(({ data }) => {
        this.guguns = data;
      });
    },
    selectGugun(event) {
      // 구/군 선택 후, 해당 도시 동 데이터 가져오기
      http.get(`/map/dong/${event.target.value}`).then(({ data }) => {
        this.dongs = data;
      });
    },
    selectDong(event) {
      // 동 선택후, Map으로 넘어가면서 aptcode 넘기기
      this.$router.push({
        name: "HouseMap",
        params: { aptcode: event.target.value },
      });
    },
  },
};
</script>

<style scoped>
div {
  height: 100vh;
}
#background {
  /* height: 100vh;
  width: 100vw;
  overflow: hidden;
  margin: 0;
  background-image: url("../../assets/house_searchbar.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center; */
}
/* #searchbar {
  width: 100vw;
  height: 100vh;
  background-image: require("@/assets/house_searchbar.jpg");
} */
</style>

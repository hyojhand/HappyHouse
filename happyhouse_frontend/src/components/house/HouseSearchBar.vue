<template>
  <b-container>
    <div class="form-group form-inline justify-content-center">
      <div class="form-horizontal">
        <h1>Find Your Happy House</h1>
        <button @click="testApi">test</button>
        <div class="form-inline">
          <label class="mr-2" for="sido">시/도 : </label>
          <select class="form-control" id="sido" @change="selectCity($event)">
            <option value="0">선택</option>
            <option
              :value="city.sidoCode"
              v-for="city in cities"
              :key="city.sido"
            >
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
              {{ don.dong }}
            </option>
          </select>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import { mapActions } from "vuex";

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
    ...mapActions(["getStoreList"]),
    testApi() {
      http.get("/test/api").then(({ data }) => {
        console.log(data);
      });
    },
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
    async selectDong(event) {
      let aptcode = event.target.value;
      await http.post(`/map/aptcnt/${aptcode}`);
      // 동 선택후, Map으로 넘어가면서 aptcode 넘기기

      // 상권정보
      console.log("여기부터 상권정보 시작");
      await this.getStoreList(aptcode);
      console.log("여기까지 상권정보 호출 끝");

      this.$router.push({
        name: "HouseMap",
        params: { aptcode: aptcode },
      });
    },
  },
};
</script>

<style scoped>
div {
  height: 100vh;
}
</style>

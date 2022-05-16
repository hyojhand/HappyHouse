<template>
  <div class="form-group form-inline justify-content-center">
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

    <!-- <input
      type="text"
      class="ml-1 form-control"
      id="sword"
      name="word"
      v-model="word"
      placeholder="아파트명으로 검색.."
    />
    <button type="button" id="searchBtn" class="ml-1 btn btn-outline-primary">
      검색
    </button> -->
  </div>
  <!-- <b-row class="mt-4 mb-4 text-center">
    <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col>
  </b-row> -->
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "HouseSearchBar",

  data() {
    return {
      // dongCode: "",
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
      // console.log(event.target.value);
      http.get(`/map/gugun/${event.target.value}`).then(({ data }) => {
        this.guguns = data;
      });
    },
    selectGugun(event) {
      // 구/군 선택 후, 해당 도시 동 데이터 가져오기
      // console.log(event.target.value);
      http.get(`/map/dong/${event.target.value}`).then(({ data }) => {
        this.dongs = data;
      });
    },
    selectDong(event) {
      // 동 선택후, Map으로 넘어가면서 aptcode 넘기기
      // console.log(event.target.value);
      this.$router.push({
        name: "HouseMap",
        params: { aptcode: event.target.value },
      });
    },
  },
};
</script>

<style></style>

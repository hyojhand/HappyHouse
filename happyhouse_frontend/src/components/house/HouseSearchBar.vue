<template>
  <b-container>
    <div class="form-group form-inline justify-content-center">
      <div class="form-horizontal" style="margin-top: 200px">
        <h1 style="margin-bottom: 100px; color: white">
          Find Your Happy House
        </h1>
        <div class="form-inline">
          <select
            class="form-control mr-3"
            id="sido"
            @change="selectCity($event)"
          >
            <option value="0" disabled selected>시/도</option>
            <option
              :value="city.sidoCode"
              v-for="city in cities"
              :key="city.sido"
            >
              {{ city.sidoName }}
            </option>
          </select>
          <select
            class="form-control mr-3"
            id="gugun"
            @change="selectGugun($event)"
          >
            <option value="0" disabled selected>구/군</option>
            <option :value="gu.gugunCode" v-for="gu in guguns" :key="gu.gugun">
              {{ gu.gugunName }}
            </option>
          </select>
          <select class="form-control" id="dong" @change="selectDong($event)">
            <option value="0" disabled selected>읍/면/동</option>
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
      selectGuguncode: "",
      dongs: [],
    };
  },
  created() {
    http.get("/map/sido").then(({ data }) => {
      this.cities = data;
    });
  },
  methods: {
    ...mapActions("houseStore", ["getSido", "getGugun", "getDong", "getArea"]),

    selectCity(event) {
      this.getSido(event.target.value);
      // 도시선택 후, 해당 도시 구/군 데이터 가져오기
      http.get(`/map/gugun/${event.target.value}`).then(({ data }) => {
        this.guguns = data;
      });
    },
    selectGugun(event) {
      this.getGugun(event.target.value);

      this.selectGuguncode = event.target.value;
      // 구/군 선택 후, 해당 도시 동 데이터 가져오기
      http.get(`/map/dong/${event.target.value}`).then(({ data }) => {
        this.dongs = data;
      });
    },
    async selectDong(event) {
      let aptcode = event.target.value;
      this.getDong(aptcode);
      await http.post(`/map/aptcnt/${aptcode}`);
      // 동 선택후, Map으로 넘어가면서 aptcode 넘기기

      http.get(`/map/aptinfo/${aptcode}`).then(({ data }) => {
        this.getArea({
          sidoName: data.sidoName,
          gugunName: data.gugunName,
          dongName: data.dong,
        });
      });

      this.$router.push({
        name: "HouseMap",
      });
    },
  },
};
</script>

<style scoped></style>

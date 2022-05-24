<template>
  <b-row
    class="mb-3"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    @click="selectApart"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="6" class="text-center align-self-center">
      <b-img
        class="apartImg"
        thumbnail
        :src="require(`@/assets/apart_img/apart${num}.jpg`)"
        fluid-grow
      ></b-img>
    </b-col>
    <b-col cols="6" class="align-self-center text-left">
      <div class="AptName">
        <strong>{{ apt.apartmentName }} </strong>
      </div>
      <hr />
      <div>{{ apt.buildYear }}년 건축</div>
      <div>{{ apt.recentPrice }}만원</div>
      <div>{{ apt.area }}평</div>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions } from "vuex";
import axios from "axios";
export default {
  name: "HouseListRow",
  props: {
    apt: Object,
    num: Number,
  },
  data() {
    return {
      isColor: false,
    };
  },
  computed: {
    ...mapState("houseStore", ["selectArea"]),
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
    async selectApart() {
      await this.selectApt(this.apt);
      await this.selectApartImgNum(this.num);

      var lng = this.apt.lng;
      var lat = this.apt.lat;

      this.getCafe(await this.searchItem(lng, lat, 300, "CE7"));
      this.getConbi(await this.searchItem(lng, lat, 100, "CS2"));
      this.getHospital(await this.searchItem(lng, lat, 1000, "HP8"));
      this.getEducation(await this.searchItem(lng, lat, 500, "SC4"));
      this.getCulture(await this.searchItem(lng, lat, 1500, "CT1"));
    },
    // 주변 상가 정보 가져오기
    async searchItem(x, y, rad, code) {
      axios.defaults.headers["Authorization"] =
        "KakaoAK d00501781e125b07eeb9a328ebc287e6";
      var data = await axios.get(
        `https://dapi.kakao.com/v2/local/search/category.json?category_group_code=${code}&radius=${rad}&x=${x}&y=${y}`
      );
      // console.log(data.data.documents);
      // console.log(data.data.meta.total_count);
      return data.data;
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apartImg {
  height: 200px;
}
.apt {
  width: 50px;
}
.AptName {
  font-size: 20px;
}
.mouse-over-bgcolor {
  background-color: rgba(0, 0, 0, 0.1);
}
</style>

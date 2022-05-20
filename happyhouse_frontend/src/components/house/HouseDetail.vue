<template>
  <b-container class="bv-example-row">
    <b-row class="mt-3 mb-3">
      <b-col
        ><h3>{{ selectApart.aptName }}</h3></b-col
      >
    </b-row>
    <b-row class="mb-3 mt-1">
      <b-col
        ><b-img
          id="detailImg"
          :src="require(`@/assets/apart_img/apart${selectApartImgNum}.jpg`)"
          fluid-grow
        ></b-img
      ></b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col> <strong>일련번호</strong> : {{ selectApart.aptCode }} </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col> <strong>건축년도</strong> : {{ selectApart.buildYear }} </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <strong>최근 거래일</strong> : {{ selectApart.recentYear }} 년
        {{ selectApart.recentMonth }} 월 {{ selectApart.recentDay }} 일
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <strong>최근 거래가</strong> : {{ selectApart.recentPrice }} 만원
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col> <strong>평수</strong> : {{ selectApart.area }} 평 </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col> <strong>층수</strong> : {{ selectApart.floor }} 층 </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "HouseDetail",
  computed: {
    ...mapState(["selectApart", "selectApartImgNum"]),
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
#detailImg {
  height: 500px;
}
</style>

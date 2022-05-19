<template>
  <b-container class="bv-example-row">
    <b-row class="mt-3 mb-3">
      <b-col
        ><h3>{{ selectApart.aptName }}</h3></b-col
      >
    </b-row>
    <b-row class="mb-3 mt-1">
      <b-col
        ><b-img :src="require(`@/assets/apt.png`)" fluid-grow></b-img
      ></b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <!-- <b-alert show variant="secondary">일련번호 : 일련번호</b-alert> -->
        <strong>일련번호</strong> : {{ selectApart.aptCode }}
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <!-- <b-alert show variant="primary">아파트 이름 : 아파트 </b-alert> -->
        <strong>건축년도</strong> : {{ selectApart.buildYear }}
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <!-- <b-alert show variant="danger">거래금액 : 원</b-alert> -->
        <strong>최근 거래일</strong> : {{ selectApart.recentYear }} 년
        {{ selectApart.recentMonth }} 월 {{ selectApart.recentDay }} 일
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <!-- <b-alert show variant="danger">거래금액 : 원</b-alert> -->
        <strong>최근 거래가</strong> : {{ selectApart.recentPrice }} 만원
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <!-- <b-alert show variant="info">법정동 : 법정동 </b-alert> -->
        <strong>평수</strong> : {{ selectApart.area }} 평
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <!-- <b-alert show variant="warning">층수 : 층</b-alert> -->
        <strong>층수</strong> : {{ selectApart.floor }} 층
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "HouseDetail",
  computed: {
    ...mapState(["selectApart"]),
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style></style>

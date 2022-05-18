<template>
  <div>
    <h2 class="m-4">
      {{ apts.sidoName }} {{ apts.gugunName }} {{ apts.dongName }} 지역 아파트
      최근 거래정보
    </h2>
    <b-container
      v-if="this.apts && this.apts.length != 0"
      class="bv-example-row mt-3"
    >
      <b-row>
        <b-col cols="6">
          <house-list-row
            v-for="(apt, index) in this.apts"
            :key="index"
            :apt="apt"
          />
        </b-col>
        <b-col cols="6">
          <house-detail />
        </b-col>
      </b-row>
    </b-container>
    <b-container v-else class="bv-example-row mt-3">
      <b-row>
        <b-col><b-alert show>아파트 목록이 없습니다.</b-alert></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import HouseListRow from "@/components/house/HouseListRow.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import http from "@/util/http-common";

export default {
  name: "HouseList",
  components: {
    HouseListRow,
    HouseDetail,
  },
  data() {
    return {
      apts: [],
    };
  },
  created() {
    console.log(this.$route.params.aptcode);
    http
      .get(`/map/aptdetail/${this.$route.params.aptcode}`)
      .then(({ data }) => {
        console.log(data);
        this.apts = data;
        if (data != null) {
          this.$store.dispatch("selectApt", data[0]);
        }
      });
  },
};
</script>

<style></style>

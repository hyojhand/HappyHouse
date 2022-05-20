<template>
  <div>
    <h2 class="m-4">
      {{ this.sidoName }} {{ this.gugunName }} {{ this.dong }} 지역 아파트 최근
      거래정보
    </h2>

    <div>
      <span class="mr-4"
        >정렬 기준 : <b>{{ sortBy }}</b></span
      >
      <span
        >정렬방향 : <b>{{ sortDesc ? "Descending" : "Ascending" }}</b></span
      >
    </div>
    <b-container
      v-if="this.apts && this.apts.length != 0"
      class="bv-example-row mt-3"
    >
      <div>
        <b-nav tabs fill>
          <b-nav-item @click="sortName">이름</b-nav-item>
          <b-nav-item @click="sortYear">건축년도</b-nav-item>
          <b-nav-item @click="sortPrice">거래가</b-nav-item>
          <b-nav-item @click="sortArea">평수</b-nav-item>
        </b-nav>
      </div>
      <b-row>
        <b-col cols="6">
          <house-list-row
            v-for="(apt, index) in this.apts"
            :key="index"
            :apt="apt"
            :num="Math.ceil(Math.random() * 20)"
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
      sortBy: "이름",
      sortDesc: false,
      apts: [],
      aptCode: "",
      sidoName: "",
      gugunName: "",
      dong: "",
    };
  },
  created() {
    this.sidoName = this.$route.params.sidoName;
    this.gugunName = this.$route.params.gugunName;
    this.dong = this.$route.params.dong;
    this.aptCode = this.$route.params.aptcode;
    http
      .get(`/map/aptdetail/${this.$route.params.aptcode}`)
      .then(({ data }) => {
        console.log(data);
        this.apts = data;
        if (data != null) {
          this.$store.dispatch("selectApt", data[0]);
          this.$store.dispatch("selectApartImgNum", "1");
        }
      });
  },
  methods: {
    sortName() {
      if (this.sortBy === "이름" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            console.log(data);
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      } else {
        this.sortBy = "이름";
        this.sortDesc = false;
        http
          .get(`/map/aptdetail/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            console.log(data);
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      }
    },
    sortYear() {
      if (this.sortBy === "건축년도" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/year/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      } else {
        this.sortBy = "건축년도";
        this.sortDesc = false;
        http
          .get(`/map/aptdetail/year/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      }
    },
    sortPrice() {
      if (this.sortBy === "거래가" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/price/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      } else {
        this.sortBy = "거래가";
        this.sortDesc = false;
        http
          .get(`/map/aptdetail/price/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      }
    },
    sortArea() {
      if (this.sortBy === "평수" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/area/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      } else {
        this.sortBy = "평수";
        this.sortDesc = false;
        http
          .get(`/map/aptdetail/area/${this.$route.params.aptcode}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.$store.dispatch("selectApt", data[0]);
              this.$store.dispatch("selectApartImgNum", "1");
            }
          });
      }
    },
  },
};
</script>

<style></style>

<template>
  <div>
    <h2 class="m-4" v-if="type == 'bookmark'">나의 북마크 아파트 정보</h2>
    <h2 class="m-4" v-else>
      {{ this.selectArea.sidoName }}
      {{ this.selectArea.gugunName }}
      {{ this.selectArea.dongName }} 지역 아파트 최근 거래정보
    </h2>

    <div v-if="type != 'bookmark'">
      <span class="mr-4"
        >정렬 기준 : <b>{{ sortBy }}</b></span
      >
      <span
        >정렬방향 : <b>{{ sortDesc ? "Descending" : "Ascending" }}</b></span
      >
    </div>
    <b-container
      v-if="this.apts && this.apts.length != 0"
      class="bv-example-row mt-5"
    >
      <div v-if="type != 'bookmark'">
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
          <house-detail
            :type="type"
            :flag="flag"
            @deleteBookmark="deleteBookmark"
          />
        </b-col>
      </b-row>
    </b-container>
    <b-container v-else style="margin-top: 350px; font-size: 20px">
      <b-row>
        <b-col>아파트 목록이 없습니다 :&#40;</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import HouseListRow from "@/components/house/HouseListRow.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import http from "@/util/http-common";
import { mapState, mapActions } from "vuex";
import jwt_decode from "jwt-decode";

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
      userInfo: {},
      type: "",
      flag: false,
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
    ...mapState("houseStore", ["selectDong", "selectArea"]),
  },
  async created() {
    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      await http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
      });
    }
    if (this.$route.params.type == "bookmark") {
      if (!this.isLogin) {
        alert("로그인된 사용자만 가능합니다");
        this.$router.push({ name: "Home" });
      }
      this.type = "bookmark";
      console.log("bookmark");
      http
        .get(`/map/bookmark/list/${this.userInfo.userid}`)
        .then(({ data }) => {
          console.log(data);
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
          }
        });
    } else {
      http.get(`/map/aptdetail/${this.selectDong}`).then(({ data }) => {
        this.apts = data;
        if (data != null) {
          this.selectApt(data[0]);
          this.selectApartImgNum("1");
        }
      });
    }
  },
  methods: {
    ...mapActions("houseStore", ["selectApt", "selectApartImgNum"]),
    sortName() {
      if (this.sortBy === "이름" && this.sortDesc === false) {
        this.sortDesc = true;
        http.get(`/map/aptdetailDesc/${this.selectDong}`).then(({ data }) => {
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
          }
        });
      } else {
        this.sortBy = "이름";
        this.sortDesc = false;
        http.get(`/map/aptdetail/${this.selectDong}`).then(({ data }) => {
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
          }
        });
      }
    },
    sortYear() {
      if (this.sortBy === "건축년도" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/year/${this.selectDong}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.selectApt(data[0]);
              this.selectApartImgNum("1");
            }
          });
      } else {
        this.sortBy = "건축년도";
        this.sortDesc = false;
        http.get(`/map/aptdetail/year/${this.selectDong}`).then(({ data }) => {
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
          }
        });
      }
    },
    sortPrice() {
      if (this.sortBy === "거래가" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/price/${this.selectDong}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.selectApt(data[0]);
              this.selectApartImgNum("1");
            }
          });
      } else {
        this.sortBy = "거래가";
        this.sortDesc = false;
        http.get(`/map/aptdetail/price/${this.selectDong}`).then(({ data }) => {
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
          }
        });
      }
    },
    sortArea() {
      if (this.sortBy === "평수" && this.sortDesc === false) {
        this.sortDesc = true;
        http
          .get(`/map/aptdetailDesc/area/${this.selectDong}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.selectApt(data[0]);
              this.selectApartImgNum("1");
            }
          });
      } else {
        this.sortBy = "평수";
        this.sortDesc = false;
        http.get(`/map/aptdetail/area/${this.selectDong}`).then(({ data }) => {
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
          }
        });
      }
    },
    deleteBookmark(flag) {
      if (flag) {
        http
          .get(`/map/bookmark/list/${this.userInfo.userid}`)
          .then(({ data }) => {
            this.apts = data;
            if (data != null) {
              this.selectApt(data[0]);
              this.selectApartImgNum("1");
            }
          });
      }
    },
  },
};
</script>

<style></style>

<template>
  <b-container>
    <b-row class="mt-3 mb-3">
      <b-col
        ><h3>
          {{ selectApart.apartmentName }}
          <img
            v-if="isBookmark"
            role="button"
            width="30"
            height="30"
            src="@/assets/bookmarked.png"
            @click="nobookmark"
          /><img
            v-else
            role="button"
            width="30"
            height="30"
            src="@/assets/bookmark.png"
            @click="bookmark"
          />
        </h3>
      </b-col>
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
      <b-col> <strong>건축년도</strong> : {{ selectApart.buildYear }}년 </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <strong>최근 거래일</strong> : {{ selectApart.recentYear }}년
        {{ selectApart.recentMonth }}월 {{ selectApart.recentDay }}일
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col>
        <strong>최근 거래가</strong> : {{ selectApart.recentPrice }}만원
      </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col> <strong>평수</strong> : {{ selectApart.area }}평 </b-col>
    </b-row>
    <b-row class="mb-2">
      <b-col> <strong>층수</strong> : {{ selectApart.floor }} 층 </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import http from "@/util/http-common";
import jwt_decode from "jwt-decode";

export default {
  name: "HouseDetail",
  data() {
    return {
      userInfo: {},
      isBookmark: "",
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
    ...mapState("houseStore", ["selectApart", "selectApartImgNum"]),
  },
  async created() {
    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      await http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
        console.log(this.userInfo);
      });
      http
        .get(`map/bookmark`, {
          params: {
            userid: this.userInfo.userid,
            aptCode: this.selectApart.aptCode,
          },
        })
        .then(({ data }) => {
          this.isBookmark = data;
          console.log("bookmark", this.isBookmark);
        });
    }
  },
  updated() {
    http
      .get(`map/bookmark`, {
        params: {
          userid: this.userInfo.userid,
          aptCode: this.selectApart.aptCode,
        },
      })
      .then(({ data }) => {
        this.isBookmark = data;
        console.log("bookmark", this.isBookmark);
      });
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    bookmark() {
      if (!this.isLogin) {
        alert("로그인이 필요합니다 !");
        this.$router.push({ name: "SignIn" });
      }

      http
        .post(`map/bookmark`, {
          userid: this.userInfo.userid,
          aptCode: this.selectApart.aptCode,
        })
        .then(({ data }) => {
          let msg = "북마크 저장시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "북마크하였습니다.";
            this.isBookmark = true;
          }
          alert(msg);
        });
    },
    nobookmark() {
      http
        .delete(`map/bookmark`, {
          params: {
            userid: this.userInfo.userid,
            aptCode: this.selectApart.aptCode,
          },
        })
        .then(({ data }) => {
          let msg = "북마크 삭제시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "북마크가 삭제되었습니다.";
            console.log(this);
            if (this.$route.params.type == "bookmark") {
              this.$emit("deleteBookmark", true);
            } else {
              this.isBookmark = false;
            }
          }
          alert(msg);
        });
    },
  },
};
</script>

<style scoped>
#detailImg {
  height: 400px;
}
</style>

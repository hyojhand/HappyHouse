<template>
  <b-container style="overflow: scroll; height: 700px">
    <b-row class="mb-2">
      <b-col
        ><h3>
          {{ selectApart.apartmentName }}
          <img
            v-if="isBookmark"
            role="button"
            width="30"
            height="30"
            src="@/assets/apt_detail/bookmarked.png"
            @click="nobookmark"
          /><img
            v-else
            role="button"
            width="30"
            height="30"
            src="@/assets/apt_detail/bookmark.png"
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
      <b-col>
        <strong>주소</strong> : {{ selectApart.sidoName }}
        {{ selectApart.gugunName }} {{ selectApart.dong }}
      </b-col>
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

    <div class="accordion" role="tablist">
      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button
            block
            v-b-toggle.accordion-1
            variant="light"
            style="padding: 10px 30px; line-height: 40px"
            ><span style="float: left"
              ><img
                width="30"
                height="30"
                src="@/assets/apt_detail/cafeImg.png"
              />&nbsp;&nbsp; 카페 :
              <span>{{ cafeInfo.meta.total_count }} 개 </span>
            </span>
            <span style="float: right">
              <img
                v-if="cafeInfo.meta.total_count >= 7"
                width="40"
                height="40"
                src="@/assets/apt_detail/gold_medal.png"
              />
              <img
                v-else-if="cafeInfo.meta.total_count >= 4"
                width="40"
                height="40"
                src="@/assets/apt_detail/silver_medal.png"
              />
              <img
                v-else
                width="40"
                height="40"
                src="@/assets/apt_detail/bronze_medal.png"
              />
            </span>
          </b-button>
        </b-card-header>
        <b-collapse
          id="accordion-1"
          visible
          accordion="my-accordion"
          role="tabpanel"
        >
          <b-card-body>
            <b-card-text v-for="cafe in cafeInfo.documents" :key="cafe.id"
              ><code>{{ cafe.distance }}m</code> 거리에
              <code>{{ cafe.place_name }}</code> 이(가) 있습니다.</b-card-text
            >
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button
            block
            v-b-toggle.accordion-2
            variant="light"
            style="padding: 10px 30px; line-height: 40px"
            ><span style="float: left"
              ><img
                width="30"
                height="30"
                src="@/assets/apt_detail/conbiImg.png"
              />&nbsp;&nbsp; 편의 :
              <span>{{ conbiInfo.meta.total_count }} 개 </span>
            </span>
            <span style="float: right">
              <img
                v-if="conbiInfo.meta.total_count >= 7"
                width="40"
                height="40"
                src="@/assets/apt_detail/gold_medal.png"
              />
              <img
                v-else-if="conbiInfo.meta.total_count >= 4"
                width="40"
                height="40"
                src="@/assets/apt_detail/silver_medal.png"
              />
              <img
                v-else
                width="40"
                height="40"
                src="@/assets/apt_detail/bronze_medal.png"
              />
            </span>
          </b-button>
        </b-card-header>
        <b-collapse
          id="accordion-2"
          visible
          accordion="my-accordion"
          role="tabpanel"
        >
          <b-card-body>
            <b-card-text v-for="conbi in conbiInfo.documents" :key="conbi.id"
              ><code>{{ conbi.distance }}m</code> 거리에
              <code>{{ conbi.place_name }}</code> 이(가) 있습니다.</b-card-text
            >
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button
            block
            v-b-toggle.accordion-3
            variant="light"
            style="padding: 10px 30px; line-height: 40px"
            ><span style="float: left"
              ><img
                width="30"
                height="30"
                src="@/assets/apt_detail/eduImg.png"
              />
              &nbsp;&nbsp; 교육 :
              <span>{{ educationInfo.meta.total_count }} 개 </span>
            </span>
            <span style="float: right">
              <img
                v-if="educationInfo.meta.total_count >= 7"
                width="40"
                height="40"
                src="@/assets/apt_detail/gold_medal.png"
              />
              <img
                v-else-if="educationInfo.meta.total_count >= 4"
                width="40"
                height="40"
                src="@/assets/apt_detail/silver_medal.png"
              />
              <img
                v-else
                width="40"
                height="40"
                src="@/assets/apt_detail/bronze_medal.png"
              />
            </span>
          </b-button>
        </b-card-header>
        <b-collapse
          id="accordion-3"
          visible
          accordion="my-accordion"
          role="tabpanel"
        >
          <b-card-body>
            <b-card-text v-for="edu in educationInfo.documents" :key="edu.id"
              ><code>{{ edu.distance }}m</code> 거리에
              <code>{{ edu.place_name }}</code> 이(가) 있습니다.</b-card-text
            >
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button
            block
            v-b-toggle.accordion-4
            variant="light"
            style="padding: 10px 30px; line-height: 40px"
            ><span style="float: left"
              ><img
                width="30"
                height="30"
                src="@/assets/apt_detail/hospitalImg.png"
              />
              &nbsp;&nbsp; 의료 :
              <span>{{ hospitalInfo.meta.total_count }} 개 </span>
            </span>
            <span style="float: right">
              <img
                v-if="hospitalInfo.meta.total_count >= 7"
                width="40"
                height="40"
                src="@/assets/apt_detail/gold_medal.png"
              />
              <img
                v-else-if="hospitalInfo.meta.total_count >= 4"
                width="40"
                height="40"
                src="@/assets/apt_detail/silver_medal.png"
              />
              <img
                v-else
                width="40"
                height="40"
                src="@/assets/apt_detail/bronze_medal.png"
              />
            </span>
          </b-button>
        </b-card-header>
        <b-collapse
          id="accordion-4"
          visible
          accordion="my-accordion"
          role="tabpanel"
        >
          <b-card-body>
            <b-card-text v-for="hosp in hospitalInfo.documents" :key="hosp.id"
              ><code>{{ hosp.distance }}m</code> 거리에
              <code>{{ hosp.place_name }}</code> 이(가) 있습니다.</b-card-text
            >
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="mb-1">
        <b-card-header
          header-tag="header"
          class="p-1"
          role="tab"
          padding="auto"
        >
          <b-button
            block
            v-b-toggle.accordion-5
            variant="light"
            style="padding: 10px 30px; line-height: 40px"
            ><span style="float: left"
              ><img
                width="30"
                height="30"
                src="@/assets/apt_detail/cultureImg.png"
              />&nbsp;&nbsp; 문화 :
              <span>{{ cultureInfo.meta.total_count }} 개 </span>
            </span>
            <span style="float: right">
              <img
                v-if="cultureInfo.meta.total_count >= 7"
                width="40"
                height="40"
                src="@/assets/apt_detail/gold_medal.png"
              />
              <img
                v-else-if="cultureInfo.meta.total_count >= 4"
                width="40"
                height="40"
                src="@/assets/apt_detail/silver_medal.png"
              />
              <img
                v-else
                width="40"
                height="40"
                src="@/assets/apt_detail/bronze_medal.png"
              />
            </span>
          </b-button>
        </b-card-header>
        <b-collapse
          id="accordion-5"
          visible
          accordion="my-accordion"
          role="tabpanel"
        >
          <b-card-body>
            <b-card-text
              v-for="culture in cultureInfo.documents"
              :key="culture.id"
              ><code>{{ culture.distance }}m</code> 거리에
              <code>{{ culture.place_name }}</code> 이(가)
              있습니다.</b-card-text
            >
          </b-card-body>
        </b-collapse>
      </b-card>
    </div>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import http from "@/util/http-common";
import jwt_decode from "jwt-decode";
// import axios from "axios";

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
    ...mapState("houseStore", [
      "selectApart",
      "selectApartImgNum",
      "cafeInfo",
      "conbiInfo",
      "hospitalInfo",
      "educationInfo",
      "cultureInfo",
    ]),
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
  height: 370px;
}
</style>

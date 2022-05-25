<template>
  <div class="list-bg">
    <h2 class="p-4 mt-5" v-if="type == 'bookmark'">나의 북마크</h2>
    <h2 class="p-4 mt-5" v-else>
      {{ this.selectArea.sidoName }}
      {{ this.selectArea.gugunName }}
      {{ this.selectArea.dongName }} 지역 아파트 정보
    </h2>

    <b-container v-if="this.apts && this.apts.length != 0" class="mt-5">
      <div v-if="type != 'bookmark'" class="text-right mb-5">
        <span class="sort-order" role="button" @click="sort('이름')"
          >이름순<b-icon :icon="sortIconName"></b-icon
        ></span>
        <span class="sort-order" role="button" @click="sort('건축년도')"
          >건축년도순<b-icon :icon="sortIconYear"></b-icon
        ></span>
        <span class="sort-order" role="button" @click="sort('거래가')"
          >거래가순<b-icon :icon="sortIconPrice"></b-icon
        ></span>
        <span class="sort-order" role="button" @click="sort('평수')"
          >평수순<b-icon :icon="sortIconArea"></b-icon
        ></span>
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
            id="house_detail"
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
import axios from "axios";

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
      sortIconName: "",
      sortIconYear: "",
      sortIconPrice: "",
      sortIconArea: "",
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
    ...mapState("houseStore", [
      "selectDong",
      "selectArea",
      "cafeInfo",
      "conbiInfo",
      "hospitalInfo",
      "educationInfo",
      "cultureInfo",
    ]),
  },
  async created() {
    console.log(this.cafeInfo);
    console.log(this.conbiInfo);
    console.log(this.hospitalInfo);
    console.log(this.educationInfo);
    console.log(this.cultureInfo);

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
      await http.get(`/map/aptdetail/${this.selectDong}`).then(({ data }) => {
        this.apts = data;
        if (data != null) {
          this.selectApt(data[0]);
          this.selectApartImgNum("1");
        }
      });
    }
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
    async sort(kind) {
      this.sortIconName =
        this.sortIconYear =
        this.sortIconPrice =
        this.sortIconArea =
          "";
      if (this.sortBy == kind) {
        this.sortDesc = !this.sortDesc;
      } else {
        this.sortBy = kind;
        this.sortDesc = false;
      }
      if (kind == "이름") {
        if (this.sortDesc) {
          this.sortIconName = "sort-alpha-down";
        } else {
          this.sortIconName = "sort-alpha-up";
        }
      } else if (kind == "건축년도") {
        if (this.sortDesc) {
          this.sortIconYear = "sort-numeric-down";
        } else {
          this.sortIconYear = "sort-numeric-up";
        }
      } else if (kind == "거래가") {
        if (this.sortDesc) {
          this.sortIconPrice = "sort-numeric-down";
        } else {
          this.sortIconPrice = "sort-numeric-up";
        }
      } else if (kind == "평수") {
        if (this.sortDesc) {
          this.sortIconArea = "sort-numeric-down";
        } else {
          this.sortIconArea = "sort-numeric-up";
        }
      }
      await http
        .get(`/map/aptsort/${this.selectDong}`, {
          params: { kind: kind, order: !this.sortDesc },
        })
        .then(({ data }) => {
          this.apts = data;
          if (data != null) {
            this.selectApt(data[0]);
            this.selectApartImgNum("1");
            console.log(data[0]);
            this.setSortApart(data[0]);
          }
        });
    },
    async setSortApart(data) {
      var lng = data.lng;
      var lat = data.lat;

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
        `https://dapi.kakao.com/v2/local/search/category.json?category_group_code=${code}&radius=${rad}&x=${x}&y=${y}&size=5&sort=distance`
      );
      return data.data;
    },

    // sortName() {
    //   if (this.sortBy === "이름" && this.sortDesc === false) {
    //     this.sortDesc = true;
    //     http.get(`/map/aptdetailDesc/${this.selectDong}`).then(({ data }) => {
    //       this.apts = data;
    //       if (data != null) {
    //         this.selectApt(data[0]);
    //         this.selectApartImgNum("1");
    //       }
    //     });
    //     this.sortIconName = "sort-alpha-up";
    //     this.sortIconYear = this.sortIconPrice = this.sortIconArea = "";
    //   } else {
    //     this.sortBy = "이름";
    //     this.sortDesc = false;
    //     http.get(`/map/aptdetail/${this.selectDong}`).then(({ data }) => {
    //       this.apts = data;
    //       if (data != null) {
    //         this.selectApt(data[0]);
    //         this.selectApartImgNum("1");
    //       }
    //     });
    //     this.sortIconName = "sort-alpha-down";
    //     this.sortIconYear = this.sortIconPrice = this.sortIconArea = "";
    //   }
    // },
    // sortYear() {
    //   if (this.sortBy === "건축년도" && this.sortDesc === false) {
    //     this.sortDesc = true;
    //     http
    //       .get(`/map/aptdetailDesc/year/${this.selectDong}`)
    //       .then(({ data }) => {
    //         this.apts = data;
    //         if (data != null) {
    //           this.selectApt(data[0]);
    //           this.selectApartImgNum("1");
    //         }
    //       });
    //     this.sortIconYear = "sort-numeric-up";
    //     this.sortIconName = this.sortIconPrice = this.sortIconArea = "";
    //   } else {
    //     this.sortBy = "건축년도";
    //     this.sortDesc = false;
    //     http.get(`/map/aptdetail/year/${this.selectDong}`).then(({ data }) => {
    //       this.apts = data;
    //       if (data != null) {
    //         this.selectApt(data[0]);
    //         this.selectApartImgNum("1");
    //       }
    //     });
    //     this.sortIconYear = "sort-numeric-down";
    //     this.sortIconName = this.sortIconPrice = this.sortIconArea = "";
    //   }
    // },
    // sortPrice() {
    //   if (this.sortBy === "거래가" && this.sortDesc === false) {
    //     this.sortDesc = true;
    //     http
    //       .get(`/map/aptdetailDesc/price/${this.selectDong}`)
    //       .then(({ data }) => {
    //         this.apts = data;
    //         if (data != null) {
    //           this.selectApt(data[0]);
    //           this.selectApartImgNum("1");
    //         }
    //       });
    //     this.sortIconPrice = "sort-numeric-up";
    //     this.sortIconName = this.sortIconYear = this.sortIconArea = "";
    //   } else {
    //     this.sortBy = "거래가";
    //     this.sortDesc = false;
    //     http.get(`/map/aptdetail/price/${this.selectDong}`).then(({ data }) => {
    //       this.apts = data;
    //       if (data != null) {
    //         this.selectApt(data[0]);
    //         this.selectApartImgNum("1");
    //       }
    //     });
    //     this.sortIconPrice = "sort-numeric-down";
    //     this.sortIconName = this.sortIconYear = this.sortIconArea = "";
    //   }
    // },
    // sortArea() {
    //   if (this.sortBy === "평수" && this.sortDesc === false) {
    //     this.sortDesc = true;
    //     http
    //       .get(`/map/aptdetailDesc/area/${this.selectDong}`)
    //       .then(({ data }) => {
    //         this.apts = data;
    //         if (data != null) {
    //           this.selectApt(data[0]);
    //           this.selectApartImgNum("1");
    //         }
    //       });
    //     this.sortIconArea = "sort-numeric-up";
    //     this.sortIconName = this.sortIconYear = this.sortIconPrice = "";
    //   } else {
    //     this.sortBy = "평수";
    //     this.sortDesc = false;
    //     http.get(`/map/aptdetail/area/${this.selectDong}`).then(({ data }) => {
    //       this.apts = data;
    //       if (data != null) {
    //         this.selectApt(data[0]);
    //         this.selectApartImgNum("1");
    //       }
    //     });
    //     this.sortIconArea = "sort-numeric-down";
    //     this.sortIconName = this.sortIconYear = this.sortIconPrice = "";
    //   }
    // },
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

<style scoped>
#house_detail {
  position: sticky;
  top: 100px;
}
.sort-order {
  margin-left: 15px;
}
.list-bg {
  background: white;
}
</style>

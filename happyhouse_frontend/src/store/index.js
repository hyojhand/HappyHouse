import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    isLogin: false,
    selectApart: {},
    selectApartImgNum: "",
    cafeList: [],
  },
  actions: {
    async goLogin({ commit }, user) {
      await http.post("/member/login", user).then(({ data }) => {
        console.log(data);
        const token = data["access-token"];
        if (data.message === "success") {
          alert("로그인 완료!!!");
          sessionStorage.setItem("access-token", token);
          console.log(sessionStorage.getItem("access-token"));
          commit("SET_IS_LOGIN");
        } else {
          alert("로그인실패! 아이디&비밀번호를 확인해주세요");
          //   commit("SET_IS_NOT_LOGIN");
        }
      });
    },
    setIsNotLogin({ commit }) {
      commit("SET_IS_NOT_LOGIN");
    },
    selectApt({ commit }, apt) {
      commit("SELECT_APT", apt);
    },
    selectApartImgNum({ commit }, num) {
      commit("SELECT_APART_IMG_NUM", num);
    },
    async getCafeList({ commit }, gugunCode) {
      console.log("getStoreList 시작");
      // vue cli enviroment variables 검색
      //.env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      const SERVICE_URL =
        "http://apis.data.go.kr/B553077/api/open/sdsc2/storeListInDong";

      const params = {
        serviceKey: decodeURIComponent(SERVICE_KEY),
        pageNo: 1,
        numOfRows: 50,
        // divId: "ldongCd", //ctprvnCd, signguCd, adongCd
        // key: aptCode,
        divId: "signguCd", //ctprvnCd, signguCd, adongCd
        key: gugunCode,
        // indsLclsCd: "Q", // 음식
        indsMclsCd: "Q12", // 커피점/카페
        // indsSclsCd: "Q12A01", // 커피전문점/카페/다방
        type: "json",
      };

      await http
        .get(SERVICE_URL, { params })
        .then(({ data }) => {
          console.log("axios success 부분");
          console.log(data);
          console.log(gugunCode);

          // console.log(data.body);
          console.log(data.body.items);
          // console.log(data.body.items[0]);
          console.log(data.body.items[0].lat);
          console.log(data.body.items[0].lon);

          commit("SET_CAFE_LIST", data.body.items);
        })
        .catch((error) => {
          console.log("axios fail 부분");
          console.log(error);
        });

      console.log("getStoreList 끝");
    },
  },
  mutations: {
    SET_IS_LOGIN: (state) => {
      state.isLogin = true;
    },
    SET_IS_NOT_LOGIN: (state) => {
      state.isLogin = false;
    },
    SELECT_APT: (state, apt) => {
      state.selectApart = apt;
    },
    SELECT_APART_IMG_NUM: (state, num) => {
      state.selectApartImgNum = num;
    },
    SET_CAFE_LIST: (state, cafeItems) => {
      state.cafeList = cafeItems;
    },
  },

  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

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
    async getStoreList({ commit }, gugunCode) {
      console.log("getStoreList 시작");
      // vue cli enviroment variables 검색
      //.env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      // const SERVICE_KEY =
      //   "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      const SERVICE_URL =
        "http://apis.data.go.kr/B553077/api/open/sdsc2/storeOne";

      // const params = {
      //   serviceKey: SERVICE_KEY, //decodeURIComponent(SERVICE_KEY),
      //   key: signguCd,
      //   divId: "signguCd", //ctprvnCd, signguCd, adongCd
      //   type: "json",
      //   indsLclsCd: "Q",
      //   // indsMclsCd: "Q12",
      //   // indsSclsCd: "Q12A01",
      //   pageNo: 1,
      //   numOfRows: 100,
      // };

      const params = {
        key: "19911025",
        serviceKey: SERVICE_KEY,
      };

      await http
        .get(SERVICE_URL, { params })
        .then(({ data }) => {
          console.log("axios success 부분");

          console.log(data);
          console.log(gugunCode);
          console.log(commit, data);

          // commit("SET_HOUSE_LIST", data.response.body.items.item);
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
  },

  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

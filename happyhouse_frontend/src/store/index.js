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
  getters: {},

  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

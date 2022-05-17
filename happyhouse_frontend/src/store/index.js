import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common";
import createPersistedState from "vuex-persistedstate";
// import jwt_decode from "jwt-decode";
Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    isLogin: false,
    userInfo: null,
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
  },
  mutations: {
    SET_IS_LOGIN: (state) => {
      state.isLogin = true;
    },
    SET_IS_NOT_LOGIN: (state) => {
      state.isLogin = false;
    },
  },
  getters: {},

  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

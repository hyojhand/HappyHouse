import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    isLogin: false,
    userInfo_message: null,
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

          // 유저 정보 가져오기
          http.defaults.headers["access-token"] =
            sessionStorage.getItem("access-token");
          http.get(`/member/info/${user.userid}`).then(({ data }) => {
            commit("SET_USERINFO", data);
          });
        } else {
          alert("로그인실패! 아이디&비밀번호를 확인해주세요");
          //   commit("SET_IS_NOT_LOGIN");
        }
      });
    },
    setIsNotLogin({ commit }) {
      commit("SET_IS_NOT_LOGIN");
      commit("SET_NO_USERINFO");
    },
  },
  mutations: {
    SET_NO_USERINFO: (state) => {
      state.userInfo_message = null;
    },
    SET_USERINFO: (state, user) => {
      state.userInfo_message = user;
    },
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
      storage: sessionStorage,
    }),
  ],
});

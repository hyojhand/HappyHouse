import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common";
import createPersistedState from "vuex-persistedstate";
// import jwt_decode from "jwt-decode";
Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    isLogin: false,
    // isLoginError: false,
    userInfo: null,
  },
  actions: {
    goLogin({ commit }, user) {
      http.post("/member/login", JSON.stringify(user)).then(({ data }) => {
        console.log(data);
        // console.log(data.message);
        const token = data["access-token"];
        // console.log(token);
        if (data.message === "success") {
          alert("로그인 완료!!!");
          sessionStorage.setItem("access-token", token);
          console.log(sessionStorage.getItem("access-token"));
          // this.$store.dispatch("isLogin");
          console.log("실행1");
          commit("SET_IS_LOGIN");
          console.log("실행2");
          console.log(this.isLogin);
          // this.$router.push({ name: "Home" });
        } else {
          alert("로그인실패! 아이디&비밀번호를 확인해주세요");
          // this.$store.dispatch("isNotLogin");
          commit("SET_IS_NOT_LOGIN");
          console.log(this.isLogin);
        }
      });
    },
    setIsLogin({ commit }) {
      commit("SET_IS_LOGIN");
      //   commit("SET_IS_NOT_LOGIN", false);
    },
    setIsNotLogin({ commit }) {
      commit("SET_IS_NOT_LOGIN");
      //   commit("SET_IS_NOT_LOGIN", true);
    },
  },
  mutations: {
    SET_IS_LOGIN: (state) => {
      state.isLogin = true;
    },
    SET_IS_NOT_LOGIN: (state) => {
      state.isLogin = false;
    },
    // SET_IS_NOT_LOGIN(state, isNotLogin) {
    //   state.isNotLogin = isNotLogin;
    // },
  },
  getters: {},

  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

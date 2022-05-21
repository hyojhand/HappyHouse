import http from "@/util/http-common";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isadmin: false,
  },
  getters: {},
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
          if (data.isadmin == 1) {
            commit("SET_IS_ADMIN");
          }
        } else {
          alert("로그인실패! 아이디&비밀번호를 확인해주세요");
          //   commit("SET_IS_NOT_LOGIN");
        }
      });
    },
    setIsNotLogin({ commit }) {
      commit("SET_IS_NOT_LOGIN");
      commit("SET_IS_NOT_ADMIN");
    },
  },
  mutations: {
    SET_IS_LOGIN: (state) => {
      state.isLogin = true;
    },
    SET_IS_NOT_LOGIN: (state) => {
      state.isLogin = false;
    },
    SET_IS_ADMIN: (state) => {
      state.isadmin = true;
    },
    SET_IS_NOT_ADMIN: (state) => {
      state.isadmin = false;
    },
  },
};

export default memberStore;

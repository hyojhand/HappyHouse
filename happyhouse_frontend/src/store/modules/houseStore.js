const houseStore = {
  namespaced: true,
  state: {
    selectSido: "",
    selsectGugun: "",
    selectDong: "",
    selectArea: {
      sidoName: "",
      gugunName: "",
      dongName: "",
    },

    selectApt: "",
    selectApartImgNum: "",

    selectAptCode: "",
    selectApart: {},
  },
  getters: {},

  actions: {
    getSido: ({ commit }, sidoname) => {
      commit("GET_SIDO", sidoname);
      commit("GET_GUGUN", "");
      commit("GET_DONG", "");
    },
    getGugun: ({ commit }, gugunname) => {
      commit("GET_GUGUN", gugunname);
      commit("GET_DONG", "");
    },
    getDong: ({ commit }, dongname) => {
      commit("GET_DONG", dongname);
    },
    getArea: ({ commit }, areaname) => {
      commit("GET_AREA", areaname);
    },

    selectApt({ commit }, apt) {
      commit("SELECT_APT", apt);
    },
    selectApartImgNum({ commit }, num) {
      commit("SELECT_APART_IMG_NUM", num);
    },
  },
  mutations: {
    GET_SIDO: (state, sidoname) => {
      state.selectSido = sidoname;
    },
    GET_GUGUN: (state, gugunname) => {
      state.selsectGugun = gugunname;
    },
    GET_DONG: (state, dongname) => {
      state.selectDong = dongname;
    },
    GET_AREA: (state, areaname) => {
      state.selectArea = areaname;
    },
    SELECT_APT: (state, apt) => {
      state.selectApart = apt;
    },
    SELECT_APART_IMG_NUM: (state, num) => {
      state.selectApartImgNum = num;
    },
  },
};

export default houseStore;

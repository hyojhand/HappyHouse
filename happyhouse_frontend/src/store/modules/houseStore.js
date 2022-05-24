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
    cafeCount: 0,
    conbiCount: 0,
    hospitalCount: 0,
    educationCount: 0,
    cultureCount: 0,
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
    getCafe({ commit }, count) {
      commit("GET_CAFE", count);
    },
    getConbi({ commit }, count) {
      commit("GET_CONBI", count);
    },
    getHospital({ commit }, count) {
      commit("GET_HOSPITAL", count);
    },
    getEducation({ commit }, count) {
      commit("GET_EDUCATION", count);
    },
    getCulture({ commit }, count) {
      commit("GET_CULTURE", count);
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
    GET_CAFE: (state, count) => {
      state.cafeCount = count;
    },
    GET_CONBI: (state, count) => {
      state.conbiCount = count;
    },
    GET_HOSPITAL: (state, count) => {
      state.hospitalCount += count;
    },
    GET_EDUCATION: (state, count) => {
      state.educationCount += count;
    },
    GET_CULTURE: (state, count) => {
      state.cultureCount += count;
    },
  },
};

export default houseStore;

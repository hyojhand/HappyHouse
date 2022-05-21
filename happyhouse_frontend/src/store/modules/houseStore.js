import http from "@/util/http-common";

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
    cafeList: [],

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
    async getCafeList({ commit }, gugunCode) {
      console.log("getCafeList 시작");
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
          console.log(data.body.items);
          console.log(data.body.items[0].lat);
          console.log(data.body.items[0].lon);
          commit("SET_CAFE_LIST", data.body.items);
        })
        .catch((error) => {
          console.log(error);
        });

      console.log("getCafeList 끝");
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
    SET_CAFE_LIST: (state, cafeItems) => {
      state.cafeList = cafeItems;
    },
  },
};

export default houseStore;

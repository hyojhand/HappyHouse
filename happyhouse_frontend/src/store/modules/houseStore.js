// const houseStore = {
//   namespaced: true,
//   state: {
//     selectSido: "",
//     selsectGugun: "",
//     selectDong: "",
//     selectApt: "",
//     selectAptCode: "",
//   },

//   getters: {},

//   mutations: {
//     GET_SIDO: (state, sidoname) => {
//       state.selectSido = sidoname;
//     },
//     GET_GUGUN: (state, gugunname) => {
//       state.selsectGugun = gugunname;
//     },
//     GET_DONG: (state, dongname) => {
//       state.selectDong = dongname;
//     },
//   },

//   actions: {
//     getSido: ({ commit }, sidoname) => {
//       commit("GET_SIDO", sidoname);
//       commit("GET_GUGUN", "");
//       commit("GET_DONG", "");
//     },
//     getGugun: ({ commit }, gugunname) => {
//       commit("GET_GUGUN", gugunname);
//       commit("GET_DONG", "");
//     },
//     getDong: ({ commit }, dongname) => {
//       commit("GET_DONG", dongname);
//     },
//   },
// };

// export default houseStore;

import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/instargram",
    name: "Instargram",
    component: () => import("@/views/Instargram.vue"),
  },
  {
    path: "/user",
    name: "Member",
    component: () => import("@/views/Member.vue"),
    children: [
      {
        path: "singin",
        name: "SignIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "singup",
        name: "SignUp",
        component: () => import("@/components/user/MemberJoin.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "Board",
    component: () => import("@/views/Board.vue"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "BoardWrite",
        component: () => import("@/components/board/BoardWrite.vue"),
      },
      {
        path: "detail/:no",
        name: "BoardView",
        component: () => import("@/components/board/BoardView.vue"),
      },
      {
        path: "update/:no",
        name: "BoardUpdate",
        component: () => import("@/components/board/BoardUpdate.vue"),
      },
      {
        path: "delete/:no",
        name: "BoardDelete",
        component: () => import("@/components/board/BoardDelete.vue"),
      },
    ],
  },
  {
    path: "/house",
    name: "House",
    component: () => import("@/views/House.vue"),
    redirect: "/house/search",
    children: [
      {
        path: "search",
        name: "HouseSearchBar",
        component: () => import("@/components/house/HouseSearchBar.vue"),
      },
      {
        path: "map/:aptcode",
        name: "HouseMap",
        component: () => import("@/components/house/HouseMap.vue"),
      },
      {
        path: "list/:aptcode",
        name: "HouseList",
        component: () => import("@/components/house/HouseList.vue"),
      },
      {
        path: "detail",
        name: "HouseDetail",
        component: () => import("@/components/house/HouseDetail.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

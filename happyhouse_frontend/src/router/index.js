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
    path: "/admin",
    name: "Admin",
    component: () => import("@/views/Admin.vue"),
    redirect: "/admin/member",
    children: [
      {
        path: "member",
        name: "AdminMember",
        component: () => import("@/components/admin/AdminMember.vue"),
      },
      {
        path: "search",
        name: "AdminSearch",
        component: () => import("@/components/admin/AdminSearch.vue"),
      },
      {
        path: "bookmark",
        name: "AdminBookmark",
        component: () => import("@/components/admin/AdminBookmark.vue"),
      },
    ],
  },
  {
    path: "/user",
    name: "Member",
    component: () => import("@/views/Member.vue"),
    children: [
      {
        path: "signin",
        name: "SignIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "signup",
        name: "SignUp",
        component: () => import("@/components/user/MemberJoin.vue"),
      },
    ],
  },
  {
    path: "/mypage",
    name: "Mypage",
    component: () => import("@/views/Mypage.vue"),
    // redirect: "/mypage/member",
    children: [
      {
        path: "member",
        name: "MypageMember",
        component: () => import("@/components/mypage/MypageMember.vue"),
      },
      {
        path: "message",
        name: "MypageMessage",
        component: () => import("@/components/mypage/MypageMessage.vue"),
      },
      {
        path: "board",
        name: "MypageWriteBoard",
        component: () => import("@/components/mypage/MypageWriteBoard.vue"),
      },
      {
        path: "reply",
        name: "MypageWriteReply",
        component: () => import("@/components/mypage/MypageWriteReply.vue"),
      },
      {
        path: "like",
        name: "MypageLikeBoard",
        component: () => import("@/components/mypage/MypageLikeBoard.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "Board",
    component: () => import("@/views/Board.vue"),
    redirect: "/board/main",
    children: [
      {
        path: "main",
        name: "BoardMain",
        component: () => import("@/components/board/BoardMain.vue"),
      },
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
        path: "map",
        name: "HouseMap",
        component: () => import("@/components/house/HouseMap.vue"),
      },
      {
        path: "list",
        name: "HouseList",
        component: () => import("@/components/house/HouseList.vue"),
      },
    ],
  },
  {
    path: "*",
    name: "Error",
    component: () => import("@/views/Error.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

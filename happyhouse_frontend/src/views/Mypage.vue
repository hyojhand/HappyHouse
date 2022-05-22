<template>
  <div id="mypage-container">
    <b-row class="p-5 text-center">
      <div style="margin-top: 100px; margin-left: 100px">
        <div class="mt-5">
          <a class="text-light" @click="moveMypageMember">회원정보 수정</a>
        </div>
        <div class="mt-5">
          <a class="text-light" @click="moveMessage">쪽지함</a>
        </div>
        <div class="mt-5">
          <a class="text-light" @click="moveWriteBoard">내가 쓴 글</a>
        </div>
        <div class="mt-5">
          <a class="text-light" @click="moveWriteReply">내가 쓴 댓글</a>
        </div>
        <div class="mt-5">
          <a class="text-light" @click="moveLikeBoard">좋아요한 게시글</a>
        </div>
      </div>
      <b-col class="text-center">
        <router-view></router-view>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
import http from "@/util/http-common";
import jwt_decode from "jwt-decode";
export default {
  name: "Mypage",
  data() {
    return {
      userInfo: {},
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
  },
  async created() {
    if (!this.isLogin) {
      alert("로그인된 사용자만 가능합니다!");
      this.$router.push({ name: "Home" });
    }
    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      await http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
        console.log(this.userInfo);
      });
    }
    this.$router.push({
      name: "MypageMember",
      params: { userInfo: this.userInfo },
    });
  },

  methods: {
    moveMypageMember() {
      this.$router.push({
        name: "MypageMember",
        params: { userInfo: this.userInfo },
      });
    },
    moveMessage() {
      this.$router.push({
        name: "MypageMessage",
        params: { userInfo: this.userInfo },
      });
    },
    moveWriteBoard() {
      this.$router.push({
        name: "MypageWriteBoard",
        params: { userInfo: this.userInfo },
      });
    },
    moveWriteReply() {
      this.$router.push({
        name: "MypageWriteReply",
        params: { userInfo: this.userInfo },
      });
    },
    moveLikeBoard() {
      this.$router.push({
        name: "MypageLikeBoard",
        params: { userInfo: this.userInfo },
      });
    },
  },
};
</script>

<style scoped>
#mypage-container {
  background: url("../assets/mypage.jpg");
  background-size: cover;
  background-attachment: fixed;
  width: 100%;
  min-height: 100vh;
}
</style>

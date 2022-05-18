<template>
  <b-container class="bv-example-row mt-5">
    <b-row class="m-2 text-center">
      <div class="mt-5">
        <div class="mt-3">
          <a class="text-dark" @click="moveMypageMember">회원정보 수정</a>
        </div>
        <div class="mt-3">
          <a class="text-dark" @click="moveMessageReceive">받은 쪽지</a>
        </div>
        <div class="mt-3">
          <a class="text-dark" @click="moveMessageSend">보낸 쪽지</a>
        </div>
        <div class="mt-3">
          <a class="text-dark" @click="moveWriteBoard">내가 쓴 글</a>
        </div>
        <div class="mt-3">
          <a class="text-dark" @click="moveWriteReply">내가 쓴 댓글</a>
        </div>
        <div class="mt-3">
          <a class="text-dark" @click="moveLikeBoard">좋아요한 게시글</a>
        </div>
      </div>
      <b-col class="text-center align-self-center">
        <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
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
    ...mapState(["isLogin"]),
  },
  created() {
    if (!this.isLogin) {
      alert("로그인된 사용자만 가능합니다!");
      this.$router.push({ name: "Home" });
    }

    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
        console.log(this.userInfo);
      });
    }
  },

  methods: {
    moveMypageMember() {
      this.$router.push({
        name: "MypageMember",
        params: { userInfo: this.userInfo },
      });
    },
    moveMessageReceive() {
      this.$router.push({
        name: "MypageMessageReceive",
        params: { userInfo: this.userInfo },
      });
    },
    moveMessageSend() {
      this.$router.push({
        name: "MypageMessageSend",
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

<style scoped></style>

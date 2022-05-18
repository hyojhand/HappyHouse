<template>
  <b-container class="bv-example-row mt-5">
    <b-row class="m-2 text-center">
      <div>
        <div><button @click="moveMypageMember">회원정보 수정</button></div>
        <div><button @click="moveMessageReceive">받은 쪽지</button></div>
        <div><button @click="moveMessageSend">보낸 쪽지</button></div>

        <div><button @click="moveWriteBoard">내가 쓴 글</button></div>
        <div><button @click="moveWriteReply">내가 쓴 댓글</button></div>
        <div><button @click="moveLikeBoard">좋아요한 게시글</button></div>
      </div>
      <b-col class="text-center align-self-center">
        <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "Mypage",
  data() {
    return {};
  },
  methods: {
    moveMypageMember() {
      this.$router.push({ name: "MypageMember" });
    },
    moveMessageReceive() {
      this.$router.push({ name: "MypageMessageReceive" });
    },
    moveMessageSend() {
      this.$router.push({ name: "MypageMessageSend" });
    },
    moveWriteBoard() {
      this.$router.push({ name: "MypageWriteBoard" });
    },
    moveWriteReply() {
      this.$router.push({ name: "MypageWriteReply" });
    },
    moveLikeBoard() {
      this.$router.push({ name: "MypageLikeBoard" });
    },
  },
};
</script>

<style scoped></style>

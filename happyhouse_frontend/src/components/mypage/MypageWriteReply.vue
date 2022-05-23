<template>
  <b-container class="mt-3">
    <div class="text-center mt-3">
      <div class="col-lg-11 mx-auto">
        <h2 class="p-3 mb-3 text-light">내가 쓴 댓글</h2>
        <table class="text-light w-100">
          <tr class="mypage-table-head">
            <td>내용</td>
            <td>작성 시간</td>
          </tr>
          <tr v-for="reply in replies" :key="reply.replyid" class="l-height-50">
            <td>{{ reply.content }}</td>
            <td>{{ reply.regtime }}</td>
            <td>
              <b-button
                class="btn-light"
                size="sm"
                @click="moveBoard(reply.articleno)"
                ><b-icon icon="reply"></b-icon> 바로가기</b-button
              >
            </td>
          </tr>
        </table>
      </div>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "MypageWriteReply",
  data() {
    return {
      userInfo: {
        userid: "",
        username: "",
        userpwd: "",
        email: "",
      },
      replies: [
        {
          replyid: "",
          articleno: "",
          wrtier: "",
          content: "",
          regtime: "",
          isdeleted: "",
        },
      ],
    };
  },
  created() {
    this.userInfo = this.$route.params.userInfo;
    console.log(this.userInfo);
    http.get(`/mypage/reply/${this.userInfo.userid}`).then(({ data }) => {
      console.log(data);
      this.replies = data;
    });
  },
  methods: {
    moveBoard(no) {
      this.$router.push({
        name: "BoardView",
        params: { no: no },
      });
    },
  },
};
</script>

<style scoped>
.mypage-table-head {
  font-weight: bold;
  font-size: 20px;
  line-height: 80px;
}
.l-height-50 {
  line-height: 50px;
}
</style>

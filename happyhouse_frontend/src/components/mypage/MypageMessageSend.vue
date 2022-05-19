<template>
  <b-container class="bv-example-row mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 text-light">받은 쪽지</h2>
        <table class="text-light w-100">
          <tr style="font-weight: bold; font-size: 20px; line-height: 80px">
            <td>To</td>
            <td>제목</td>
            <td>내용</td>
            <td>보낸 시간</td>
          </tr>
          <tr
            v-for="message in messages"
            :key="message.messageid"
            style="line-height: 50px"
          >
            <td>{{ message.receive }}</td>
            <td>{{ message.title }}</td>
            <td>{{ message.content }}</td>
            <td>{{ message.regtime }}</td>
          </tr>
        </table>
      </div>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "MypageMessageSend",
  data() {
    return {
      userInfo: {
        userid: "",
        username: "",
        userpwd: "",
        email: "",
      },
      messages: [
        {
          messageid: "",
          send: "",
          receive: "",
          title: "",
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
    http.get(`/mypage/send/${this.userInfo.userid}`).then(({ data }) => {
      console.log(data);
      this.messages = data;
    });
  },
  methods: {},
};
</script>

<style scoped></style>

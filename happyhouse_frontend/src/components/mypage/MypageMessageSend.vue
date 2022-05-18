<template>
  <div>
    보낸 메시지함 화면
    <table>
      <tr>
        <td>To</td>
        <td>title</td>
        <td>content</td>
        <td>regtime</td>
      </tr>
      <tr v-for="message in messages" :key="message.messageid">
        <td>{{ message.receive }}</td>
        <td>{{ message.title }}</td>
        <td>{{ message.content }}</td>
        <td>{{ message.regtime }}</td>
      </tr>
    </table>
  </div>
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

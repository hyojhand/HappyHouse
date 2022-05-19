<template>
  <b-container class="bv-example-row mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 text-light">받은 쪽지</h2>
        <table class="text-light w-100">
          <tr style="font-weight: bold; font-size: 20px; line-height: 80px">
            <td>From</td>
            <td>제목</td>
            <td>내용</td>
            <td>받은 시간</td>
          </tr>
          <tr
            v-for="message in messages"
            :key="message.messageid"
            style="line-height: 50px"
          >
            <td>{{ message.send }}</td>
            <td>{{ message.title }}</td>
            <td>{{ message.content }}</td>
            <td>{{ message.regtime }}</td>
            <td>
              <b-button
                class="btn-light"
                size="sm"
                v-b-modal="'modal-' + message.messageid"
                ><b-icon icon="reply"></b-icon> 답장</b-button
              >
              <b-modal
                :id="'modal-' + message.messageid"
                :ref="'modal-' + message.messageid"
                title="쪽지 보내기"
                hide-footer
              >
                <b-form-group id="title-group">
                  <b-form-input
                    type="text"
                    id="title"
                    v-model="title"
                    placeholder="제목 입력"
                  ></b-form-input>
                </b-form-group>
                <b-form-group id="content-group">
                  <b-form-textarea
                    id="content"
                    v-model="content"
                    rows="5"
                    max-rows="10"
                    placeholder="내용 입력"
                  ></b-form-textarea>
                </b-form-group>
                <b-button
                  class="mt-3"
                  block
                  @click="
                    sendMsg(message.messageid, message.send, message.receive)
                  "
                  >send</b-button
                >
              </b-modal>
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
  name: "MypageMessageReceive",
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
      title: "",
      content: "",
    };
  },
  created() {
    this.userInfo = this.$route.params.userInfo;
    console.log(this.userInfo);
    http.get(`/mypage/receive/${this.userInfo.userid}`).then(({ data }) => {
      console.log(data);
      this.messages = data;
    });
  },
  methods: {
    sendMsg(no, send, receive) {
      console.log(this.msg);
      http
        .post(`/message`, {
          send: receive,
          receive: send,
          title: this.title,
          content: this.content,
        })
        .then(({ data }) => {
          let msg = "쪽지 전송시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "쪽지를 보냈습니다.";
          }
          alert(msg);
        });
      this.$refs[`modal-${no}`].hide();
    },
  },
};
</script>

<style scoped></style>

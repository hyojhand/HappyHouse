<template>
  <b-container class="bv-example-row">
    <div class="container">
      <div class="col-lg-11 mx-auto">
        <h2 class="p-3 mb-3 text-light">쪽지함</h2>
        <div v-for="message in messages" :key="message.messageid">
          <div v-if="message.send == userInfo.userid" class="text-light">
            <p class="ml-30">To. {{ message.receive }}</p>
            <div class="mypage-message-receive-template">
              <p>
                <b>{{ message.title }}</b>
              </p>
              <hr />
              <p>{{ message.content }}</p>
              <p class="text-right font-10">
                {{ message.regtime }}
                <b-button
                  variant="transparent"
                  size="sm"
                  @click="deleteMsg(message.messageid)"
                  ><b-icon icon="trash" variant="light"></b-icon
                ></b-button>
              </p>
            </div>
          </div>
          <div v-else>
            <p class="text-left text-light ml-4">
              From. {{ message.send }}
              <b-button
                variant="transparent"
                class="text-light"
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
            </p>
            <div class="mypage-message-send-template">
              <p>
                <b>{{ message.title }}</b>
              </p>
              <hr />
              <p>{{ message.content }}</p>
              <p class="text-left font-10">
                {{ message.regtime }}
                <b-button
                  variant="transparent"
                  size="sm"
                  @click="deleteMsg(message.messageid)"
                  ><b-icon icon="trash"></b-icon
                ></b-button>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "MypageMessage",
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
    http.get(`/mypage/message/${this.userInfo.userid}`).then(({ data }) => {
      console.log(data);
      this.messages = data;
    });
  },
  methods: {
    async sendMsg(no, send, receive) {
      await http
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
      this.$bvModal.hide(`modal-${no}`);
      http.get(`/mypage/message/${this.userInfo.userid}`).then(({ data }) => {
        this.messages = data;
        console.log(data);
      });
    },
    async deleteMsg(no) {
      await http
        .put(`/mypage/message`, { messageid: no, title: this.userInfo.userid })
        .then(({ data }) => {
          let msg = "쪽지 삭제시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "쪽지가 삭제되었습니다.";
          }
          alert(msg);
        });
      http.get(`/mypage/message/${this.userInfo.userid}`).then(({ data }) => {
        this.messages = data;
        console.log(data);
      });
    },
  },
};
</script>

<style scoped>
.mypage-message-receive-template {
  background-color: rgb(4, 125, 255);
  border-radius: 25px;
  margin-bottom: 20px;
  margin-left: 60%;
  width: 300px;
  padding: 10px 20px 1px;
}
.mypage-message-send-template {
  background-color: silver;
  border-radius: 25px;
  margin-bottom: 20px;
  width: 300px;
  padding: 10px 20px 1px;
}
.font-10 {
  font-size: 10px;
}
.ml-30 {
  margin-left: 30%;
}
</style>

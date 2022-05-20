<template>
  <b-dropdown variant="link" dropright :text="writer" no-caret v-if="!isWriter">
    <b-dropdown-item v-b-modal="'modal-' + no" v-if="isLogin"
      >쪽지 보내기</b-dropdown-item
    >
    <b-modal
      :id="'modal-' + no"
      :ref="'modal-' + no"
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
      <b-button class="mt-3" block @click="sendMsg(no)">send</b-button>
    </b-modal>
    <b-dropdown-item @click="searchBoard">회원글 더보기</b-dropdown-item>
  </b-dropdown>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
import jwt_decode from "jwt-decode";

export default {
  props: {
    writer: String,
    no: Number,
    isWriter: Boolean,
  },
  data() {
    return {
      send: "",
      title: "",
      content: "",
      userInfo: [],
    };
  },
  computed: {
    ...mapState(["isLogin"]),
  },
  async created() {
    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      await http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
      });
    }
  },
  methods: {
    sendMsg(no) {
      console.log(this.msg);
      http
        .post(`/message`, {
          send: this.userInfo.userid,
          receive: this.$props.writer,
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
    searchBoard() {
      http.get(`board/writer/${this.$props.writer}`).then(({ data }) => {
        this.$router.push({
          name: "BoardList",
          params: { articles: data, type: "search" },
        });
      });
    },
  },
};
</script>
<style>
button.dropdown-toggle {
  color: black;
}
</style>

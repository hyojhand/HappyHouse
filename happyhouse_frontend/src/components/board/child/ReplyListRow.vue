<template>
  <b-tr>
    <b-td>
      <writer-menu :writer="writer" :no="replyid"></writer-menu>
    </b-td>
    <b-td class="reply-table-content">{{ content }}</b-td>
    <b-td class="reply-table-date">{{ changeDateFormat }}</b-td>
    <b-td v-if="isWriter || isadmin">
      <b-button
        variant="light"
        size="sm"
        @click="modifyReply"
        class="mr-2"
        v-if="isModify"
        ><b-icon icon="pencil-square"></b-icon> 수정</b-button
      >
      <b-button
        variant="light"
        size="sm"
        @click="modifyReplyAction"
        class="mr-2"
        v-else
        ><b-icon icon="pencil-square"></b-icon> 수정</b-button
      ><b-button variant="dark" size="sm" @click="deleteReply"
        ><b-icon icon="trash"></b-icon> 삭제</b-button
      >
    </b-td>
  </b-tr>
</template>

<script>
import moment from "moment";
import WriterMenu from "@/components/board/WriterMenu.vue";
import http from "@/util/http-common";
import { mapState } from "vuex";
import jwt_decode from "jwt-decode";

export default {
  name: "BoardListRow",
  props: {
    replyid: Number,
    writer: String,
    content: String,
    regtime: String,
  },
  components: {
    WriterMenu,
  },
  data() {
    return {
      isWriter: false,
      isModify: true,
      nreplyid: "",
      ncontent: "",
    };
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
    if (this.$props.writer == this.userInfo.userid) {
      this.isWriter = true;
    }
  },
  computed: {
    ...mapState("memberStore", ["isLogin", "isadmin"]),
    changeDateFormat() {
      return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    },
  },
  methods: {
    modifyReply(e) {
      let tr = e.target.parentElement.parentElement;
      let original = tr.childNodes[1];
      original.innerHTML = `<input type='text' value="${original.innerText}" v-model="ncontent">`;
      this.isModify = false;
      this.nreplyid = this.$props.replyid;
    },
    modifyReplyAction(e) {
      let td = e.target.parentElement.parentElement.childNodes[1];
      let original = td.childNodes[0].value;
      http
        .put(`/reply`, {
          replyid: this.nreplyid,
          content: original,
          articleno: this.$route.params.no,
        })
        .then(({ data }) => {
          let msg = "수정이 완료되었습니다.";
          if (data === "fail") {
            msg = "수정 처리시 문제가 발생했습니다.";
          } else {
            td.innerHTML = original;
            this.isModify = true;
          }
          alert(msg);
        });
    },
    deleteReply(e) {
      let tr = e.target.parentElement.parentElement;
      http.delete(`/reply/${this.$props.replyid}`).then(({ data }) => {
        let msg = "삭제가 완료되었습니다.";
        if (data === "fail") {
          msg = "삭제 처리시 문제가 발생했습니다.";
        } else {
          tr.remove();
        }
        alert(msg);
      });
    },
  },
};
</script>

<style scoped>
.reply-table-content {
  width: 60%;
  line-height: 30px;
}
.reply-table-date {
  font-size: 13px;
  line-height: 30px;
}
</style>

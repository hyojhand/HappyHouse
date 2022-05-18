<template>
  <b-tr>
    <b-td>{{ replyid }}</b-td>
    <b-td>{{ content }}</b-td>
    <b-td>
      <writer-menu :writer="writer" :no="replyid"></writer-menu>
    </b-td>
    <b-td>{{ changeDateFormat }}</b-td>
    <b-td v-if="isWriter">
      <b-button
        variant="outline-info"
        size="sm"
        @click="modifyReply"
        class="mr-2"
        v-if="isModify"
        >수정</b-button
      >
      <b-button
        variant="outline-info"
        size="sm"
        @click="modifyReplyAction"
        class="mr-2"
        v-else
        >수정</b-button
      ><b-button variant="outline-danger" size="sm" @click="deleteReply"
        >삭제</b-button
      >
    </b-td>
  </b-tr>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js";
import WriterMenu from "@/components/board/WriterMenu.vue";
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
      isWriter: true,
      isModify: true,
      nreplyid: "",
      ncontent: "",
    };
  },
  computed: {
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
      this.nreplyid = tr.childNodes[0].innerText;
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
      this.nreplyid = tr.childNodes[0].innerText;
      http.delete(`/reply/${this.nreplyid}`).then(({ data }) => {
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

<style></style>

<template>
  <b-container class="bv-example-row mt-5">
    <b-row class="mb-1">
      <b-col cols="2"></b-col>
      <b-col style="text-align: left" cols="7">
        <b-form>
          <b-form-input
            id="reply-content"
            v-model="content"
            type="text"
            placeholder="댓글을 입력하세요"
            ref="input"
          ></b-form-input>
        </b-form>
      </b-col>
      <b-col cols="1">
        <b-button variant="secondary" @click="writeReply()" style="width: 100%"
          ><b-icon icon="vector-pen"></b-icon
        ></b-button>
      </b-col>
    </b-row>
    <b-row class="mt-5">
      <b-col v-if="replies.length">
        <b-table-simple hover responsive>
          <tbody>
            <reply-list-row
              v-for="(reply, index) in replies"
              :key="index"
              v-bind="reply"
            />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import ReplyListRow from "@/components/board/child/ReplyListRow";
import http from "@/util/http-common";
import { mapState } from "vuex";
import jwt_decode from "jwt-decode";

export default {
  name: "ReplyList",
  components: {
    ReplyListRow,
  },
  data() {
    return {
      replies: [],
      userInfo: [],
      content: "",
    };
  },
  computed: {
    ...mapState(["isLogin"]),
  },
  created() {
    http.get(`/reply/${this.$route.params.no}`).then(({ data }) => {
      this.replies = data;
    });

    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
      });
    }
  },
  methods: {
    writeReply() {
      if (this.isLogin) {
        http
          .post(`/reply/${this.$route.params.no}`, {
            articleno: this.$route.params.no,
            writer: this.userInfo.userid,
            content: this.content,
          })
          .then(({ data }) => {
            let msg = "댓글이 작성되었습니다.";
            if (data === "fail") {
              msg = "댓글 작성시 문제가 발생했습니다.";
            } else {
              this.replies = data;
              this.$refs.input.$el.value = "";
            }
            alert(msg);
          });
      } else {
        alert("댓글작성은 로그인이 필요합니다!");
        this.$router.push({ name: "SignIn" });
      }
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>

<template>
  <b-container class="mt-5">
    <b-row class="mb-1">
      <b-col style="text-align: left">
        <b-form @submit="onSubmit" @reset="onReset">
          <b-form-group id="subject-group" label="제목 :" label-for="title">
            <b-form-input
              id="title"
              v-model="article.title"
              type="text"
              ref="title"
              required
              placeholder="제목을 입력하세요"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="content-group" label="내용 :" label-for="content">
            <b-form-textarea
              id="content"
              v-model="article.content"
              placeholder="내용을 입력하세요"
              ref="content"
              rows="10"
              max-rows="15"
            ></b-form-textarea>
          </b-form-group>

          <b-button
            type="submit"
            variant="light"
            class="m-1"
            size="sm"
            v-if="this.type === 'register'"
            ><b-icon icon="vector-pen"></b-icon> 글작성</b-button
          >
          <b-button
            type="submit"
            variant="secondary"
            class="m-1"
            size="sm"
            v-else
            ><b-icon icon="pencil-square"></b-icon> 글수정</b-button
          >
          <b-button type="reset" variant="dark" class="m-1" size="sm"
            >초기화</b-button
          >
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";
import jwt_decode from "jwt-decode";

export default {
  name: "BoardWriteForm",
  data() {
    return {
      article: {
        articleno: 0,
        writer: "",
        title: "",
        content: "",
      },
      isWriter: false,
      userInfo: [],
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (!this.isLogin) {
      alert("글작성은 로그인이 필요합니다!");
      this.$router.push({ name: "SignIn" });
    }

    if (this.type === "modify") {
      http.get(`/board/${this.$route.params.no}`).then(({ data }) => {
        console.log(data);
        this.article = data;
      });
      this.isWriter = true;
    }

    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
      });
    }
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registArticle() {
      http
        .post(`/board`, {
          writer: this.userInfo.userid,
          title: this.article.title,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board/${this.article.articleno}`, {
          articleno: this.article.articleno,
          writer: this.userInfo.userid,
          title: this.article.title,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "BoardList" });
        });
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style></style>

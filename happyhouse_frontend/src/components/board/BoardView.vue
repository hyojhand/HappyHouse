<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <div v-if="isWriter">
          <b-button
            variant="outline-info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정</b-button
          >
          <b-button variant="outline-danger" size="sm" @click="deleteArticle"
            >글삭제</b-button
          >
        </div>
        <div v-else>
          <b-icon
            icon="heart-fill"
            variant="danger"
            v-if="isLike"
            @click="modifyLike"
          ></b-icon>
          <b-icon icon="heart" v-else @click="modifyLike"></b-icon>
        </div>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.articleno}.
          ${article.title}</h3><div><h6>${article.writer}</div><div>${changeDateFormat}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
        <reply-list></reply-list>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common";
import ReplyList from "@/components/board/child/ReplyList.vue";

export default {
  name: "BoardView",
  components: {
    ReplyList,
  },
  data() {
    return {
      article: {},
      isWriter: true,
      isLike: false,
    };
  },
  computed: {
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    changeDateFormat() {
      return moment(new Date(this.article.regtime)).format(
        "YYYY.MM.DD hh:mm:ss"
      );
    },
  },
  created() {
    http.get(`/board/${this.$route.params.no}`).then(({ data }) => {
      this.article = data;
    });
    http.get(`/board/like/${this.$route.params.no}`).then(({ data }) => {
      console.log(data);
      this.isLike = data;
    });
    // 작성자 본인인지 알려주는 axios 필요 (isWriter 변경)
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "BoardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "BoardUpdate",
        params: { no: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      console.log(this.article);
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "BoardDelete",
          params: { no: this.article.articleno },
        });
      }
    },
    modifyLike() {
      if (!this.isLike) {
        http.put(`/board/like/${this.article.articleno}`).then(({ data }) => {
          let msg = "좋아요 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "좋아요가 추가되었습니다.";
          }
          alert(msg);
          this.isLike = !this.isLike;
        });
      } else {
        http
          .delete(`/board/dislike/${this.article.articleno}`)
          .then(({ data }) => {
            let msg = "좋아요 취소 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "좋아요가 취소되었습니다.";
            }
            alert(msg);
            this.isLike = !this.isLike;
          });
      }
    },
  },
};
</script>

<style></style>

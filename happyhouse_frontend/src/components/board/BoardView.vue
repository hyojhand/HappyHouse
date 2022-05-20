<template>
  <div>
    <h1
      style="
        position: absolute;
        top: 120px;
        left: 50%;
        transform: translate(-50%, 0);
        color: white;
      "
    >
      {{ article.title }}
    </h1>
    <b-container class="bv-example-row mt-3">
      <b-row class="mb-1">
        <b-col>
          <b-card
            :header-html="'<h5>' + content + '</h5>'"
            class="mb-2"
            border-variant="dark"
            no-body
          >
            <b-card-body class="text-right">
              <div>
                <writer-menu
                  :writer="article.writer"
                  :no="article.articleno"
                ></writer-menu>
                | {{ article.regtime }}
              </div>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-left">
          <b-button size="sm" variant="secondary" @click="moveMainBoard"
            ><b-icon icon="grid1x2"></b-icon> 메인</b-button
          >
          <b-button
            size="sm"
            class="ml-2"
            variant="light"
            @click="moveListArticle"
            ><b-icon icon="list-task"></b-icon> 목록</b-button
          >
        </b-col>
        <b-col class="text-right">
          <div v-if="isWriter">
            <b-button
              variant="light"
              size="sm"
              @click="moveModifyArticle"
              class="mr-2"
              ><b-icon icon="pencil-square"></b-icon> 수정</b-button
            >
            <b-button variant="dark" size="sm" @click="deleteArticle"
              ><b-icon icon="trash"></b-icon> 삭제</b-button
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
      <reply-list></reply-list>
    </b-container>
  </div>
</template>

<script>
// import moment from "moment";
import ReplyList from "@/components/board/child/ReplyList.vue";
import WriterMenu from "@/components/board/WriterMenu.vue";
import http from "@/util/http-common";
import { mapState } from "vuex";
import jwt_decode from "jwt-decode";

export default {
  name: "BoardView",
  components: {
    ReplyList,
    WriterMenu,
  },
  data() {
    return {
      article: {},
      isWriter: false,
      isLike: false,
      userInfo: [],
    };
  },
  computed: {
    ...mapState(["isLogin"]),
    content() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
    // info() {
    //   return `<div><h6>${this.article.writer}</div><div>${changeDateFormat}</h6></div>`;
    // },
  },
  async created() {
    if (this.isLogin) {
      const decode = jwt_decode(sessionStorage.getItem("access-token"));
      http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
      await http.get(`/member/info/${decode.userid}`).then(({ data }) => {
        this.userInfo = data.userInfo;
        console.log(this.userInfo);
      });
    }

    await http.get(`/board/${this.$route.params.no}`).then(({ data }) => {
      this.article = data;
    });
    await http.get(`/board/like/${this.$route.params.no}`).then(({ data }) => {
      console.log(data);
      this.isLike = data;
    });

    // 사용자 정보 가져와서 사용자이름과 작성자가 같은지 확인

    if (this.article.writer === this.userInfo.userid) {
      this.isWriter = true;
    } else {
      this.isWriter = false;
    }
    // 작성자 본인인지 알려주는 axios 필요 (isWriter 변경)
  },
  methods: {
    moveListArticle() {
      this.$router.push({ name: "BoardList" });
    },
    moveMainBoard() {
      this.$router.push({ name: "Board" });
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
      if (this.isLogin) {
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
      } else {
        alert("좋아요는 로그인이 필요합니다!");
        this.$router.push({ name: "SignIn" });
      }
    },
  },
};
</script>

<style></style>

<template>
  <b-container class="bv-example-row mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 text-light">내가 쓴 글</h2>
        <table class="text-light w-100">
          <tr style="mypage-table-head">
            <td>제목</td>
            <td>내용</td>
            <td>좋아요</td>
            <td>작성 시간</td>
          </tr>
          <tr
            v-for="article in articles"
            :key="article.articleno"
            class="l-height-50"
          >
            <td>{{ article.title }}</td>
            <td class="mypage-board-content">
              {{ article.content }}
            </td>
            <td><b-icon icon="heart"></b-icon> {{ article.likey }}</td>
            <td>{{ article.regtime }}</td>
            <td>
              <b-button
                class="btn-light"
                size="sm"
                @click="moveBoard(article.articleno)"
                ><b-icon icon="reply"></b-icon> 바로가기</b-button
              >
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
  name: "MypageWriteBoard",
  data() {
    return {
      userInfo: {
        userid: "",
        username: "",
        userpwd: "",
        email: "",
      },
      articles: [
        {
          articleno: "",
          wrtier: "",
          title: "",
          content: "",
          regtime: "",
          likey: "",
          isdeleted: "",
        },
      ],
    };
  },
  created() {
    this.userInfo = this.$route.params.userInfo;
    console.log(this.userInfo);
    http.get(`/mypage/board/${this.userInfo.userid}`).then(({ data }) => {
      console.log(data);
      this.articles = data;
    });
  },
  methods: {
    moveBoard(no) {
      this.$router.push({
        name: "BoardView",
        params: { no: no },
      });
    },
  },
};
</script>

<style scoped>
td {
  padding: 10px;
}
.mypage-table-head {
  font-weight: bold;
  font-size: 20px;
  line-height: 80px;
}
.l-height-50 {
  line-height: 50px;
}
.mypage-board-content {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 200px;
}
</style>

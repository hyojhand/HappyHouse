<template>
  <b-container class="bv-example-row mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 text-light">내가 좋아요한 글</h2>
        <table class="text-light w-100">
          <tr style="font-weight: bold; font-size: 20px; line-height: 80px">
            <td>제목</td>
            <td style="width: 300px">내용</td>
          </tr>
          <tr
            v-for="article in articles"
            :key="article.articleno"
            style="line-height: 50px"
          >
            <td>{{ article.title }}</td>
            <td
              style="
                display: block;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                width: 300px;
              "
            >
              {{ article.content }}
            </td>
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
  name: "MypageLikeBoard",
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
    http.get(`/mypage/like/${this.userInfo.userid}`).then(({ data }) => {
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
</style>

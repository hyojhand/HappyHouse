<template>
  <b-container class="mt-3">
    <h2 class="board-head">게시판</h2>
    <b-form-input
      class="search-content"
      v-model="keyword"
      placeholder="검색어를 입력하세요"
      @keyup.enter="search"
    ></b-form-input>
    <b-button class="btn-light search-icon" @click="search"
      ><b-icon icon="search"></b-icon
    ></b-button>
    <b-row class="mb-5 mr-3">
      <b-col class="text-right">
        <b-button variant="secondary" size="sm" @click="moveMainBoard"
          ><b-icon icon="grid1x2"></b-icon> 메인</b-button
        >
        <b-button variant="dark" size="sm" class="ml-3" @click="moveWrite"
          ><b-icon icon="vector-pen"></b-icon> 글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="articles.length">
        <b-table-simple hover responsive>
          <tbody>
            <board-list-row
              v-for="(article, index) in articles"
              :key="index"
              v-bind="article"
            />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import BoardListRow from "@/components/board/child/BoardListRow";

export default {
  name: "BoardList",
  components: {
    BoardListRow,
  },
  data() {
    return {
      articles: [],
      keyword: "",
    };
  },
  created() {
    if (this.$route.params.type == "search") {
      this.articles = this.$route.params.articles;
    } else {
      http.get(`/board/list/recent`).then(({ data }) => {
        this.articles = data;
      });
    }
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    moveMainBoard() {
      this.$router.push({ name: "Board" });
    },
    search() {
      if (this.keyword == "") {
        alert("검색어를 입력하세요");
      }
      http.get(`/board/search/${this.keyword}`).then(({ data }) => {
        this.articles = data;
        console.log(data);
      });
    },
  },
};
</script>

<style scoped>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
.board-head {
  position: absolute;
  top: 120px;
  left: 50%;
  transform: translate(-50%, 0);
  color: white;
}
.search-content {
  position: absolute;
  top: 230px;
  right: 250px;
  width: 300px;
}
.search-icon {
  position: absolute;
  top: 230px;
  right: 200px;
}
</style>

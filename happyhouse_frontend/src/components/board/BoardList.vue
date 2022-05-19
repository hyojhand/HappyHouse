<template>
  <b-container class="bv-example-row mt-3">
    <h2
      style="
        position: absolute;
        top: 120px;
        left: 50%;
        transform: translate(-50%, 0);
        color: white;
      "
    >
      게시판
    </h2>
    <b-form-input
      style="position: absolute; top: 230px; right: 250px; width: 300px"
      v-model="keyword"
      placeholder="검색어를 입력하세요"
    ></b-form-input>
    <b-button
      style="position: absolute; top: 230px; right: 200px"
      class="btn-light"
      @click="search"
      ><b-icon icon="search"></b-icon
    ></b-button>
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
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="secondary" size="sm" @click="moveWrite()"
          ><b-icon icon="vector-pen"></b-icon> 글쓰기</b-button
        >
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

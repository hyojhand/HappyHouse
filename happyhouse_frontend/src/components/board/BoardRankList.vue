<template>
  <b-container class="bv-example-row mt-3">
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
    };
  },
  created() {
    http.get(`/board/list/${this.$props.type}`).then(({ data }) => {
      console.log(data);
      if (this.$props.type == "recent") {
        this.articles = data.slice(undefined, 7);
      } else {
        this.articles = data;
      }
    });
  },
  props: {
    type: String,
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 500px;
  text-align: left;
}
</style>

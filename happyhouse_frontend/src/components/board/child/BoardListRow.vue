<template>
  <b-tr>
    <b-td class="board-table-head">
      <router-link
        :to="{
          name: 'BoardView',
          params: { no: articleno },
        }"
        class="board-title"
        >{{ title }}</router-link
      >
    </b-td>
    <b-td class="board-table-body">
      {{ writer }}
    </b-td>
    <b-td class="board-table-body">{{ changeDateFormat }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";

export default {
  name: "BoardListRow",
  props: {
    articleno: Number,
    writer: String,
    title: String,
    regtime: String,
  },

  computed: {
    changeDateFormat() {
      return moment(new Date(this.regtime)).format("MM.DD hh:mm");
    },
  },
};
</script>

<style scoped>
.board-table-head {
  width: 50%;
  line-height: 40px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.board-title {
  font-weight: bold;
  font-size: 18px;
  color: black;
}
.board-table-body {
  width: 25%;
  line-height: 40px;
  font-size: 13px;
}
</style>

<template>
  <b-tr>
    <b-td>{{ articleno }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'BoardView', params: { no: articleno } }">{{
        title
      }}</router-link>
    </b-th>
    <b-td>
      <writer-menu :writer="writer" :no="articleno"></writer-menu>
    </b-td>
    <b-td>{{ changeDateFormat }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";
import WriterMenu from "@/components/board/WriterMenu.vue";

export default {
  name: "BoardListRow",
  props: {
    articleno: Number,
    writer: String,
    title: String,
    regtime: String,
  },
  components: {
    WriterMenu,
  },
  computed: {
    changeDateFormat() {
      return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    },
  },
};
</script>

<style></style>

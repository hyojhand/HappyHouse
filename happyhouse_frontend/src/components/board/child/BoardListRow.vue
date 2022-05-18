<template>
  <b-tr>
    <b-td style="width: 50%; line-height: 40px">
      <router-link
        :to="{ name: 'BoardView', params: { no: articleno } }"
        style="font-weight: bold; font-size: 18px; color: black"
        >{{ title }}</router-link
      >
    </b-td>
    <b-td style="width: 25%; line-height: 40px; font-size: 13px">
      {{ writer }}
    </b-td>
    <b-td style="width: 25%; line-height: 40px; font-size: 13px">{{
      changeDateFormat
    }}</b-td>
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

<style></style>

<template>
  <div>
    <b-container
      v-if="this.apts && this.apts.length != 0"
      class="bv-example-row mt-3"
    >
      <house-list-row
        v-for="(apt, index) in this.apts"
        :key="index"
        :apt="apt"
      />
    </b-container>
    <b-container v-else class="bv-example-row mt-3">
      <b-row>
        <b-col><b-alert show>아파트 목록이 없습니다.</b-alert></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import HouseListRow from "@/components/house/HouseListRow.vue";
import http from "@/util/http-common";

export default {
  name: "HouseList",
  components: {
    HouseListRow,
  },
  data() {
    return {
      apts: [],
    };
  },
  created() {
    console.log(this.$route.params.aptcode);
    http.get(`/map/apt/${this.$route.params.aptcode}`).then(({ data }) => {
      console.log(data);
      this.apts = data;
    });
  },
};
</script>

<style></style>

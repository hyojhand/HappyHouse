<template>
  <div class="mt-5">
    <div
      class="text-center"
      v-for="i in 3"
      :key="i"
      style="color: white; font-size: 20px; margin-bottom: 85px"
    >
      <h2>{{ cities[i - 1].address }}</h2>
    </div>
  </div>
</template>
<script>
import http from "@/util/http-common.js";
export default {
  data() {
    return {
      cities: [],
    };
  },
  created() {
    http.get(`/admin/search`).then(({ data }) => {
      this.cities = data;
    });
  },
};
</script>
<style></style>

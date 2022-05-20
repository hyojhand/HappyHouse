<template>
  <div class="mt-5">
    <div
      class="text-center mb-5"
      v-for="i in 3"
      :key="i"
      style="color: white; font-size: 20px"
    >
      <h2>{{ cities[i].address }}</h2>
    </div>
  </div>
</template>
<script>
import http from "@/util/http-common.js";
export default {
  data() {
    return {
      cities: [
        {
          dongCode: "",
          address: "",
          cnt: "",
        },
      ],
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

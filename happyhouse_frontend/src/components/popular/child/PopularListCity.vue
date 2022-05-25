<template>
  <div class="mt-5">
    <div
      class="text-center pop-content"
      v-for="city in cities"
      :key="city.dongCode"
    >
      <h2>{{ city.address }}</h2>
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
      this.cities = data.slice(undefined, 3);
    });
  },
};
</script>
<style scoped>
.pop-content {
  color: white;
  font-size: 20px;
  margin-bottom: 85px;
}
</style>

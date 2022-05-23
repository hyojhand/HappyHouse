<template>
  <div class="mt-5">
    <div class="text-center mb-5 pop-content" v-for="i in 3" :key="i">
      <h2>{{ apts[i - 1].apartmentName }}</h2>
      <span
        >{{ apts[i - 1].sidoName }} {{ apts[i - 1].gugunName }}
        {{ apts[i - 1].dong }}</span
      >
    </div>
  </div>
</template>
<script>
import http from "@/util/http-common.js";
export default {
  data() {
    return {
      apts: [],
    };
  },
  created() {
    http.get(`/admin/bookmark`).then(({ data }) => {
      this.apts = data;
      console.log("apts : ", this.apts);
    });
  },
};
</script>
<style scoped>
.pop-content {
  color: white;
  font-size: 20px;
}
</style>

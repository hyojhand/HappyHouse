<template>
  <b-container class="bv-example-row mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 text-light">검색 통계</h2>
        <table class="text-light w-100">
          <tr class="admin-table-head">
            <td>순위</td>
            <td>법정동코드</td>
            <td>주소</td>
            <td>검색횟수</td>
          </tr>
          <tr
            v-for="(code, index) in codes"
            :key="index"
            class="admin-table-body"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ code.dongCode }}</td>
            <td>{{ code.address }}</td>
            <td>{{ code.cnt }}</td>
          </tr>
        </table>
      </div>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "AdminSearch",
  data() {
    return {
      codes: [
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
      console.log(data);
      this.codes = data;
    });
  },
  methods: {},
};
</script>

<style scoped></style>

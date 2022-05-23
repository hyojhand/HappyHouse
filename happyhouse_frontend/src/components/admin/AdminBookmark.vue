<template>
  <b-container class="mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 text-light">북마크 통계</h2>
        <table class="text-light w-100">
          <tr class="admin-table-head">
            <td>순위</td>
            <td>아파트명</td>
            <td>주소</td>
          </tr>
          <tr
            v-for="(mark, index) in marks"
            :key="index"
            class="admin-table-body"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ mark.apartmentName }}</td>
            <td>{{ mark.sidoName }} {{ mark.gugunName }} {{ mark.dong }}</td>
          </tr>
        </table>
      </div>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "AdminBookmark",
  data() {
    return {
      marks: [
        {
          apartmentName: "",
          sidoName: "",
          gugunName: "",
          dong: "",
        },
      ],
    };
  },
  created() {
    http.get(`/admin/bookmark`).then(({ data }) => {
      console.log(data);
      this.marks = data;
    });
  },
  methods: {},
};
</script>

<style></style>

<template>
  <div id="admin-container">
    <b-row class="p-5 text-center">
      <div style="margin-top: 150px; margin-left: 100px">
        <div class="mt-5">
          <a class="text-light" @click="moveAdminMember">회원 관리</a>
        </div>
        <div class="mt-5">
          <a class="text-light" @click="moveAdminSearch">검색 통계</a>
        </div>
        <div class="mt-5">
          <a class="text-light" @click="moveAdminBookmark">북마크 통계</a>
        </div>
      </div>
      <b-col class="text-center align-self-center" style="margin-top: 30px">
        <router-view></router-view>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Admin",
  computed: {
    ...mapState("memberStore", ["isadmin"]),
  },
  created() {
    if (!this.isadmin) {
      alert("관리자만 접근가능한 페이지입니다.");
      this.$router.push({ name: "Home" });
    }
  },

  methods: {
    moveAdminMember() {
      this.$router.push({
        name: "AdminMember",
      });
    },
    moveAdminSearch() {
      this.$router.push({
        name: "AdminSearch",
      });
    },
    moveAdminBookmark() {
      this.$router.push({
        name: "AdminBookmark",
      });
    },
  },
};
</script>

<style>
#admin-container {
  background: url("../assets/admin.jpg");
  background-size: cover;
  background-attachment: fixed;
  width: 100%;
  min-height: 100vh;
}
.admin-table-head {
  font-weight: bold;
  font-size: 20px;
  line-height: 80px;
}
.admin-table-body {
  line-height: 50px;
}
</style>

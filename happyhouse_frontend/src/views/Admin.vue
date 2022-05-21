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
      <b-col class="text-center align-self-center" style="margin-top: 100px">
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

<style scoped>
#admin-container {
  background: url("../assets/admin.jpg") repeat-y;
  background-size: cover;
  width: 100%;
  min-height: 100vh;
}
</style>

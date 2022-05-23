<template>
  <b-container class="mt-3">
    <div class="text-center mt-3">
      <div class="col-lg-6 mx-auto">
        <h2 class="p-3 mb-3 text-light">마이 페이지</h2>
        <div>
          <div class="form-group text-left text-light">
            <label for="username">이름</label>
            <input
              type="text"
              class="form-control"
              id="username"
              name="username"
              v-model="username"
              ref="username"
            />
          </div>
          <div class="form-group text-left text-light">
            <label for="userid">아이디</label>
            <input
              type="text"
              class="form-control"
              id="userid"
              name="userid"
              ref="userid"
              v-model="userid"
              readonly
            />
            <div id="idresult" class="mt-1"></div>
          </div>
          <div class="form-group text-left text-light">
            <label for="userpwd">비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="userpwd"
              name="userpwd"
              v-model="userpwd"
              ref="userpwd"
              placeholder="비밀번호를 입력하세요"
            />
          </div>
          <div class="form-group text-left text-light">
            <label for="email">이메일</label>
            <input
              type="text"
              class="form-control"
              id="email"
              name="email"
              ref="email"
              v-model="email"
            />
          </div>
          <div class="form-group text-center float-right mt-3">
            <b-button @click="checkValue" class="btn btn-light" size="sm">
              회원정보 수정
            </b-button>
            <b-button
              @click="checkDelete"
              class="btn btn-secondary ml-3"
              size="sm"
            >
              회원 탈퇴
            </b-button>
          </div>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import http from "@/util/http-common";
export default {
  name: "MypageMember",

  data() {
    return {
      userInfo: {},
      userid: "",
      username: "",
      userpwd: "",
      email: "",
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
  },
  created() {
    this.userInfo = this.$route.params.userInfo;
    this.userid = this.userInfo.userid;
    this.username = this.userInfo.username;
    this.email = this.userInfo.email;
    console.log("넘어온 데이터.");
    console.log(this.$route.params.userInfo);
  },
  methods: {
    checkValue() {
      let err = true;
      let msg = "";
      !this.username &&
        ((msg = "이름을 입력해주세요"),
        (err = false),
        this.$refs.username.focus());
      err &&
        !this.userid &&
        ((msg = "아이디를 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.userpwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.focus());
      err &&
        !this.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.focus());

      if (!err) alert(msg);
      else this.updateUser();
    },
    updateUser() {
      http
        .put("/member", {
          userid: this.userid,
          userpwd: this.userpwd,
          username: this.username,
          email: this.email,
        })
        .then(({ data }) => {
          if (data == "success") {
            alert("수정완료!");
          } else {
            alert("수정실패!");
          }
          this.$router.go(this.$router.currentRoute);
        });
    },
    checkDelete() {
      let err = true;
      let msg = "";
      err &&
        !this.userpwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.focus());

      if (!err) alert(msg);
      else {
        if (confirm("회원 탈퇴하시겠습니까?")) {
          this.deleteUser();
        }
      }
    },
    deleteUser() {
      http.delete(`/member/${this.userid}`).then(({ data }) => {
        console.log(data);
        if (data === "success") {
          alert("탈퇴 완료!");
          sessionStorage.removeItem("access-token");
          this.$store.dispatch("setIsNotLogin");
          this.$router.push({ name: "Home" });
        } else {
          alert("탈퇴 중 오류가 발생했습니다.");
        }
      });
    },
  },
};
</script>

<style scoped></style>

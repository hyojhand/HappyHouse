<template>
  <b-container class="bv-example-row mt-3">
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 shadow bg-light">
          <mark class="orange">마이 페이지</mark>
        </h2>
        <div>
          <div class="form-group text-left">
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
          <div class="form-group text-left">
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
          <div class="form-group text-left">
            <label for="userpwd">비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="userpwd"
              name="userpwd"
              v-model="userpwd"
              ref="userpwd"
              placeholder="비밀번호입력..."
            />
          </div>
          <div class="form-group text-left">
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
          <div class="form-group text-center">
            <button @click="checkValue" class="btn btn-outline-primary">
              회원정보 수정
            </button>
            <button @click="deleteUser" class="btn btn-outline-danger">
              회원 탈퇴
            </button>
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
    ...mapState(["isLogin"]),
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
    deleteUser() {},
  },
};
</script>

<style scoped></style>

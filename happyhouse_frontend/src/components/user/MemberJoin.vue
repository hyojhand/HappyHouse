<template>
  <div id="join-container">
    <div style="margin: auto; width: 600px">
      <div style="padding: 100px">
        <h2 class="text-light p-5">회원가입</h2>
        <div class="form-group text-left">
          <label for="username" class="text-light">이름</label>
          <input
            type="text"
            class="form-control"
            id="username"
            name="username"
            v-model="username"
            ref="username"
            placeholder="이름 입력"
          />
        </div>
        <div class="form-group text-left">
          <label for="userid" class="text-light">아이디</label>
          <input
            type="text"
            class="form-control"
            id="userid"
            name="userid"
            v-model="userid"
            ref="userid"
            placeholder="아이디 입력"
          />
          <div id="idresult" class="mt-1"></div>
        </div>
        <div class="form-group text-left">
          <label for="userpwd" class="text-light">비밀번호</label>
          <input
            type="password"
            class="form-control"
            id="userpwd"
            name="userpwd"
            v-model="userpwd"
            ref="userpwd"
            placeholder="비밀번호 입력"
          />
        </div>
        <div class="form-group text-left">
          <label for="email" class="text-light">이메일</label>
          <input
            type="text"
            class="form-control"
            id="email"
            name="email"
            v-model="email"
            ref="email"
            placeholder="이메일 입력"
          />
        </div>

        <div class="form-group text-center mt-5">
          <b-button @click="checkValue" class="btn-light mr-3">
            회원가입
          </b-button>
          <b-button type="reset" class="btn-dark">초기화</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "MemberJoin",
  data() {
    return {
      username: "",
      userid: "",
      userpwd: "",
      email: "",
    };
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
      else this.registUser();
    },
    registUser() {
      http
        .post("/member", {
          username: this.username,
          userid: this.userid,
          userpwd: this.userpwd,
          email: this.email,
        })
        .then(({ data }) => {
          let msg = "회원가입중 오류가 생겼습니다!";
          if (data === "success") {
            msg = "회원가입 완료!";
          }
          alert(msg);
          this.moveLogin();
        });
    },
    moveLogin() {
      this.$router.push({ name: "SignIn" });
    },
  },
};
</script>

<style scope>
#join-container {
  background: url("../../assets/join.jpg") repeat-y;
  background-size: cover;
  width: 100%;
  min-height: 100vh;
}
</style>

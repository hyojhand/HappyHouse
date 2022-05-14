<template>
  <b-container class="bv-example-row mt-3">
    <!-- <b-row>
      <b-col>
        <b-alert variant="secondary" class="shadow bg-light" show
          ><h2>회원가입</h2></b-alert
        >
      </b-col>
    </b-row> -->
    <div class="container text-center mt-3">
      <div class="col-lg-8 mx-auto">
        <h2 class="p-3 mb-3 shadow bg-light">
          <mark class="orange">회원가입</mark>
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
              placeholder="이름입력..."
            />
          </div>
          <div class="form-group text-left">
            <label for="userid">아이디</label>
            <input
              type="text"
              class="form-control"
              id="userid"
              name="userid"
              v-model="userid"
              ref="userid"
              placeholder="아이디입력..."
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
          <!-- <div class="form-group">
            <label for="pwcheck">비밀번호재입력</label>
            <input
              type="password"
              class="form-control"
              id="pwcheck"
              name="pwcheck"
              v-model="pwcheck"
              ref="pwcheck"
              placeholder="비밀번호재입력..."
            />
          </div> -->
          <div class="form-group text-left">
            <label for="email">이메일</label>
            <input
              type="text"
              class="form-control"
              id="email"
              name="email"
              v-model="email"
              ref="email"
              placeholder="이메일 입력..."
            />
          </div>

          <!-- <div class="form-group text-left">
            <label for="tel">전화번호</label>
            <input
              type="text"
              class="form-control"
              id="tel"
              name="tel"
              v-model="tel"
              ref="tel"
              placeholder="번호 입력..."
            />
          </div> -->

          <div class="form-group text-center">
            <button @click="checkValue" class="btn btn-outline-primary">
              회원가입
            </button>
            <button type="reset" class="btn btn-outline-danger">초기화</button>
          </div>
        </div>
      </div>
    </div>
  </b-container>
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
      // err &&
      //   !this.pwcheck &&
      //   ((msg = "비번확인 입력해주세요"),
      //   (err = false),
      //   this.$refs.pwcheck.focus());
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
          let msg = "회원가입에 오류가 생겼습니다!";
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

<style></style>

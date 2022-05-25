<template>
  <div id="login-container">
    <div class="login-container">
      <h1 class="text-light p-5">로그인</h1>
      <b-form class="text-left mt-5 login-form">
        <b-form-group label="아이디 :" label-for="userid" class="text-light">
          <b-form-input
            id="userid"
            v-model="userid"
            ref="userid"
            required
            placeholder="아이디 입력"
            @keyup.enter="checkValue"
          ></b-form-input>
        </b-form-group>
        <b-form-group label="비밀번호 :" label-for="userpwd" class="text-light">
          <b-form-input
            type="password"
            id="userpwd"
            v-model="userpwd"
            ref="userpwd"
            required
            placeholder="비밀번호 입력"
            @keyup.enter="checkValue"
          ></b-form-input>
        </b-form-group>
        <b-form-checkbox v-model="remember" value="remember" class="text-light"
          >아이디 기억하기</b-form-checkbox
        >
        <b-button
          type="button"
          variant="secondary"
          class="mt-3 float-right"
          size="sm"
          @click="moveJoin"
          >회원가입</b-button
        >
        <b-button
          type="button"
          variant="light"
          class="mt-3 mr-2 float-right"
          size="sm"
          @click="checkValue"
          >로그인</b-button
        >
      </b-form>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "MemberLogin",
  data() {
    return {
      userid: this.$cookies.get("userid"),
      userpwd: "",
      remember: this.$cookies.get("remember"),
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
  },
  methods: {
    ...mapActions("memberStore", ["goLogin", "setAlarm"]),
    checkValue() {
      let msg = "";
      let err = true;
      !this.userid &&
        ((msg = "아이디를 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.userpwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.focus());

      if (!err) this.makeErrToast(msg);
      else {
        this.startLogin();
      }
    },
    async startLogin() {
      var user = {
        userid: this.userid,
        userpwd: this.userpwd,
      };
      await this.goLogin(user);
      if (this.remember) {
        if (!this.$cookies.get("userid")) {
          this.$cookies.set("userid", this.userid);
          this.$cookies.set("remember", "remember");
        }
      } else {
        this.$cookies.remove("userid");
        this.$cookies.remove("remember");
      }
      if (this.isLogin) {
        this.setAlarm(true);
        this.$router.push({ name: "Home" });
        this.$router.go(this.$router.currentRoute);
      }
    },
    moveJoin() {
      this.$router.push({ name: "SignUp" });
    },
    makeErrToast(text) {
      this.$bvToast.toast(text, {
        title: "경고",
        variant: "danger",
        toaster: "b-toaster-top-center",
        autoHideDelay: 1000,
        solid: true,
      });
    },
  },
};
</script>

<style scoped>
#login-container {
  background: url("../../assets/login.jpg") repeat-y;
  background-size: cover;
  width: 100%;
  min-height: 100vh;
}
.login-container {
  padding-top: 100px;
}
.login-form {
  width: 300px;
  margin: auto;
}
</style>

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
      userid: "",
      userpwd: "",
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
  },
  methods: {
    ...mapActions("memberStore", ["goLogin"]),
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

      if (!err) alert(msg);
      else {
        console.log("로그인 시도");
        this.startLogin();
      }
    },
    async startLogin() {
      var user = {
        userid: this.userid,
        userpwd: this.userpwd,
      };
      await this.goLogin(user);
      console.log(this.isLogin);
      if (this.isLogin) {
        this.$router.push({ name: "Home" });
        this.$router.go(this.$router.currentRoute);
      }
    },
    moveJoin() {
      this.$router.push({ name: "SignUp" });
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

<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <!-- <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            > -->
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="userid"
                ref="userid"
                required
                placeholder="아이디 입력...."
                @keyup.enter="checkValue"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="userpwd"
                ref="userpwd"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="checkValue"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="checkValue"
              >로그인</b-button
            >
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="moveJoin"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import { mapActions, mapState } from "vuex";

export default {
  name: "MemberLogin",
  data() {
    return {
      // isLoginError: false,
      // user: {
      userid: "",
      userpwd: "",
      // },
    };
  },
  computed: {
    ...mapState(["isLogin"]),
  },
  methods: {
    ...mapActions(["setIsLogin", "setIsNotLogin", "goLogin"]),
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
        // this.userLogin();
        this.startLogin();
      }
    },
    async startLogin() {
      var user = {
        userid: this.userid,
        userpwd: this.userpwd,
      };
      await this.goLogin(user);
      console.log("로직끝");
      console.log(this.isLogin);
      this.$router.push({ name: "Home" });
    },
    userLogin() {
      console.log("로그인실행");

      http
        .post("/member/login", {
          userid: this.userid,
          userpwd: this.userpwd,
        })
        .then(({ data }) => {
          console.log(data);
          // console.log(data.message);
          const token = data["access-token"];
          // console.log(token);
          if (data.message === "success") {
            alert("로그인 완료!!!");
            sessionStorage.setItem("access-token", token);
            console.log(sessionStorage.getItem("access-token"));
            // this.$store.dispatch("isLogin");
            console.log("실행1");
            this.setIsLogin;
            console.log("실행2");
            console.log(this.isLogin);
            this.$router.push({ name: "Home" });
          } else {
            alert("로그인실패! 아이디&비밀번호를 확인해주세요");
            // this.$store.dispatch("isNotLogin");
            this.setIsNotLogin;
            console.log(this.isLogin);
          }

          // 유저 정보 가져오기
          // http.defaults.headers["access-token"] =
          //   sessionStorage.getItem("access-token");
          // http.get(`/member/info/${this.userid}`).then(({ data }) => {
          //   console.log(data);
          // });

          // 얜 버리기직전
          // http.get("/member/check").then(({ data }) => {
          //   if (data === "success") {
          //     this.isLogin = true;
          //     console.log("로그인 true");
          //   } else {
          //     this.isLogin = false;
          //     console.log("로그인 false");
          //   }
          // });
        });
    },
    moveJoin() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>

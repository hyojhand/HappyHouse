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
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
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

export default {
  name: "MemberLogin",
  data() {
    return {
      isLoginError: false,
      // user: {
      userid: "",
      userpwd: "",
      // },
    };
  },
  methods: {
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
        this.userLogin();
      }
    },
    userLogin() {
      console.log("로그인실행");
      http
        .post(`/member/${this.userid}`, {
          userid: this.userid,
          userpwd: this.userpwd,
        })
        .then(({ data }) => {
          console.log(data);
          if (data === "fail") {
            alert("로그인실패! 아이디&비밀번호를 확인해주세요");
          } else {
            alert("로그인 완료!!!");
            this.$router.push({ name: "Home" });
          }

          http.get("/member/check").then(({ data }) => {
            if (data === "success") {
              this.isLogin = true;
              console.log("로그인 true");
            } else {
              this.isLogin = false;
              console.log("로그인 false");
            }
          });
        });
    },
    moveJoin() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>

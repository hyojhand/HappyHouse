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
import { mapState } from "vuex";

export default {
  name: "MemberLogin",
  data() {
    return {
      userid: "",
      userpwd: "",
    };
  },
  computed: {
    ...mapState(["isLogin"]),
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
        this.startLogin();
      }
    },
    async startLogin() {
      var user = {
        userid: this.userid,
        userpwd: this.userpwd,
      };
      await this.$store.dispatch("goLogin", user);
      console.log(this.isLogin);
      if (this.isLogin) {
        this.$router.push({ name: "Home" });
      }
    },

    // 유저 정보 가져오기
    // http.defaults.headers["access-token"] =
    //   sessionStorage.getItem("access-token");
    // http.get(`/member/info/${this.userid}`).then(({ data }) => {
    //   console.log(data);
    // });

    moveJoin() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>

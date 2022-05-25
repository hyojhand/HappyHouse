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

        <!-- <div>
          <b-button @click="$bvToast.show('my-toast')">Show toast</b-button>
          <b-toast id="my-toast" variant="warning" solid>
            <template #toast-title>
              <div class="d-flex flex-grow-1 align-items-baseline">
                <b-img
                  blank
                  blank-color="#ff5555"
                  class="mr-2"
                  width="12"
                  height="12"
                ></b-img>
                <strong class="mr-auto">Notice!</strong>
                <small class="text-muted mr-2">now</small>
              </div>
            </template>
            This is the content of the toast. It is short and to the point.
          </b-toast>
        </div> -->
      </b-form>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import "mosha-vue-toastify/dist/style.css";
// import { createToast } from "mosha-vue-toastify";

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
  created() {
    () => {
      this.$bvToast.show("my-toast");
    };
    // this.userid = this.$cookies.get("userid");
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
      console.log(this.remember);
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
        this.toast();
        // this.$bvToast.show("my-toast");

        // this.$toast.info("my-toast");
        // const toast = () => {
        //   createToast({
        //     title: "이 지역은 어떠신가요??",
        //     description: "부산시 금정구 부곡동",
        //   });
        // };
        this.$router.push({ name: "Home" });
        this.$router.go(this.$router.currentRoute);

        this.setAlarm(true);
      }
    },
    toast() {
      console.log("toatst 메서드");
      this.$bvToast.show(`Toast with action link`, {
        href: "#foo",
        title: "Example",
      });
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

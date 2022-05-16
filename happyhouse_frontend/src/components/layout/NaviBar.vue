<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/logo.png"
            class="d-inline-block align-middle"
            width="90px"
            alt="Kitten"
          />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#"
            ><router-link
              :to="{ name: 'Home' }"
              class="link"
              style="color: white"
              ><b-icon icon="house" font-scale="1"></b-icon> HOME</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link
              :to="{ name: 'Board' }"
              class="link"
              style="color: white"
              ><b-icon icon="journal" font-scale="1"></b-icon>
              게시판</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Instargram' }" class="link"
              ><b-icon icon="instagram" font-scale="1"></b-icon>
              인스타그램</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'House' }" class="link"
              ><b-icon icon="house-fill" font-scale="1"></b-icon>
              아파트정보</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-if="isLogin">
          <b-nav-item href="#"
            ><a href="#" class="link" @click="logout"
              ><b-icon icon="person-circle"></b-icon> 로그아웃
            </a></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Home' }" class="link"
              ><b-icon icon="key"></b-icon> 마이 페이지</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-else>
          <b-nav-item href="#"
            ><router-link :to="{ name: 'SignUp' }" class="link"
              ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'SignIn' }" class="link"
              ><b-icon icon="key"></b-icon> 로그인</router-link
            ></b-nav-item
          >
          <!-- <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'SignUp' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'SignIn' }" class="link"
                ><b-icon icon="key"></b-icon> 로그인</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown> -->
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "NaviBar",
  data() {
    return { isLogin: true };
  },
  created() {
    console.log("로그인 체크 시작");
    http.get("/member/check").then(({ data }) => {
      if (data === "success") {
        this.isLogin = true;
        console.log("로그인 true");
      } else {
        this.isLogin = false;
        console.log("로그인 false");
      }
    });
  },
  methods: {
    logout() {
      console.log("로그아웃 시작");
      http.get("/member/logout");
      alert("로그아웃 성공!");
      this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style></style>

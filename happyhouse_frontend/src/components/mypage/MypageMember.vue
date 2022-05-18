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
              value="{userInfo_message.userInfo.userid}"
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
              v-model="email"
              ref="email"
              placeholder="이메일 입력..."
            />
          </div>
          <div class="form-group text-center">
            <button @click="checkValue" class="btn btn-outline-primary">
              회원정보수정
            </button>
            <button type="reset" class="btn btn-outline-danger">
              회원탈퇴
            </button>
          </div>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
export default {
  name: "MypageMember",
  computed: {},
  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style scoped></style>

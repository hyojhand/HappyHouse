<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col style="text-align: left">
        <b-form>
          <b-form-group
            id="reply-content-group"
            label="댓글 : "
            label-for="reply-content"
            description="댓글을 입력하세요."
          ></b-form-group>
          <b-form-input
            id="reply-content"
            v-model="content"
            type="text"
            placeholder="댓글을 입력하세요"
            ref="input"
          ></b-form-input>
        </b-form>
        <b-button variant="outline-primary" @click="writeReply()"
          >작성</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="replies.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th>댓글 번호</b-th>
              <b-th>댓글</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <reply-list-row
              v-for="(reply, index) in replies"
              :key="index"
              v-bind="reply"
            />
          </tbody>
        </b-table-simple>
      </b-col>
      <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import ReplyListRow from "@/components/board/child/ReplyListRow";

export default {
  name: "ReplyList",
  components: {
    ReplyListRow,
  },
  data() {
    return {
      replies: [],
      content: "",
    };
  },
  created() {
    http.get(`/reply/${this.$route.params.no}`).then(({ data }) => {
      this.replies = data;
    });
  },
  methods: {
    writeReply() {
      http
        .post(`/reply/${this.$route.params.no}`, {
          articleno: this.$route.params.no,
          writer: "ssafy",
          content: this.content,
        })
        .then(({ data }) => {
          let msg = "댓글이 작성되었습니다.";
          if (data === "fail") {
            msg = "댓글 작성시 문제가 발생했습니다.";
          } else {
            this.replies = data;
            this.$refs.input.$el.value = "";
          }
          alert(msg);
        });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>

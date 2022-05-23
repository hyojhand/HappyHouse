<template>
  <div>
    <h1 class="board-common-head">글쓰기</h1>
    <board-write-form type="register" />
  </div>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardWrite",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>

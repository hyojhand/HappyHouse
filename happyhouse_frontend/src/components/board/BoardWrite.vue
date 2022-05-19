<template>
  <div>
    <h1
      style="
        position: absolute;
        top: 120px;
        left: 50%;
        z-index: 10;
        color: white;
      "
    >
      글쓰기
    </h1>
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

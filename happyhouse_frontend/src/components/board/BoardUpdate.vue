<template>
  <div>
    <h1 class="board-common-head">글수정</h1>
    <board-write-form type="modify" />
  </div>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardUpdate",
  components: {
    BoardWriteForm,
  },
};
</script>

<style>
.board-common-head {
  position: absolute;
  top: 120px;
  left: 50%;
  z-index: 10;
  color: white;
}
</style>

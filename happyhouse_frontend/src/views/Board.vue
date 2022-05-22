<template>
  <div class="bv-example-row text-center" style="position: relative">
    <div style="height: 300px; overflow: hidden">
      <img src="@/assets/board.jpg" style="width: 100%" />
    </div>

    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: "Board",
};
</script>
<style scoped></style>

<template>
  <div id="house-background">
    <div class="text-center house-view">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
export default {
  name: "House",
};
</script>
<style scoped>
#house-background {
  background: url("../assets/house_background.jpg");
  background-size: cover;
  background-attachment: fixed;
  width: 100%;
  min-height: 100vh;
}
.house-view {
  opacity: 1;
  color: white;
}
</style>

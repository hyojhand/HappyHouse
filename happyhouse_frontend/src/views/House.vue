<template>
  <div id="house-background">
    <b-container class="bv-example-row text-center house-view">
      <b-row>
        <b-col>
          <router-view></router-view>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
export default {
  name: "House",
};
</script>
<style scoped>
#house-background {
  background: url("../assets/house_background.jpg") repeat-y;
  background-size: cover;
  width: 100%;
  min-height: 100vh;
}
.house-view {
  opacity: 1;
  color: white;
}
</style>

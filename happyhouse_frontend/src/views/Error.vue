<template>
  <div
    id="error-container"
    class="
      text-light
      d-flex
      align-items-center
      justify-content-center
      flex-column
    "
  >
    <p
      style="font-weight: bold; font-size: 40px; border-bottom: 1px solid white"
    >
      404 ERROR
    </p>
    <p>Page Not Found</p>
    <router-link :to="{ name: 'Home' }" style="color: lightgray"
      >Go to Home</router-link
    >
  </div>
</template>

<script>
export default {
  name: "Error",
};
</script>

<style scoped>
#error-container {
  background: url("../assets/error.jpg");
  background-size: cover;
  width: 100%;
  min-height: 100vh;
}
</style>

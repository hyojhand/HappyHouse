<template>
  <div>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="10000"
      controls
      indicators
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- 지도로 가기 -->
      <b-carousel-slide
        caption="Where is Your Happy House?"
        text="Let me show you what we got for you"
      >
        <template v-slot:img>
          <img
            style="max-width: 100vw; max-height: 100vw"
            class="class-name"
            src="@/assets/house.jpg"
            alt="image slot"
          />
        </template>
        <b-button class="mb-3" @click="moveHouse"
          ><b-icon icon="cursor"></b-icon
        ></b-button>
      </b-carousel-slide>
      <!-- 게시판으로 가기 -->
      <b-carousel-slide
        caption="Anything you want to say to everyone"
        text="We are waiting for your writing"
      >
        <template v-slot:img>
          <img
            style="max-width: 100vw; max-height: 100vw"
            class="class-name"
            src="@/assets/posting.jpg"
            alt="image slot"
          />
        </template>
        <b-button class="mb-3" @click="moveBoard"
          ><b-icon icon="cursor"></b-icon
        ></b-button>
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
export default {
  name: "Main",
  props: {
    msg: String,
  },
  methods: {
    moveHouse() {
      this.$router.push({ name: "House" });
    },
    moveBoard() {
      this.$router.push({ name: "Board" });
    },
  },
};
</script>

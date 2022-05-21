<template>
  <div>
    <b-carousel id="carousel-1" :interval="10000" controls indicators>
      <!-- 지도로 가기 -->
      <b-carousel-slide
        caption="Where is your HappyHouse?"
        text="Let us show you what we got only for you"
      >
        <template v-slot:img>
          <img
            style="width: 100vw; height: 100vh"
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
            style="width: 100vw; height: 100vh"
            class="class-name"
            src="@/assets/posting.jpg"
            alt="image slot"
          />
        </template>
        <b-button class="mb-3" @click="moveBoard"
          ><b-icon icon="cursor"></b-icon
        ></b-button>
      </b-carousel-slide>
      <!-- 뉴스로 가기 -->
      <b-carousel-slide>
        <template v-slot:img>
          <img
            style="width: 100vw; height: 100vh"
            class="news-img"
            src="@/assets/news.jpg"
            alt="image slot"
          />
        </template>
        <news-list></news-list>
      </b-carousel-slide>
      <!-- 최다 검색 -->
      <b-carousel-slide>
        <template v-slot:img>
          <img
            style="width: 100vw; height: 100vh"
            class="news-img"
            src="@/assets/hot.jpg"
            alt="image slot"
          />
        </template>
        <popular-list></popular-list>
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
import NewsList from "@/components/news/NewsList.vue";
import PopularList from "@/components/popular/PopularList.vue";

export default {
  name: "Main",
  props: {
    msg: String,
  },
  components: {
    NewsList,
    PopularList,
  },
  methods: {
    moveHouse() {
      this.$router.push({ name: "House" });
    },
    moveBoard() {
      this.$router.push({ name: "Board" });
    },
    moveNews() {
      this.$router.push({ name: "News" });
    },
  },
};
</script>
<style>
.carousel-caption {
  top: 40%;
}
.news-img + div {
  top: 5%;
}
</style>

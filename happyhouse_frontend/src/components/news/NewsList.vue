<template>
  <div>
    <h1>BREAK NEWS</h1>
    <div class="mt-5">
      <b-card-group deck>
        <b-card text-variant="dark" class="text-center" v-for="i in 3" :key="i">
          <h6 v-html="news[i - 1].title"></h6>
          <b-card-text
            v-html="news[i - 1].description"
            style="font-size: 10px"
          ></b-card-text>
          <a :href="news[i - 1].link">See More...</a>
        </b-card>
      </b-card-group>
    </div>
    <div class="mt-3">
      <b-card-group deck>
        <b-card text-variant="dark" class="text-center" v-for="i in 3" :key="i">
          <h6 v-html="news[i + 2].title"></h6>
          <b-card-text
            v-html="news[i + 2].description"
            style="font-size: 10px"
          ></b-card-text>
          <a :href="news[i + 2].link">See More...</a>
        </b-card>
      </b-card-group>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
export default {
  created() {
    http.get(`/news`).then(({ data }) => {
      this.news = data.items;
    });
  },
  data() {
    return {
      news: [
        {
          title: "",
          description: "",
          link: "",
          originallink: "",
          pubDate: "",
        },
      ],
    };
  },
};
</script>

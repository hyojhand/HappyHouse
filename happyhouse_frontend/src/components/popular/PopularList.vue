<template>
  <div>
    <h1>HOT</h1>
    <b-row>
      <b-col class="mt-5"
        >최다 검색 지역<popular-list-city></popular-list-city
      ></b-col>
      <b-col class="mt-5"
        >최다 북마크 아파트<popular-list-bookmark></popular-list-bookmark
      ></b-col>
    </b-row>
  </div>
</template>

<script>
import PopularListCity from "@/components/popular/child/PopularListCity.vue";
import PopularListBookmark from "@/components/popular/child/PopularListBookmark.vue";
export default {
  components: {
    PopularListCity,
    PopularListBookmark,
  },
};
</script>

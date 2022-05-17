<template>
  <b-row
    class="m-2"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <b-img
        thumbnail
        src="https://picsum.photos/250/250/?image=58"
        alt="Image 1"
      ></b-img>
    </b-col>
    <b-col cols="10" class="align-self-center text-left">
      <div class="AptName">
        <strong>{{ apt.aptName }} 아파트</strong>
      </div>
      <div>
        주소 : {{ apt.sidoName }} {{ apt.gugunName }} {{ apt.dongName }}
      </div>
      <div>건축년도 : {{ apt.buildYear }} 년</div>
      <div>거래가 : {{ apt.recentPrice }} 만원</div>
      <div>거래가 : {{ apt.floor }} 층</div>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "HouseListRow",
  props: {
    apt: Object,
  },
  data() {
    return {
      isColor: false,
    };
  },
  methods: {
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.AptName {
  font-size: 20px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>

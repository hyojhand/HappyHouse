<template>
  <b-row
    class="mb-3"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    @click="selectApt"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="6" class="text-center align-self-center">
      <b-img
        class="apartImg"
        thumbnail
        :src="require(`@/assets/apart_img/apart${random_num}.jpg`)"
        fluid-grow
      ></b-img>
    </b-col>
    <b-col cols="6" class="align-self-center text-left">
      <div class="AptName">
        <strong>{{ apt.aptName }} </strong>
      </div>
      <div>
        <strong>주소</strong> : {{ apt.sidoName }} {{ apt.gugunName }}
        {{ apt.dongName }}
      </div>
      <div><strong>건축년도</strong> : {{ apt.buildYear }} 년</div>
      <div><strong>거래가</strong> : {{ apt.recentPrice }} 만원</div>
      <div><strong>평수</strong> : {{ apt.floor }} 평</div>
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
      random_num: Math.ceil(Math.random() * 20),
      // temp: { apt: this.apt, random_num: Math.ceil(Math.random() * 12) },
      isColor: false,
    };
  },
  methods: {
    selectApt() {
      this.$store.dispatch("selectApt", this.apt);
      this.$store.dispatch("selectApartImgNum", this.random_num);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apartImg {
  height: 200px;
}
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

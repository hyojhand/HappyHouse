<template>
  <b-row
    class="mb-3"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    @click="selectApart"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="6" class="text-center align-self-center">
      <b-img
        class="apartImg"
        thumbnail
        :src="require(`@/assets/apart_img/apart${num}.jpg`)"
        fluid-grow
      ></b-img>
    </b-col>
    <b-col cols="6" class="align-self-center text-left">
      <div class="AptName">
        <strong>{{ apt.apartmentName }} </strong>
      </div>
      <div>
        <strong>주소</strong> : {{ this.selectArea.sidoName }}
        {{ this.selectArea.gugunName }}
        {{ this.selectArea.dongName }}
      </div>
      <div><strong>건축년도</strong> : {{ apt.buildYear }} 년</div>
      <div><strong>거래가</strong> : {{ apt.recentPrice }} 만원</div>
      <div><strong>평수</strong> : {{ apt.area }} 평</div>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "HouseListRow",
  props: {
    apt: Object,
    num: Number,
  },
  data() {
    return {
      isColor: false,
    };
  },
  computed: {
    ...mapState("houseStore", ["selectArea"]),
  },
  methods: {
    ...mapActions("houseStore", ["selectApt", "selectApartImgNum"]),
    selectApart() {
      this.selectApt(this.apt);
      this.selectApartImgNum(this.num);
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
  background-color: rgba(255, 255, 255, 0.3);
}
</style>

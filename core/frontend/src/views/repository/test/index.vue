<template>
  <div>
    <div style="text-align: center;line-height: 48px">调试页面</div>
    <div style="display: flex;margin: 10px; ">
      <div style="width: 70%;margin: 10px;border-right: 1px solid #bbbbbb">
        <!--    自定义组件主页面内容在此div内写-->
        <div :style="style">
          我是div
        </div>
      </div>
      <div style="width: 30%;margin: 10px">
        <el-tabs v-model="activeName" >
<!--          <el-tab-pane label="数据" name="first">数据</el-tab-pane>-->
          <el-tab-pane label="样式" name="second">
            <STYLE />
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>

import STYLE from '@/views/repository/test/style.vue'
export default {
  name: 'Index',
  components: {
    STYLE
  },
  data() {
    return {
      activeName: 'second',
      value1: true,
      rightColor: null,
      myChart: null,
      showTrackBar: true,
      trackBarStyle: {
        position: 'absolute',
        left: '0px',
        top: '0px'
      },
      style: {}
    }
  },
  mounted() {

  },
  methods:{

  }
}
</script>

<style scoped>
.div0{
  width:100%;
  height:28px;
  display: flex;
}
.div1{
  width: 50%;
  height:28px;
  background: #34E3EF;
  /*-webkit-clip-path: polygon(0% 0%, 100% 0%, 99% 100%, 0% 100%);*/
  clip-path:polygon(0% 0%, 100% 0%, 99% 100%, 0% 100%);
  border-top-left-radius: 28px;
  border-bottom-left-radius: 28px;
  /*position: absolute;*/
  /*top: 0;*/
  /*left: 0;*/
}
.div2{
  width: 50%;
  height:28px;
  background: #F48518;
  /*-webkit-clip-path: polygon(3% 0, 100% 0%, 100% 100%, 0% 100%);*/
  clip-path: polygon(3% 0, 100% 0%, 100% 100%, 0% 100%);
  border-top-right-radius: 28px;
  border-bottom-right-radius: 28px;
  /*position: absolute;*/
  /*top: 0;*/
  /*right: 0;*/
}
</style>

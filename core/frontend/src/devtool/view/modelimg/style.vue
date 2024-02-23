<template>
  <div
    style="
      overflow: auto;
      border-right: 1px solid #e6e6e6;
      height: 100%;
      width: 100%;
    "
    class="attr-style theme-border-class"
  >
    <el-row>
      <span class="padding-lr">{{ $t("chart.shape_attr") }}</span>
      <el-collapse v-model="attrActiveNames" class="style-collapse">
        <el-form ref="styleForm" :model="styleForm" label-width="80px" size="mini" >
          <el-collapse-item name="font" title="字体与背景">
            <el-form-item label="字体颜色" class="form-item">
              <el-color-picker v-model="styleForm.color" show-alpha class="color-picker-style" :predefine="predefineColors" @change="changeAttributeStyle" />
            </el-form-item>
            <el-form-item label="背景颜色" class="form-item">
              <el-color-picker v-model="styleForm.backgroundColor" show-alpha class="color-picker-style" :predefine="predefineColors" @change="changeAttributeStyle" />
            </el-form-item>

          </el-collapse-item>
        </el-form>
      </el-collapse>
    </el-row>
  </div>
</template>

<script>
// 插件源码内，更换相应路径
import { COLOR_PANEL } from '../../utils/map'
// 插件源码内，屏蔽此行代码
import bus from '@/utils/bus'
// import ColorSelector from "@/components/selector/ColorSelector";
// import LabelSelector from "@/components/selector/LabelSelector";
// import LegendSelector from "@/components/selector/LegendSelector";
// import TitleSelector from "@/components/selector/TitleSelector";
// import TooltipSelector from "@/components/selector/TooltipSelector";
export default {
  components: {
  },
  data() {
    return {
      attrActiveNames: [],
      styleActiveNames: [],
      styleForm:{
      }
    };
  },
  props: {
    obj: {
      type: Object,
      default: () => {},
    },
  },

  computed: {
    param() {
      return this.obj.param;
    },
    view() {
      return this.obj.view;
    },
    chart() {
      return this.obj.chart;
    },
  },
  mounted() {
    this.init();
  },
  methods: {
    changeAttributeStyle(val){
      this.view.customStyle.styleForm = this.styleForm;
      this.calcStyle();
    },
    calcStyle() {
      bus.$emit('plugins-calc-style', this.view)
/*      this.$emit("plugin-call-back", {
        eventName: "plugins-calc-style",
        eventParam: this.view,
      });*/
    },
    init() {
      debugger
      const chart = JSON.parse(JSON.stringify(this.chart));
      if (chart.customStyle) {
        let customStyle = null;
        if (Object.prototype.toString.call(chart.customStyle) === '[object Object]') {
          customStyle = JSON.parse(JSON.stringify(chart.customStyle))
        } else {
          customStyle = JSON.parse(chart.customStyle)
        }
        if (customStyle.styleForm) {
          this.styleForm = this.view.customStyle.styleForm;
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.padding-lr {
  padding: 0 6px;
}
span {
  font-size: 12px;
}
.el-radio {
  margin: 5px;
}
.radio-span ::v-deep .el-radio__label {
  margin-left: 2px;
}
</style>

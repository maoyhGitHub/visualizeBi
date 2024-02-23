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
<!--            <el-radio-group-->
<!--              v-model="styleForm.colorTheme"-->
<!--              @change="changeAttributeStyle"-->
<!--            >-->
<!--              <el-radio label="lightTheme">浅色主题</el-radio>-->
<!--              <el-radio label="deepTheme">深色主题</el-radio>-->
<!--            </el-radio-group>-->
                <el-form-item label="标题颜色" class="form-item">
                  <el-color-picker v-model="styleForm.titleColor" class="color-picker-style" :predefine="predefineColors" show-alpha @change="changeAttributeStyle" />
                </el-form-item>
                <el-form-item label="标题背景" class="form-item">
                  <el-color-picker v-model="styleForm.titleBgColor" class="color-picker-style" :predefine="predefineColors" show-alpha @change="changeAttributeStyle" />
                </el-form-item>
                <el-form-item
                  label="标题大小"
                  class="form-item"
                >
                  <el-select
                    v-model="styleForm.titleFontSize"
                    :placeholder="$t('chart.text_fontsize')"
                    size="mini"
                    @change="changeAttributeStyle"
                  >
                    <el-option
                      v-for="option in fontSize"
                      :key="option.value"
                      :label="option.name"
                      :value="option.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item
                  label="标题位置"
                  class="form-item"
                >
                  <el-radio-group
                    v-model="styleForm.titlePosition"
                    size="mini"
                    @change="changeAttributeStyle"
                  >
                    <el-radio-button label="left">{{ $t('chart.text_pos_left') }}</el-radio-button>
                    <el-radio-button label="center">{{ $t('chart.text_pos_center') }}</el-radio-button>
                    <el-radio-button label="right">{{ $t('chart.text_pos_right') }}</el-radio-button>
                  </el-radio-group>
                </el-form-item>


              <el-form-item label="内容颜色" class="form-item">
                <el-color-picker v-model="styleForm.color" class="color-picker-style" :predefine="predefineColors" show-alpha @change="changeAttributeStyle" />
              </el-form-item>
              <el-form-item label="内容背景" class="form-item">
                <el-color-picker v-model="styleForm.backgroundColor" class="color-picker-style" :predefine="predefineColors" show-alpha @change="changeAttributeStyle" />
              </el-form-item>
              <el-form-item
                label="内容大小"
                class="form-item"
              >
                <el-select
                  v-model="styleForm.fontSize"
                  :placeholder="$t('chart.text_fontsize')"
                  size="mini"
                  @change="changeAttributeStyle"
                >
                  <el-option
                    v-for="option in fontSize"
                    :key="option.value"
                    :label="option.name"
                    :value="option.value"
                  />
                </el-select>
              </el-form-item>

            <el-form-item
              label-width="100px"
              label="左边距(px)"
              class="form-item form-item-slider"
            >
              <div style="display: flex">
                <el-slider
                  style="margin-right: 30px"
                  v-model="styleForm.paddingLeft"
                  :min="0"
                  :max="500"
                  show-input
                  :show-input-controls="false"
                  input-size="mini"
                  @change="changeAttributeStyle"
                />
              </div>

            </el-form-item>

          </el-collapse-item>
        </el-form>
      </el-collapse>
    </el-row>
  </div>
</template>

<script>
import bus from '@/utils/bus'
export default {
  components: {
  },
  data() {
    return {
      attrActiveNames: [],
      styleActiveNames: [],
      styleForm:{
        titleFontSize:14,
        fontSize:14,
        titlePosition:'left',
        paddingLeft:2
      },
      fontSize: [],
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
    this.initFontSize()
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
    initFontSize() {
      const arr = []
      for (let i = 10; i <= 60; i = i + 2) {
        arr.push({
          name: i + '',
          value: i + ''
        })
      }
      this.fontSize = arr
    }
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

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
      <el-collapse
        v-model="attrActiveNames"
        class="style-collapse"
      >
        <el-form
          ref="styleForm"
          :model="styleForm"
          label-width="80px"
          size="mini"
        >
          <el-collapse-item
            name="leftColor"
            title="颜色"
          >
            <el-form-item
              label="左侧颜色"
              class="form-item"
            >
              <el-color-picker
                v-model="styleForm.leftColor"
                show-alpha
                :predefine="predefineColors"
                @change="onColorChange"
              />
            </el-form-item>
            <el-form-item
              label="右侧颜色"
              class="form-item"
            >
              <el-color-picker
                v-model="styleForm.rightColor"
                show-alpha
                :predefine="predefineColors"
                @change="onColorChange"
              />
            </el-form-item>
          </el-collapse-item>
          <el-collapse-item
            name="tooltip"
            title="字体属性"
          >

            <el-form-item
              label="名称显示"
              class="form-item"
            >
              <el-checkbox
                v-model="styleForm.show"
                @change="changeAttributeStyle"
              >显示</el-checkbox>
            </el-form-item>

            <el-form-item
              :label="$t('chart.text_fontsize')"
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
              :label="$t('chart.text_color')"
              class="form-item"
            >
              <el-color-picker
                v-model="styleForm.color"
                class="color-picker-style"
                :predefine="predefineColors"
                @change="changeAttributeStyle"
              />
            </el-form-item>
            <el-form-item
              label="文本宽度"
              class="form-item"
            >
              <el-slider
                v-model="styleForm.width"
                show-input
                :show-input-controls="false"
                value="50"
                input-size="mini"
                @change="changeAttributeStyle"
              />
            </el-form-item>
          </el-collapse-item>
        </el-form>
      </el-collapse>
    </el-row>

  </div>
</template>

<script>
import { COLOR_PANEL } from './map'
export default {
  components: {
  },
  props: {
    obj: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      styleForm: {
        width: 50,
        fontSize: 14
      }, // 样式统一form
      attrActiveNames: [],
      styleActiveNames: [],
      fontSize: [],
      predefineColors: COLOR_PANEL
    }
  },
  computed: {
    param() {
      return this.obj.param
    },
    view() {
      return this.obj.view
    },
    chart() {
      return this.obj.chart
    }
  },
  mounted() {
    this.init()
    this.initFontSize()
  },
  methods: {
    changeAttributeStyle(val) {
      this.view.customStyle.styleForm = this.styleForm
      this.calcStyle()
    },
    onColorChange() {
      this.view.customStyle.styleForm = this.styleForm
      this.calcStyle()
    },
    calcStyle() {
      this.$emit('plugin-call-back', {
        eventName: 'plugins-calc-style',
        eventParam: this.view
      })
    },
    init() {
     /*  const chart = JSON.parse(JSON.stringify(this.chart))
      if (chart.customStyle) {
        let customStyle = null
        if (Object.prototype.toString.call(chart.customStyle) === '[object Object]') {
          customStyle = JSON.parse(JSON.stringify(chart.customStyle))
        } else {
          customStyle = JSON.parse(chart.customAttr)
        }
        if (customStyle.styleForm) {
          this.styleForm = this.view.customStyle.styleForm
        }
      } */
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
  }
}
</script>

<style lang="scss" scoped>
.padding-lr {
  padding: 0 6px;
}
span {
  font-size: 12px;
}

</style>

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
          label-width="100px"
          size="mini"
        >
          <el-collapse-item
            name="leftColor"
            title="地图基础属性"
          >
            <el-form-item
              label="图层背景色"
              class="form-item"
            >
              <el-radio-group
                v-model="styleForm.radioColor"
                @change="onColorChange"
              >
                <el-radio label="allZone">全局配色</el-radio>
                <el-radio label="warZone">战区配色</el-radio>
              </el-radio-group>
            </el-form-item>
            <template v-if="styleForm.radioColor ==='allZone'">
              <el-form-item
                label="图层背景色"
                class="form-item"
              >
                <el-color-picker
                  v-model="styleForm.mapBackgroundColor"
                  show-alpha
                  :predefine="predefineColors"
                  @change="onColorChange"
                />
              </el-form-item>
            </template>
            <template v-else>
              <el-form-item
                label="东部战区"
                class="form-item"
              >
                <el-color-picker
                  v-model="styleForm.eastZoneBackgroundColor"
                  show-alpha
                  :predefine="predefineColors"
                  @change="onColorChange"
                />
              </el-form-item>
              <el-form-item
                label="南部战区"
                class="form-item"
              >
                <el-color-picker
                  v-model="styleForm.southZoneBackgroundColor"
                  show-alpha
                  :predefine="predefineColors"
                  @change="onColorChange"
                />
              </el-form-item>
              <el-form-item
                label="西部战区"
                class="form-item"
              >
                <el-color-picker
                  v-model="styleForm.westZoneBackgroundColor"
                  show-alpha
                  :predefine="predefineColors"
                  @change="onColorChange"
                />
              </el-form-item>
              <el-form-item
                label="北部战区"
                class="form-item"
              >
                <el-color-picker
                  v-model="styleForm.northZoneBackgroundColor"
                  show-alpha
                  :predefine="predefineColors"
                  @change="onColorChange"
                />
              </el-form-item>
              <el-form-item
                label="中部战区"
                class="form-item"
              >
                <el-color-picker
                  v-model="styleForm.middleZoneBackgroundColor"
                  show-alpha
                  :predefine="predefineColors"
                  @change="onColorChange"
                />
              </el-form-item>
            </template>
            <el-form-item
              label="地图大小"
              class="form-item"
            >
              <el-slider
                v-model="styleForm.mapScaleZoom"
                show-input
                :show-input-controls="false"
                input-size="mini"
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
              label="省界线颜色"
              class="form-item"
            >
              <el-color-picker
                v-model="styleForm.provinceBorderColor"
                show-alpha
                :predefine="predefineColors"
                @change="changeAttributeStyle"
              />
            </el-form-item>
            <el-form-item
              label="省界线宽度"
              class="form-item"
            >
              <el-slider
                v-model="styleForm.provinceBorderWidth"
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
// 插件源码内，更换相应路径
import { COLOR_PANEL } from '../../utils/map'
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
        mapBackgroundColor: 'rgb(22,185,241)',
        mapScaleZoom: 50,
        provinceBorderColor: '#ffffff',
        provinceBorderWidth: 50,
        fontSize: 14,
        radioColor: 'allZone',
        eastZoneBackgroundColor: 'rgb(22,185,241)',
        southZoneBackgroundColor: 'rgb(22,185,241)',
        westZoneBackgroundColor: 'rgb(22,185,241)',
        northZoneBackgroundColor: 'rgb(22,185,241)',
        middleZoneBackgroundColor: 'rgb(22,185,241)'
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
      const chart = JSON.parse(JSON.stringify(this.chart))
      if (chart.customStyle) {
        let customStyle = null
        if (Object.prototype.toString.call(chart.customStyle) === '[object Object]') {
          customStyle = JSON.parse(JSON.stringify(chart.customStyle))
        } else {
          customStyle = JSON.parse(chart.customStyle)
        }
        if (customStyle.styleForm) {
          Object.assign(this.styleForm, this.view.customStyle.styleForm)
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

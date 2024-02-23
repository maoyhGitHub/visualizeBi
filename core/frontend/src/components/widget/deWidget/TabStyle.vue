<template>

  <el-popover
    placement="right"
    trigger="click"
  >
    <div style="height: 700px;overflow-y: auto">
      <el-form
        ref="tabsStyleForm"
        :model="styleInfo"
        size="small"
        class="de-form-item"
      >
        <el-form-item
          label="头部面板高度"
          prop="titlePanelHeight"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('titlePanelHeight')"
          >
            <el-input
              v-model="styleInfo.titlePanelHeight"
              type="number"
              size="mini"
              @change="styleChange"
            >
              <template slot="append">px</template>
            </el-input>
          </div>
        </el-form-item>
        <el-form-item
          label="头部面板右移"
          prop="titlePanelRight"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('titlePanelRight')"
          >
            <el-input
              v-model="styleInfo.titlePanelRight"
              type="number"
              size="mini"
              @change="styleChange"
            >
              <template slot="append">px</template>
            </el-input>
          </div>
        </el-form-item>
        <el-form-item
          label="开启头部面板背景"
          prop="activeBackgroundImage"
        >
          <el-checkbox
            v-model="styleInfo.activeBackgroundImage"
            @change="styleChange">
            显示背景
          </el-checkbox>
        </el-form-item>
        <el-form-item
          label="头部字体颜色"
          prop="headFrontColor"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('headFontColor')"
          >
            <el-input
              v-model="styleInfo.headFontColor"
              readonly
              class="theme-input"
            >
              <el-color-picker
                ref="headFontColorPicker"
                slot="prefix"
                v-model="styleInfo.headFontColor"
                class="theme-picker"
                @change="styleChange"
              />
            </el-input>
          </div>
          <div
            class="picker-color-div"
            @click="triggerTheme('headFontColor1')"
          >
            <el-input
              v-model="styleInfo.headFontColor1"
              readonly
              class="theme-input"
            >
              <el-color-picker
                ref="headFontColorPicker1"
                slot="prefix"
                v-model="styleInfo.headFontColor1"
                class="theme-picker"
                @change="styleChange"
              />
            </el-input>
          </div>

        </el-form-item>
        <el-form-item
          label="头部字体选中颜色"
          prop="headFontActiveColor"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('headFontActiveColor')"
          >
            <el-input
              v-model="styleInfo.headFontActiveColor"
              readonly
              class="theme-input"
            >
              <el-color-picker
                ref="headFontActiveColorPicker"
                slot="prefix"
                v-model="styleInfo.headFontActiveColor"
                class="theme-picker"
                @change="styleChange"
              />
            </el-input>
          </div>
        </el-form-item>
        <el-form-item
          label="头部选中背景色"
          prop="activeBackgroundColor"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('activeBackgroundColor')"
          >
            <el-input
              v-model="styleInfo.activeBackgroundColor"
              readonly
              class="theme-input"
            >
              <el-color-picker
                ref="activeBackgroundColorPicker"
                slot="prefix"
                v-model="styleInfo.activeBackgroundColor"
                class="theme-picker"
                @change="styleChange"
              />
            </el-input>
          </div>
        </el-form-item>
        <el-form-item
          label="头部边框颜色"
          prop="headBorderColor"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('headBorderColor')"
          >
            <el-input
              v-model="styleInfo.headBorderColor"
              readonly
              class="theme-input"
            >
              <el-color-picker
                ref="headBorderColorPicker"
                slot="prefix"
                v-model="styleInfo.headBorderColor"
                class="theme-picker"
                @change="styleChange"
              />
            </el-input>
          </div>
        </el-form-item>
        <el-form-item
          label="头部边框选中颜色"
          prop="headBorderActiveColor"
        >
          <div
            class="picker-color-div"
            @click="triggerTheme('headBorderActiveColor')"
          >
            <el-input
              v-model="styleInfo.headBorderActiveColor"
              readonly
              class="theme-input"
            >
              <el-color-picker
                ref="headBorderActiveColorPicker"
                slot="prefix"
                v-model="styleInfo.headBorderActiveColor"
                class="theme-picker"
                @change="styleChange"
              />
            </el-input>
          </div>
        </el-form-item>
        <el-form-item :label="$t('detabs.head_position')">
          <el-radio-group
            v-model="styleInfo.headPosition"
            size="mini"
          >
            <el-radio-button label="left">{{ $t('chart.text_pos_left') }}</el-radio-button>
            <el-radio-button label="center">{{ $t('chart.text_pos_center') }}</el-radio-button>
            <el-radio-button label="right">{{ $t('chart.text_pos_right') }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('panel.carousel')">
          <el-row>
            <el-col :span="6">
              <el-checkbox
                v-model="styleInfo.carouselEnable"
                size="mini"
                @change="styleChange"
              >{{ $t('commons.enable') }}
              </el-checkbox>
            </el-col>
            <el-col
              :span="8"
              style="text-align: right;padding-right: 10px"
            >
              {{ $t('panel.switch_time') }}
            </el-col>
            <el-col :span="10">
              <el-input
                v-model="styleInfo.switchTime"
                :disabled="!styleInfo.carouselEnable"
                type="number"
                size="mini"
                :min="2"
                :max="3600"
                class="hide-icon-number number-padding"
                @change="switchTimeChange"
              >
                <template slot="append">S</template>
              </el-input>
            </el-col>

          </el-row>
        </el-form-item>
      </el-form>
    </div>
    <i
      slot="reference"
      class="iconfont icon-tabs"
    />
  </el-popover>

</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'TabStyle',
  props: {
    styleInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {}
  },
  computed: {

    ...mapState([
      'componentData',
      'curComponent'
    ])
  },
  methods: {
    triggerTheme(key) {
      const pickKey = key + 'Picker'
      const current = this.$refs[pickKey]
      current && (current.showPicker = true)
    },
    switchTimeChange() {
      if (!this.styleInfo.switchTime || this.styleInfo.switchTime < 2) {
        this.styleInfo.switchTime = 2
      } else if (this.styleInfo.switchTime && this.styleInfo.switchTime > 3600) {
        this.styleInfo.switchTime = 3600
      }
      this.styleChange()
    },
    styleChange() {
      this.$store.commit('canvasChange')
    }
  }

}
</script>

<style lang="scss" scoped>
::v-deep.number-padding {
  .el-input__inner {
    padding-right: 0;
  }
  .el-input-group__append {
    padding: 0 10px;
  }
}
</style>

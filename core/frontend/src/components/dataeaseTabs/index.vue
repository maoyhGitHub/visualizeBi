<template>
  <el-tabs
    :class="['de-tabs',...tabClassName]"
    :style="tabStyle"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <slot />
  </el-tabs>
</template>

<script>
export default {
  name: 'DataeaseTabs',
  props: {

    fontColor: String,
    activeColor: String,
    borderColor: String,
    borderActiveColor: String,
    bindCustomStyle: Object,
    styleType: {
      type: String,
      default: '',
      validator: (val) => ['', 'radioGroup'].includes(val)
    }
  },
  data() {
    return {}
  },
  computed: {
    tabStyle() {
      return {
        '--font-color': this.fontColor,
        '--active-color': this.activeColor,
        '--border-color': this.borderColor,
        '--border-active-color': this.borderActiveColor,
        '--active-background-color': this.bindCustomStyle.activeBackgroundColor,
        '--title-panel-height': this.bindCustomStyle.titlePanelHeight,
        '--title-panel-right': this.bindCustomStyle.titlePanelRight
      }
    },
    tabClassName() {
      const classes = [
        this.styleType,
        this.fontColor && 'fontColor',
        this.activeColor && 'activeColor',
        this.noBorder ? 'noBorder' : this.borderColor && 'borderColor',
        this.borderActiveColor && 'borderActiveColor',
        this.bindCustomStyle.activeBackgroundColor && 'activeBackgroundColor',
        this.bindCustomStyle.titlePanelHeight && 'titlePanelHeight',
        this.bindCustomStyle.titlePanelRight && 'titlePanelRight',
        this.bindCustomStyle.activeBackgroundImage ? 'activeBackgroundImage' : ''
      ]
      return classes
    },
    noBorder() {
      return this.borderColor === 'none'
    },
    noBorderActive() {
      return this.borderActiveColor === 'none'
    }
  },
  created() {},
  methods: {}
}
</script>
<style lang="scss">
@import "../../styles/de-tabs";

</style>


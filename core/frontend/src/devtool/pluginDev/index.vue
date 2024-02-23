<template>
  <component
    :is="mainIndex"
    :ref="mainTool"
    :obj="obj"
    :chart="chart"
    :track-menu="trackMenu"
    @onChartClick="chartClick"
    @onJumpClick="jumpClick"
    @trigger-edit-click="pluginEditHandler"
    @execute-axios="executeAxios"
  />
</template>

<script>
import { uuid } from 'vue-uuid'
import { mainIndex } from '@/devtool/view/mainTool'
import { execute } from '@/api/system/dynamic'
export default {
  name: 'MainDev',
  components: {
    mainIndex
  },
  props: {
    chart: {
      type: Object,
      required: true
    },
    trackMenu: {
      type: Array,
      required: false
    },
    obj: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      mainTool: null,
      mainIndex: 'mainIndex'
    }
  },
  created() {
    this.mainTool = uuid.v1
  },
  beforeDestroy() {
    this.mainIndex = null
  },
  methods: {
    jumpClick(jumpParam) {
      this.$emit('onJumpClick', jumpParam)
    },
    chartClick(param) {
      this.$emit('onChartClick', param)
    },
    pluginEditHandler(param) {
      this.$emit('trigger-edit-click', param)
    },
    chartResize() {
      if (this.$refs[this.mainTool]) {
        this.$refs[this.mainTool].chartResize()
      }
    },
    executeAxios(options) {
      execute(options).then(res => {
        if (options.callBack) {
          options.callBack(res)
        }
      }).catch(e => {
        if (options.callBack) {
          options.callBack(e)
        }
      })
    }
  }
}

</script>

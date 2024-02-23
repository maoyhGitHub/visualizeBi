<template>
  <div
    style="display: flex;position:relative"
    class="chart-class"
  >
    <view-track-bar
      ref="viewTrack"
      :track-menu="trackMenu"
      class="track-bar"
      :style="trackBarStyleTime"
      @trackClick="trackClick"
    />
    <div
      class="de-select-grid-class"
    >
      <div :style="{'--fontColor':fontColor,'--fontSize':fontSize}">
        <span style="color: rgba(54,250,125,0.99)">组件示例模板 </span>
      </div>
      <div class="list">
        <span style="color: rgba(54,250,125,0.99)">拖动数据集字段，展现单选示例 </span>
        <!--单选-->
        <div
          v-if="true"
          class="radio-group-container"
          :style="{'--fontColor':fontColor,'--fontSize':fontSize}"
        >
          <el-radio-group
            v-model="radioValue"
          >
            <el-radio
              v-for="(item, index) in data.filter(node => !keyWord || (node.value && node.value.toLocaleUpperCase().includes(keyWord.toLocaleUpperCase())))"
              :key="index"
              :label="item.value"
              @change="changeRadioBox(item)"
            >
              <span>{{ item.value }}</span>
            </el-radio>
          </el-radio-group>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
// 辅助工具内使用以下组件引用
import ViewTrackBar from '@/components/canvas/components/editor/ViewTrackBar.vue'
import { uuid } from '@/devtool/utils/map'
// 插件源码内放开此代码，屏蔽上面引入组件代码
/* import ViewTrackBar from '@/components/views/ViewTrackBar'
import { uuid } from '@/utils/map' */

export default {
  name: 'ChartComponent',
  components: {
    ViewTrackBar
  },
  props: {
    chart: {
      type: Object,
      required: true
    },
    trackMenu: {
      type: Array,
      required: false,
      default: function() {
        return ['drill']
      }
    },
    obj: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chartId: uuid(), // 如果是图表echarts或antv,请使用此id获取dom
      pointParam: null, // 跳转联动时配置
      // 以下属性按自己所需定义，不需要的全删
      radioValue: '3',
      keyWord: '',
      isIndeterminate: false,
      checkAll: false,
      data: [],
      fontColor: '',
      fontSize: ''
    }
  },
  // 计算属性禁止改动，必须存在
  computed: {
    trackBarStyleTime() {
      return this.trackBarStyle
    },
    filter() {
      return this.obj.filter || {}
    },
    searchCount() {
      return this.obj.searchCount || 0
    },
    terminalType() {
      return this.obj.terminalType || 'pc'
    }
  },
  // 监听数据处禁止改动，必须存在
  watch: {
    chart: {
      handler(newVal, oldVla) {
        console.log('chart is change', this.obj)
        this.preDraw()
      },
      deep: true
    },

    resize() {
      this.preDraw()
    }
  },
  created() {
  },
  mounted() {
    this.preDraw()
  },
  destroyed() {
  },
  methods: {
    preDraw() {
      const xaxisData = JSON.parse(this.chart.xaxis)
      let model = ''
      let fileId = ''
      if (xaxisData.length > 0) {
        model = xaxisData[0].dataeaseName
        fileId = xaxisData[0].id
        let allData = []
        if (this.chart.data) {
          allData = this.chart.data.tableRow
        }
        const optionsData = []
        allData.forEach(ietm => {
          optionsData.push({ id: fileId, value: ietm[model] })
        })
        this.data = optionsData
      }
      // 设置样式
      this.setStyleInfo()
    },
    // 样式统一写此方法
    setStyleInfo() {
      if (this.chart.customStyle) {
        const customStyle = JSON.parse(this.chart.customStyle)
        if (customStyle.styleForm) {
          this.fontSize = customStyle.styleForm.fontSize + 'px'
          this.fontColor = customStyle.styleForm.color
        }
      }
    },
    // 统一接口调用方法 调用示例 this.executeAxios(url, 'post', param, res => {})
    executeAxios(url, type, data, callBack) {
      const param = {
        url: url,
        type: type,
        data: data,
        callBack: callBack
      }
      this.$emit('execute-axios', param)
    },
    changeRadioBox(item) {
      const linkageParam = {
        option: 'linkage',
        name: item.value,
        viewId: this.chart.id,
        dimensionList: [item],
        quotaList: []
      }
      this.$store.commit('addViewTrackFilter', linkageParam)

      // this.changeValue(value)
    },
    testChange(item) {
      this.changeRadioBox(item)
    },
    // 此方法禁止删除，每个index都要有，边框变化时处理，不需要处时为空方法
    chartResize() {

    },
    trackClick(trackAction) {
      const quotaList = this.pointParam.data.quotaList
      // quotaList[0]['value'] = this.pointParam.data.value
      const linkageParam = {
        option: 'linkage',
        name: this.pointParam.data.name,
        viewId: this.chart.id,
        dimensionList: this.pointParam.data.dimensionList,
        quotaList: quotaList
      }
      const jumpParam = {
        option: 'jump',
        name: this.pointParam.data.name,
        viewId: this.chart.id,
        dimensionList: this.pointParam.data.dimensionList,
        quotaList: quotaList
      }
      switch (trackAction) {
        case 'drill':
          this.$emit('onChartClick', this.pointParam)
          break
        case 'linkage':
          this.linkageActivePre()
          this.$store.commit('addViewTrackFilter', linkageParam)
          break
        case 'jump':
          this.$emit('onJumpClick', jumpParam)
          break
        default:
          break
      }
    }
  }
}

</script>

<style lang="scss" scoped>

.chart-class {
  height: 100%;
  padding: 10px;
}
.track-bar >>> ul {
  width: 80px !important;
}
.de-select-grid-search {
  width: 100%;
  ::v-deep input {
    border-radius: 0px;
  }
  .el-input {
    display: block !important;
  }
}
.de-select-grid-class {
  width: 100%;
  height: 100%;

  .list {
    overflow-y: auto;
    width: 100%;
    position: relative;
    bottom: 0;
    height: calc(100% - 40px);
    text-align: left;
  }
}
.radio-group-container > .el-radio-group > label {
  display: block !important;
  margin: 10px !important;
}

::v-deep .el-radio__label{
  color: var(--fontColor,rgb(142,143,146));
  font-size: var(--fontSize);
}


</style>

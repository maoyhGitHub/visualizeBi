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
      <div
        class="de-select-grid-search"
        @click="setEdit"
      >
        <el-input
          ref="nameInput"
          v-model="keyWord"
          :placeholder="$t('deinputsearch.placeholder')"
          prefix-icon="el-icon-search"
          clearable
        />
      </div>
      <div class="list">
        <!--多选-->
        <div
          v-if="false"
          class="checkbox-group-container"
        >
          <el-checkbox
            v-model="checkAll"
            :indeterminate="isIndeterminate"
            @change="handleCheckAllChange"
          />
          {{ $t('commons.all') }}

          <el-checkbox-group
            v-model="value"
            @change="handleCheckedChange"
          >
            <template v-for="item in data.filter(node => !keyWord || (node.id && node.id.toLocaleUpperCase().includes(keyWord.toLocaleUpperCase())))">
              <el-checkbox
                :key="item.id"
                :label="item.id"
              >{{ item.id }}
              </el-checkbox>
              <br :key="item.id">
            </template>
          </el-checkbox-group>
        </div>
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
import ViewTrackBar from '@/components/canvas/components/editor/ViewTrackBar.vue'
import { uuid } from '../../utils/map'
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
      radioValue: '3',
      keyWord: '',
      isIndeterminate: false,
      checkAll: false,
      data: [],
      fontColor: '',
      fontSize: ''
    }
  },
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
    setEdit() {
      this.$refs['nameInput'].focus()
    },
    chartResize() {
      console.log('变化水水水水')
    },
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
    setStyleInfo() {
      if (this.chart.customStyle) {
        const customStyle = JSON.parse(this.chart.customStyle)
        if (customStyle.styleForm) {
          this.fontSize = customStyle.styleForm.fontSize + 'px'
          this.fontColor = customStyle.styleForm.color
        }
      }
    },
    changeRadioBox(item) {
      console.log(item)
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

::v-deep  .el-input__inner {
  background-color: yellow !important;
  color: red !important;
}

</style>

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
      :id="chartId"
      ref="chinaMap"
      style="width: 100%; height: 70vh"
    />
  </div>
</template>

<script>
import ViewTrackBar from '@/components/canvas/components/editor/ViewTrackBar.vue'
import * as echarts from 'echarts'
import geoJson from '../../utils/china.json'
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
    obj: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chartId: uuid(),
      myChart: '',
      southZone: {
        data: ['湖南省', '广东省', '广西壮族自治区', '海南省', '云南省', '贵州省', '香港特别行政区', '澳门特别行政区', '台湾省'],
        defaultColor: '#54a2c9'
      },
      northZone: {
        data: ['黑龙江省', '吉林省', '辽宁省', '内蒙古自治区', '山东省'],
        defaultColor: '#29b0ef'
      },
      eastZone: {
        data: ['江苏省', '浙江省', '安徽省', '福建省', '江西省', '上海市'],
        defaultColor: '#1e79b4'
      },
      westZone: {
        data: ['四川省', '西藏自治区', '甘肃省', '宁夏回族自治区', '青海省', '新疆维吾尔自治区', '重庆市'],
        defaultColor: 'rgb(11,153,241)'
      },
      middleZone: {
        data: ['河北省', '山西省', '河南省', '北京市', '天津市', '湖北省', '陕西省'],
        defaultColor: '#0787cc'
      },
      setRegionsAreaCole: [],
      option: {
        tooltip: {
          backgroundColor: 'rgba(255,255,255,0.5)',
          show: true,
          trigger: 'item',
          textStyle: {
            color: '#FFF'
          },
          formatter: function(params) {
            if (params.data && params.data.value) {
              return '服务点名称：' + params.data.name + '<br>' + '型号：' + params.data.value[1]
            } else {
              return '没有可用的标记点数据'
            }
          }
        },
        geo: [
          {
            map: 'china',
            zlevel: 6,
            aspectScale: 0.75, // 长宽比
            zoom: 0.8,
            roam: false,
            itemStyle: {
              color: 'rgb(22,185,241)', // 背景
              borderWidth: '0.5', // 边框宽度
              borderColor: '#ffffff', // 边框颜色
              emphasis: {
                areaColor: 'rgba(54,250,125,0.99)',
                borderWidth: 0,
                color: 'green',
                label: {
                  show: false
                }
              }
            },
            regions: []
          },
          {
            map: 'china',
            top: '12%',
            zlevel: 4,
            aspectScale: 0.75, // 长宽比
            zoom: 0.8,
            roam: false,
          }
        ],
        series: [
          {
            name: '中国地图',
            type: 'map',
            geoIndex: 0,
            data: []
          },
          {
            type: 'effectScatter',
            coordinateSystem: 'geo',
            showEffectOn: 'render',
            zlevel: 6,
            rippleEffect: {
              period: 8,
              scale: 8,
              brushType: 'fill'
            },
            hoverAnimation: true,
            label: {
              normal: {
                formatter: '{b}',
                position: 'right',
                offset: [15, 0],
                color: 'rgba(54,250,125,0.99)',
                show: true
              }
            },
            itemStyle: {
              normal: {
                color: 'rgba(36,206,140,0.99)',
                shadowBlur: 10,
                shadowColor: '#333'
              }
            },
            symbolSize: 3,
            data: [
            /*   { value: [118.8062, 31.9208], itemStyle: { color: '#4ab2e5' }, name1: '888' },
              { value: [127.9688, 45.368], itemStyle: { color: '#4fb6d2' }, name1: '888' },
              { value: [110.3467, 41.4899], itemStyle: { color: '#52b9c7' }, name1: '888' },
              { value: [125.8154, 44.2584], itemStyle: { color: '#5abead' }, name1: '888' },
              { value: [116.4551, 40.2539], itemStyle: { color: '#f34e2b' }, name1: '888' },
              { value: [123.1238, 42.1216], itemStyle: { color: '#f56321' }, name1: '888' },
              { value: [114.4995, 38.1006], itemStyle: { color: '#f56f1c' }, name1: '888' },
              { value: [117.4219, 39.4189], itemStyle: { color: '#f58414' }, name1: '888' },
              { value: [112.3352, 37.9413], itemStyle: { color: '#f58f0e' }, name1: '888' },
              { value: [109.1162, 34.2004], itemStyle: { color: '#f5a305' }, name1: '888' },
              { value: [103.5901, 36.3043], itemStyle: { color: '#e7ab0b' }, name1: '888' },
              { value: [106.3586, 38.1775], itemStyle: { color: '#dfae10' }, name1: '888' },
              { value: [101.4038, 36.8207], itemStyle: { color: '#d5b314' }, name1: '888' },
              { value: [103.9526, 30.7617], itemStyle: { color: '#c1bb1f' }, name1: '888' },
              { value: [108.384366, 30.439702], itemStyle: { color: '#b9be23' }, name1: '888' },
              { value: [113.0823, 28.2568], itemStyle: { color: '#a6c62c' }, name1: '888' },
              { value: [102.9199, 25.46639], itemStyle: { color: '#96cc34' }, name1: '888' },
              { value: [119.4543, 25.9222] } */
            ]
          }
        ]

      },
      img: ''
    }
  },
  computed: {
    trackBarStyleTime() {
      return this.trackBarStyle
    },
    filter() {
      return this.obj.filter || {}
    },
    trackMenu() {
      return this.obj.trackMenu || ['drill']
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
    echarts.registerMap('china', geoJson)
  },
  mounted() {
    /*  const geoJson = require('./china.json')
    echarts.registerMap('china', geoJson) */
    // console.log(this.$store.getters.geoMap['156100000'])
    this.preDraw()
  },
  destroyed() {
    if (!this.myChart) {
      return
    }
    this.myChart.dispose()
    this.myChart = null
  },
  methods: {
    chartResize() {
      const chart = this.myChart
      chart.resize()
      this.preDraw()
    },
    preDraw() {
      // 设置经纬度数据
      const xaxisData = JSON.parse(this.chart.xaxis)
      const yaxisData = JSON.parse(this.chart.yaxis)
      let locationXaxis = ''
      let locationYaxis = ''
      let provinceXaxis = ''
      let toolTip = [];
      xaxisData.forEach(ietm => {
        if (ietm.flag === 'locationXaxis') {
          locationXaxis = ietm.dataeaseName
        }
        if (ietm.flag === 'locationYaxis') {
          locationYaxis = ietm.dataeaseName
        }
        if (ietm.flag === 'provinceXaxis') {
          provinceXaxis = ietm.dataeaseName
        }
        if(ietm.flag ==  'toolTip'){
          toolTip.push({dataeaseName:ietm.dataeaseName,filedName:ietm.name})
        }
      })
      const count = yaxisData[0]?.dataeaseName
      const locationData = []
      const proviceData = []
      const allData = this.chart.data?.tableRow || []
      for (let i = 0; i < allData.length; i++) {
        proviceData.push(
          {
            name: allData[i][provinceXaxis] || '',
            value: allData[i][count] || 0
          }
        )
        //提示框信息
        let toopTipData ={};
        for(var j =0;j<toolTip.length;j++){
          let key ="toolTip"+j
          let vulue =  allData[i][toolTip[j].dataeaseName]
          toopTipData[key] = vulue;
        }
        Object.assign(proviceData,toopTipData)

        locationData.push({
          value: [allData[i][locationXaxis] || '', allData[i][locationYaxis] || '']
        })
      }
      this.option.series[0].data = proviceData
      this.option.series[1].data = locationData
      // 基于准备好的dom，初始化echarts实例
      // 渲染echart等待dom加载完毕,渲染之前先尝试销毁具有相同id的echart 放置多次切换仪表板有重复id情况
      const that = this
      new Promise((resolve) => {
        resolve()
      }).then(() => {
        //	此dom为echarts图标展示dom
        this.myChart = echarts.getInstanceByDom(document.getElementById(this.chartId))
        if (!this.myChart) {
          this.myChart = echarts.init(document.getElementById(this.chartId))
        }
        this.drawEcharts()
      })
    },
    drawEcharts() {
      this.setStyleInfo()
      this.myEcharts(this.option)
    },
    setStyleInfo() {
      if (this.chart.customStyle) {
        const customStyle = JSON.parse(this.chart.customStyle)
        if (customStyle.styleForm) {
          const mapScaleZoom = Math.round(customStyle.styleForm.mapScaleZoom) / 50
          this.option.geo[0].zoom = mapScaleZoom
          this.option.geo[1].zoom = mapScaleZoom
          this.option.geo[0].itemStyle.borderColor = customStyle.styleForm.provinceBorderColor
          if (customStyle.styleForm.radioColor === 'allZone') {
            // 全局颜色
            this.option.geo[0].itemStyle.color = customStyle.styleForm.mapBackgroundColor
            this.option.geo[0].regions = []
          } else {
            // 战区颜色
            this.eastZone.defaultColor = customStyle.styleForm.eastZoneBackgroundColor
            this.southZone.defaultColor = customStyle.styleForm.southZoneBackgroundColor
            this.westZone.defaultColor = customStyle.styleForm.westZoneBackgroundColor
            this.northZone.defaultColor = customStyle.styleForm.northZoneBackgroundColor
            this.middleZone.defaultColor = customStyle.styleForm.middleZoneBackgroundColor
            this.warMapAreaColor()
          }
        }
      }
    },
    warMapAreaColor() {
      this.setRegionsAreaCole = []
      this.eastZone.data.forEach(item => {
        this.setRegionsAreaCole.push({
          name: item,
          itemStyle: {
            normal: {
              areaColor: this.eastZone.defaultColor // 整个省份的颜色
            }
          }
        })
      })

      this.southZone.data.forEach(item => {
        this.setRegionsAreaCole.push({
          name: item,
          itemStyle: {
            normal: {
              areaColor: this.southZone.defaultColor // 整个省份的颜色
            }
          }
        })
      })
      this.westZone.data.forEach(item => {
        this.setRegionsAreaCole.push({
          name: item,
          itemStyle: {
            normal: {
              areaColor: this.westZone.defaultColor // 整个省份的颜色
            }
          }
        })
      })
      this.northZone.data.forEach(item => {
        this.setRegionsAreaCole.push({
          name: item,
          itemStyle: {
            normal: {
              areaColor: this.northZone.defaultColor // 整个省份的颜色
            }
          }
        })
      })
      this.middleZone.data.forEach(item => {
        this.setRegionsAreaCole.push({
          name: item,
          itemStyle: {
            normal: {
              areaColor: this.middleZone.defaultColor // 整个省份的颜色
            }
          }
        })
      })
      this.option.geo[0].regions = this.setRegionsAreaCole
    },
    myEcharts(option) {
      const chart = this.myChart
      setTimeout(chart.setOption(option, true), 500)
      window.onresize = function() {
        chart.resize()
      }
    },
    trackClick(trackAction) {
      const param = this.pointParam
      if (!param || !param.data || !param.data.dimensionList) {
        // 地图提示没有关联字段 其他没有维度信息的 直接返回
        if (this.chart.type === 'map') {
          this.$warning(this.$t('panel.no_drill_field'))
        }
        return
      }
      const quotaList = this.pointParam.data.quotaList
      quotaList[0]['value'] = this.pointParam.data.value
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

</style>

<template>
  <div style="display: flex;position:relative"
       class="chart-class">
    <view-track-bar
      ref="viewTrack"
      :track-menu="trackMenu"
      class="track-bar"
      :style="trackBarStyleTime"
    />
    <div style="width: 100%;height: 100%;overflow: auto" :style="{'--titleColor':titleColor, '--titleBgColor':titleBgColor,'--titleFontSize':titleFontSize}">
      <el-collapse v-model="activeNames">
        <div v-for="(value, key) in dataMap" :key="key" >
          <el-collapse-item
          >
            <template slot="title" >
              <div :style="titleStyle">{{value[0]}}</div>
            </template>
            <div
              v-for="data in value[1]"
              :style="contentStyle"
            >{{data}}</div>
          </el-collapse-item>
        </div>
      </el-collapse>
    </div>
  </div>
</template>

<script>
// import ViewTrackBar from '@/components/views/ViewTrackBar'
import ViewTrackBar from '@/components/canvas/components/editor/ViewTrackBar.vue'
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
  computed: {
    trackBarStyleTime() {
      return this.trackBarStyle
    },
    chart() {
      return this.obj.chart
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
        this.preDraw();
      },
      deep: true
    },
  },
  filters: {
  },
  created() {
  },
  data() {
    return {
      activeNames:[],
      trackBarStyle :null,
      dataMap: new Map(),
      titleStyle:{
        width:'100%',
        textAlign: ''
      },
      contentStyle:{
        color: '',
        backgroundColor: '',
        fontSize:'',
        paddingLeft:''
      },
    titleColor:'',
    titleBgColor:'',
    titleFontSize:''
    }
  },
  mounted() {
    this.preDraw();
  },
  methods :{
    preDraw() {
      //获取配置
      let xaxisData = JSON.parse(this.obj.chart.xaxis);
      let allData = []
        if(this.obj.chart.data){
          allData = this.obj.chart.data.tableRow;
        }

      let categoryXaxis = '';
      let contentXaxis = '';
      xaxisData.forEach(ietm => {
        if (ietm.flag === 'categoryXaxis') {
          categoryXaxis = ietm.dataeaseName
        }
        if (ietm.flag === 'contentXaxis') {
          contentXaxis = ietm.dataeaseName
        }
      })
      let map = new Map();
      if(categoryXaxis){
        allData.forEach(ietm=>{
          if(map.get(ietm[categoryXaxis])){
            let valueData = map.get(ietm[categoryXaxis]);
            valueData.push(ietm[contentXaxis])
            map.set(ietm[categoryXaxis],valueData);
          }else{
            let data = [];
            if(ietm[contentXaxis]){
              data.push(ietm[contentXaxis]);
            }
            map.set(ietm[categoryXaxis],data)
          }
        })
      }
      this.dataMap = map;
      //设置样式
      this.setStyle();
    },
    setStyle(){
      //样式设置
      if (this.chart.customStyle) {
        const customStyle = JSON.parse(this.chart.customStyle)
        if(customStyle.styleForm){
          //标题样式
          this.titleColor = customStyle.styleForm.titleColor;
          this.titleBgColor = customStyle.styleForm.titleBgColor;
          this.titleFontSize = customStyle.styleForm.titleFontSize + 'px';
          this.titleStyle.textAlign = customStyle.styleForm.titlePosition;
          //内容样式
          this.contentStyle.color = customStyle.styleForm.color;
          this.contentStyle.backgroundColor =  customStyle.styleForm.backgroundColor;
          this.contentStyle.fontSize = customStyle.styleForm.fontSize+'px';
          this.contentStyle.paddingLeft = customStyle.styleForm.paddingLeft+'px';
        }
      }
    },
    trackClick(trackAction) {
      this.pointParam.viewId = this.chart.id
      const param = this.pointParam
      if (!param || !param.data || !param.data.dimensionList) {
        // 地图提示没有关联字段 其他没有维度信息的 直接返回
        this.$warning(this.$t('panel.no_drill_field'))
        return
      }
      const linkageParam = {
        option: 'linkage',
        viewId: this.chart.id,
        dimensionList: this.pointParam.data.dimensionList,
        quotaList: this.pointParam.data.quotaList
      }
      const jumpParam = {
        option: 'jump',
        viewId: this.chart.id,
        dimensionList: this.pointParam.data.dimensionList,
        quotaList: this.pointParam.data.quotaList
      }
      switch (trackAction) {
        case 'drill':
          this.$emit('plugin-call-back', {
            eventName: 'plugin-chart-click',
            eventParam: this.pointParam
          })

          break
        case 'linkage':
          // this.$store.commit('addViewTrackFilter', linkageParam)
          this.$emit('plugin-call-back', {
            eventName: 'plugin-add-view-track-filter',
            eventParam: linkageParam
          })
          break
        case 'jump':
          this.$emit('plugin-call-back', {
            eventName: 'plugin-jump-click',
            eventParam: jumpParam
          })
          break
        default:
          break
      }
    },
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
::v-deep .el-collapse-item__header{
  color: var(--titleColor) !important;
  background-color: var(--titleBgColor) !important;
  font-size: var(--titleFontSize) !important;
}
</style>

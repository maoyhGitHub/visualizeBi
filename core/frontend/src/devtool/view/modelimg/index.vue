<template>
  <div style="display: flex;position:relative;" class="chart-class">
    <view-track-bar
      :default-expand-all="true"
      :highlight-current="true"
      ref="viewTrack"
      :track-menu="trackMenu"
      class="track-bar"
      :style="trackBarStyleTime"
    />
    <div :style="divStyle">
      <div style="width: 100%;display: flex;" v-for="item in option">
        <div :style="dataStyle">{{item.model}}</div>
        <div>
          <img v-for="intact in Number(item.intact)" style="padding: 5px" src="@/devtool/view/mainDev/entire01.png">
          <img v-for="incomplete in Number(item.incomplete)" style="padding: 5px" src="@/devtool/view/mainDev/entire02.png">
        </div>
      </div>
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
      trackBarStyle :null,
      option:[],
      divStyle:{
        width : '100%',
        overflowY : 'auto',
        backgroundColor: ''
      },
      dataStyle:{
        marginTop: '9px',
        color: ''
      }
    }
  },
  mounted() {
    this.preDraw();
  },
  methods :{
    preDraw() {
      //获取配置
      let xaxis = JSON.parse(this.obj.chart.xaxis);
      const allData = this.chart.data?.tableRow || [];
      let modelXaxis = '';
      let intactXaxis = ''
      let incompleteXaxis = ''
      xaxis.forEach(ietm => {
        if (ietm.flag === 'modelXaxis') {
          modelXaxis = ietm.dataeaseName
        }
        if (ietm.flag === 'intactXaxis') {
          intactXaxis = ietm.dataeaseName
        }
        if (ietm.flag === 'incompleteXaxis') {
          incompleteXaxis = ietm.dataeaseName
        }
      })
      let arr =[];
      allData.forEach(item=>{
        arr.push({model:item[modelXaxis]||'',intact:item[intactXaxis]||'',incomplete:item[incompleteXaxis]||''})
      })
      this.option = arr;
      //设置样式
      this.setStyle();
    },
    setStyle(){
      //样式设置
      if (this.chart.customStyle) {
        const customStyle = JSON.parse(this.chart.customStyle)
        if(customStyle.styleForm){
          this.divStyle.backgroundColor = customStyle.styleForm.backgroundColor;
          this.dataStyle.color = customStyle.styleForm.color;
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
    selectChange(value){
      this.pointParam = {
        data: {
          dimensionList: [{ id: this.chart.data.fields[1].id, value: value }],
          quotaList: [],
        }
      }
      if (this.trackMenu.length < 2) { // 只有一个事件直接调用
        this.trackClick(this.trackMenu[0])
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

</style>

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
    <div style="width: 100%" :style="{'--bgColor':bgColor,'--borderColor':borderColor,'--color':color}"  >
      <el-select
        style="width: 100%"
        :popper-append-to-body=false
        v-model="value"
        placeholder="请选择"
        class="deep-theme"
        @change="selectChange"
        clearable
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
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
        this.encapsulationParameters();
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
      popperClass:'',
      trackBarStyle :null,
      value: '',
      options:[],
      borderColor:'',
      bgColor:'',
      color:''

    }
  },
  mounted() {
    this.encapsulationParameters();
  },
  methods :{
    encapsulationParameters() {
      //获取配置
      let xaxis = JSON.parse(this.obj.chart.xaxis);
      //获取自定义配置
      let customAttr = JSON.parse(this.obj.chart.customAttr);
      let data = [];
      let fields = [];
        //判断当前'来源是否数据集'
        if (customAttr.isDataSet) {
          //判空
          if (xaxis.length > 0 ) {
            //获取数据源
            data = this.obj.chart.data.tableRow;
            //获取数据源
            fields = this.obj.chart.data.fields;
            //转换字段
            conversionKey();
            //转换内容
            conversionData();
            //获取code和value
            if (xaxis[0].length === 0 || xaxis[1].length === 0) {
              return
            }
            //判空
            if (data.length > 1) {
              let arr = [];
              let key = xaxis[0].name;
              let value = xaxis[1].name;
              for (let obj in data) {
                arr.push({value :data[obj][key] ,label :data[obj][value]})
              }
              data = arr;
            }
          }
        } else {
          if(customAttr.textarea){
            parseJson(customAttr.textarea);
          }
        }

      this.options = data;

      //转换字段
      function conversionKey() {
        let newFields = {};
        fields.forEach((e) => {
          newFields[e.dataeaseName] = e.name;
        })
        fields = newFields;
      }

      //转换内容
      function conversionData() {
        let newDatas = [];
        data.forEach((e) => {
          let newData = {};
          //获取每一个字段
          for (let index in e) {
            newData[fields[index]] = e[index];
          }
          newDatas.push(newData);
        });
        data = newDatas;
      }

      //解析json串
      function parseJson(json) {
        let arr = [];
        //反序列为对象
        let commonCode = JSON.parse(json);
        //封装数据
        for (let key in commonCode) {
          arr.push({value :key ,label :commonCode[key]})
        }
        data = arr;
      }

      //设置样式
      this.setStyle();
    },
    setStyle(){
      //样式设置
      if (this.chart.customStyle) {
        const customStyle = JSON.parse(this.chart.customStyle)
        if(customStyle.styleForm){
          // if(customStyle.styleForm.colorTheme == 'lightTheme'){//浅色主题样式
          //   this.popperClass = ''
          //   this.bgColor = ''
          //   this.color = ''
          // }else{//深色主题样式
          //   this.bgColor = '#0B4A8F'
          //   this.color = '#FFFFFF'
          //   this.popperClass = 'dropdown'
          // }
          if(customStyle.styleForm.color){//字体颜色
            this.color = customStyle.styleForm.color;
          }
          if(customStyle.styleForm.borderColor){//边框颜色
            this.borderColor = customStyle.styleForm.borderColor;
          }
          if(customStyle.styleForm.backgroundColor){ //背景颜色
            this.bgColor = customStyle.styleForm.backgroundColor;
          }
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
::v-deep .el-input__inner {
  background-color:var(--bgColor) !important;
  border: 1px solid var(--borderColor);
  color: var(--color) !important;
  padding: 0 0px 0 5px;
  border-radius: 0px;
}
::v-deep .el-scrollbar__view.el-select-dropdown__list{
  width: 100%;
  height: 250px;
  overflow-y: auto;
  position: fixed;
  color: var(--color) !important;
  background-color:var(--bgColor) !important;
  //  color:  #FFFFFF !important;
  //  background-color:#0B4A8F !important;
}

::v-deep .el-select-dropdown__item {
  //color: #FFFFFF;
  //background-color:#0B4A8F;
  color: var(--color)!important;
  font-size: 14px;
  padding: 0 20px;
  position: relative;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 34px;
  line-height: 34px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  cursor: pointer;
}

::v-deep   .el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #173046 !important;
  box-shadow: 0px 0px 78px rgba(1, 194, 255, 0.4) inset;
}
.dropdown {
  background: linear-gradient(248deg, #3C556A 0%, #173046 100%);
  color: #a2d5e5;
  border: 1px solid #5E7788;
  box-sizing: border-box;
  z-index: 99999 !important;
  box-shadow: 0px 0px 78px rgba(1, 194, 255, 0.4) inset;
  .el-select-dropdown__item {
    //color: #FFFFFF;
    //background-color:#0B4A8F;
    color: var(--color, #FFFFFF);
  }
 //.el-scrollbar__view el-select-dropdown__list{
 //    //color: var(--color, #FFFFFF);
 //    //background-color:var(--bgColor,#0B4A8F);
 //  color:  #FFFFFF !important;
 //  background-color:#0B4A8F !important;
 //}


}

</style>

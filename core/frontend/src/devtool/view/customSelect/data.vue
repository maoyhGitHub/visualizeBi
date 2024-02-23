<template xmlns="http://www.w3.org/1999/html">
  <div>
    <div class="el-divider el-divider--horizontal"/>
    <span style="width: 80px;text-align: right;">
        <span>来源是否数据集:</span>
    </span>
    <el-checkbox v-model="customAttr.isDataSet" @change="addIsDataSet">是</el-checkbox>
<!--    <el-switch-->
<!--      v-model="customAttr.isDataSet"-->
<!--      active-color="#13ce66"-->
<!--      inactive-color="#A9A4A4"-->
<!--      @change="addIsDataSet"-->
<!--    >-->
<!--    </el-switch>-->
    <br>
    <!-- 通用代码(Type)-->
    <el-row v-if="customAttr.isDataSet" class="padding-lr" style="margin-top: 6px;">
      <span>通用代码(Type)</span>
      <draggable
        v-model="view.customFilter"
        group="drag"
        animation="300"
        :move="onMove"
        class="theme-item-class"
        style="padding:2px 0 0 0;width:100%;min-height: 32px;border-radius: 4px;border: 1px solid #DCDFE6;overflow-x: auto;display: flex;align-items: center;background-color: white;"
        @add="addCustomFilter"
        @update="calcData(true)"
      >
        <transition-group class="draggable-group">
          <filter-item
            v-for="(item,index) in view.customFilter"
            :key="item.id"
            :param="param"
            :index="index"
            :item="item"
            :dimension-data="dimensionData"
            :quota-data="quotaData"
            @onFilterItemRemove="filterItemRemove"
            @editItemFilter="showEditFilter"
          />
        </transition-group>
      </draggable>
      <div v-if="!view.customFilter || view.customFilter.length === 0" class="drag-placeholder-style">
        <span class="drag-placeholder-style-span">{{ $t('chart.placeholder_field') }}</span>
      </div>
    </el-row>
    <!--    通用代码(Code)-->
    <el-row v-if="customAttr.isDataSet" class="padding-lr">
            <span style="width: 80px;text-align: right;">
                <span >通用代码(Code)</span>/<span >{{ $t('chart.dimension') }}</span>
            </span>
      <draggable
        v-model="commonCode"
        group="drag"
        animation="300"
        :move="onMove"
        class="drag-block-style"
        @add="addCommonCode"
        @update="calcData(true)"
      >
        <transition-group class="draggable-group">
          <dimension-item
            v-for="(item,index) in commonCode"
            :key="item.id"
            :param="param"
            :index="index"
            :item="item"
            :dimension-data="dimensionData"
            :quota-data="quotaData"
            @onDimensionItemChange="dimensionItemChange"
            @onDimensionItemRemove="dimensionItemRemoveCode"
            @editItemFilter="showDimensionEditFilter"
            @onNameEdit="showRename"
          />
        </transition-group>
      </draggable>
      <div v-if="!commonCode || commonCode.length === 0" class="drag-placeholder-style">
        <span class="drag-placeholder-style-span">{{ $t('chart.placeholder_field') }}</span>
      </div>
    </el-row>
   <!-- 通用代码(Value)-->
    <el-row v-if="customAttr.isDataSet" class="padding-lr">
            <span style="width: 80px;text-align: right;">
                <span >通用代码(Value)</span>/<span >{{ $t('chart.dimension') }}</span>
            </span>
    <draggable
      v-model="commonValue"
      group="drag"
      animation="300"
      :move="onMove"
      class="drag-block-style"
      @add="addCommonValue"
      @update="calcData(true)"
    >
      <transition-group class="draggable-group">
        <dimension-item
          v-for="(item,index) in commonValue"
          :key="item.id"
          :param="param"
          :index="index"
          :item="item"
          :dimension-data="dimensionData"
          :quota-data="quotaData"
          @onDimensionItemChange="dimensionItemChange"
          @onDimensionItemRemove="dimensionItemRemoveValue"
          @editItemFilter="showDimensionEditFilter"
          @onNameEdit="showRename"
        />
      </transition-group>
    </draggable>
    <div v-if="!commonValue || commonValue.length === 0" class="drag-placeholder-style">
      <span class="drag-placeholder-style-span">{{ $t('chart.placeholder_field') }}</span>
    </div>
  </el-row>

    <!-- 否 -->
    <div v-if="!customAttr.isDataSet" class="el-divider el-divider--horizontal"/>
    <span v-if="!customAttr.isDataSet" style="width: 80px;text-align: right;">
               <span>通用代码(对象)</span>
            </span>
    <el-tooltip
      v-if="!customAttr.isDataSet"
      class="item"
      effect="dark"
      placement="bottom"
    >
      <div slot="content">
        { "01" :"正常" ,"02" :"异常"}
      </div>
      <i
        class="el-icon-info"
        style="cursor: pointer;color: #606266;font-size: 12px"
      />
    </el-tooltip>
    <br>
    <el-input
      v-if="!customAttr.isDataSet"
      type="textarea"
      :rows="2"
      placeholder="请输入内容"
      v-model="customAttr.textarea"
      @change="addTextArea"
    >
    </el-input>

  </div>
</template>

<script>
/*  import DimensionItem from '@/components/views/DimensionItem'
  import QuotaItem from '@/components/views/QuotaItem'
  import FilterItem from '@/components/views/FilterItem'
  import DrillItem from '@/components/views/DrillItem'
  import messages from '@/de-base/lang/messages'*/

import bus from '@/utils/bus'
import DimensionItem from '@/devtool/components/views/DimensionItem.vue'
import QuotaItem from '@/devtool/components/views/QuotaItem.vue'
import FilterItem from '@/devtool/components/views/FilterItem.vue'
import DrillItem from '@/devtool/components/views/DrillItem.vue'

  export default {
    props: {
      request :{
        type: Object,
        default: () => {}
      },
      obj: {
        type: Object,
        default: () => {}
      }
    },
    components: {DimensionItem, QuotaItem, FilterItem, DrillItem},
    data() {
      return {
        widgets: [],
        places: [],
        moveId: -1,
        commonCode: [],
        commonValue: [],
        //非指标维度 自定义放这
        customAttr:{
          isDataSet: true,
          textarea:''
        }
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
      },
      dimensionData() {
        return this.obj.dimensionData
      },
      quotaData() {
        return this.obj.quotaData
      }
    },
    created() {
      this.commonCode = this.view.xaxis && this.view.xaxis.length >= 1 && [this.view.xaxis[0]] || this.commonCode;
      this.commonValue = this.view.xaxis && this.view.xaxis.length >= 2&& [this.view.xaxis[1]] || this.commonValue;
      this.customAttr = this.view.customAttr;
    },
    methods: {
      normalizer(node) {
        const resultNode = {
          id: node.code,
          label: node.name
        }
        if (node.children && node.children.length > 0) {
          resultNode.children = node.children
        }

        if (resultNode.children && (!node.children || node.children.length === 0)) {
          delete resultNode.children
        }
        return resultNode
      },
      executeAxios (url, type, data, callBack) {
        const param = {
          url: url,
          type: type,
          data: data,
          callBack: callBack
        }
        this.$emit('execute-axios', param)
        if (process.env.NODE_ENV === 'development') {
          execute(param).then(res => {
            if (param.callBack) {
              param.callBack(res)
            }
          }).catch(e => {
            if (param.error) {
              param.error(e)
            }
          })
        }
      },

      onMove(e, originalEvent) {
        this.moveId = e.draggedContext.element.id
        return true
      },
      calcData(cache) {
        this.view.xaxis = [...this.commonCode, ...this.commonValue ];//维度数据
        this.view.customAttr= this.customAttr;//自定义数据
        bus.$emit('calc-data', { cache })
        // this.$emit('plugin-call-back', {
        //   eventName: 'calc-data',
        //   eventParam: {cache}
        // })
      },
      locationItemRemove(item) {
        console.log(item)
        if (item.removeType === 'source') {
          this.source.splice(0, 1)
          //this.view.xaxis[0] = undefined;
        } else if (item.removeType === 'target') {
          this.target.splice(0, 1)
          //this.view.xaxis[1] = undefined;
        }
        this.calcData(true)
      },
      dimensionItemChange(item) {
        this.calcData(true)
      },
      dimensionItemRemoveCode(item) {
        if (item.removeType === 'dimension') {
          this.commonCode.splice(item.index, 1)
        } else if (item.removeType === 'dimensionExt') {
          this.view.xaxisExt.splice(item.index, 1)
        }
        this.calcData(true)
      },
      dimensionItemRemoveValue(item) {
        if (item.removeType === 'dimension') {
          this.commonValue.splice(item.index, 1)
        } else if (item.removeType === 'dimensionExt') {
          this.view.xaxisExt.splice(item.index, 1)
        }
        this.calcData(true)
      },
      showDimensionEditFilter(item) {

        this.$emit('plugin-call-back', {
          eventName: 'show-dimension-edit-filter',
          eventParam: item
        })
      },
      showRename(item) {

        this.$emit('plugin-call-back', {
          eventName: 'show-rename',
          eventParam: item
        })
      },

      quotaItemChange(item) {
        this.calcData(true)
      },
      quotaItemRemove(item) {

        if (item.removeType === 'quota') {
          this.view.yaxis.splice(item.index, 1)
        } else if (item.removeType === 'quotaExt') {
          this.view.yaxisExt.splice(item.index, 1)
        }
        this.calcData(true)
      },
      showQuotaEditFilter(item) {

        this.$emit('plugin-call-back', {
          eventName: 'show-quota-edit-filter',
          eventParam: item
        })
      },
      showQuotaEditCompare(item) {

        this.$emit('plugin-call-back', {
          eventName: 'show-quota-edit-compare',
          eventParam: item
        })
      },
      dragCheckType(list, type) {
        if (list && list.length > 0) {
          for (let i = 0; i < list.length; i++) {
            if (list[i].groupType !== type) {
              list.splice(i, 1)
            }
          }
        }
      },
      dragMoveDuplicate(list, e) {
        const that = this
        const dup = list.filter(function(m) {
          return m.id === that.moveId
        })
        if (dup && dup.length > 1) {
          list.splice(e.newDraggableIndex, 1)
        }
      },
      addCustomFilter(e) {
        // 记录数等自动生成字段不做为过滤条件
        if (this.view.customFilter && this.view.customFilter.length > 0) {
          for (let i = 0; i < this.view.customFilter.length; i++) {
            if (this.view.customFilter[i].id === 'count') {
              this.view.customFilter.splice(i, 1)
            }
          }
        }
        this.dragMoveDuplicate(this.view.customFilter, e)
        this.calcData(true)
      },
      filterItemRemove(item) {
        this.view.customFilter.splice(item.index, 1)
        this.calcData(true)
      },
      showEditFilter(item) {
        this.$emit('plugin-call-back', {
          eventName: 'show-edit-filter',
          eventParam: item
        })
      },
      addDrill(e) {
        this.dragCheckType(this.view.drillFields, 'd')
        this.dragMoveDuplicate(this.view.drillFields, e)
        this.calcData(true)
      },
      drillItemChange(item) {
        this.calcData(true)
      },
      drillItemRemove(item) {
        this.view.drillFields.splice(item.index, 1)
        this.calcData(true)
      },

      //-----------------------------------------
      //来源是否数据集
      addIsDataSet(e) {
        this.calcData(true)
      },
      //通用代码(Code)
      addCommonCode(e) {
        this.dragCheckType(this.commonCode, 'd')
        this.dragMoveDuplicate(this.commonCode, e)
        if (this.commonCode.length > 1) {
          this.commonCode = [this.commonCode[0]]
        }
        this.calcData(true)
      },
      //通用代码(Code)
      addCommonValue(e) {
        this.dragCheckType(this.commonValue, 'd')
        this.dragMoveDuplicate(this.commonValue, e)
        //唯一
        if (this.commonValue.length > 1) {
          this.commonValue = [this.commonValue[0]]
        }
        this.calcData(true)
      },
      //通用代码(Code)
      addTextArea(e) {
        debugger
        this.calcData(true)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .padding-lr {
    padding: 0 6px;
  }

  .itxst {
    margin: 10px;
    text-align: left;
  }

  .col {
    width: 40%;
    flex: 1;
    padding: 10px;
    border: solid 1px #eee;
    border-radius: 5px;
    float: left;
  }

  .col + .col {
    margin-left: 10px;
  }

  .view-panel {
    display: flex;
    height: calc(100% - 40px);
    background-color: #f7f8fa;
  }

  .blackTheme .view-panel {
    background-color: var(--MainBG);
  }

  .drag-list {
    height: calc(100% - 26px);
    overflow: auto;
  }

  .item-dimension {
    padding: 2px 10px;
    margin: 2px 2px 0 2px;
    border: solid 1px #eee;
    text-align: left;
    color: #606266;
    /*background-color: rgba(35,46,64,.05);*/
    background-color: white;
    display: block;
    word-break: break-all;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .blackTheme .item-dimension {
    border: solid 1px;
    border-color: var(--TableBorderColor);
    color: var(--TextPrimary);
    background-color: var(--MainBG);
  }

  .item-dimension + .item-dimension {
    margin-top: 2px;
  }

  .item-dimension:hover {
    color: #1890ff;
    background: #e8f4ff;
    border-color: #a3d3ff;
    cursor: pointer;
  }

  .blackTheme .item-dimension:hover {
    color: var(--Main);
    background: var(--ContentBG);
    cursor: pointer;
  }

  .item-quota {
    padding: 2px 10px;
    margin: 2px 2px 0 2px;
    border: solid 1px #eee;
    text-align: left;
    color: #606266;
    background-color: white;
    display: block;
    word-break: break-all;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .blackTheme .item-quota {
    border: solid 1px;
    border-color: var(--TableBorderColor);
    color: var(--TextPrimary);
    background-color: var(--MainBG);
  }

  .item-quota + .item-quota {
    margin-top: 2px;
  }

  .item-quota:hover {
    color: #67c23a;
    background: #f0f9eb;
    border-color: #b2d3a3;
    cursor: pointer;
  }

  .blackTheme .item-quota:hover {
    background: var(--ContentBG);
  }

  .el-form-item {
    margin-bottom: 0;
  }

  span {
    font-size: 12px;
  }

  .tab-header > > > .el-tabs__header {
    border-top: solid 1px #eee;
    border-right: solid 1px #eee;
  }

  .tab-header > > > .el-tabs__item {
    font-size: 12px;
    padding: 0 60px!important;
  }

  .blackTheme .tab-header > > > .el-tabs__item {
    background-color: var(--MainBG);
  }

  .tab-header > > > .el-tabs__nav-scroll {
    padding-left: 0 !important;
  }

  .tab-header > > > .el-tabs__header {
    margin: 0 !important;
  }

  .tab-header > > > .el-tabs__content {
    height: 100%;
  }

  .draggable-group {
    display: block;
    width: 100%;
    height: calc(100% - 6px);
  }

  .chart-icon {
    width: 20px;
    height: 20px;
  }

  .el-radio {
    margin: 5px;
  }

  .el-radio > > > .el-radio__label {
    padding-left: 0;
  }

  .attr-style {
    height: calc(100vh - 56px - 60px - 40px - 40px);
  }

  .blackTheme .attr-style {
    color: var(--TextPrimary);
  }

  .attr-selector {
    width: 100%;
    height: 100%;
    margin: 6px 0;
    padding: 0 4px;
    display: flex;
    align-items: center;
    background-color: white
  }

  .blackTheme .attr-selector {

    background-color: var(--MainBG)
  }

  .disabled-none-cursor {
    cursor: not-allowed;
    pointer-events: none;
  }

  .chart-class {
    height: 100%;
    padding: 10px;
  }

  .table-class {
    height: calc(100% - 20px);
  }

  .dialog-css > > > .el-dialog__title {
    font-size: 14px;
  }

  .dialog-css > > > .el-dialog__header {
    padding: 20px 20px 0;
  }

  .dialog-css > > > .el-dialog__body {
    padding: 10px 20px 20px;
  }

  .filter-btn-class {
    padding: 6px;
    border: none;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .chart-error-class {
    text-align: center;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ece7e7;
  }

  .blackTheme .chart-error-class {

    background-color: var(--MainBG)
  }

  .field-height {
    height: calc(50% - 20px);
    border-top: 1px solid #E6E6E6;
  }

  .blackTheme .field-height {

    border-top: 1px solid;
    border-color: var(--TableBorderColor) !important;
  }

  .padding-tab {
    padding: 0;
    height: 100%;
  }

  .tree-select-span {
    > > > div.vue-treeselect__control {
      height: 32px !important;
      font-weight: normal !important;
    }
  }

  .drag-block-style {
    padding: 2px 0 0 0;
    width: 100%;
    min-height: 32px;
    border-radius: 4px;
    border: 1px solid #DCDFE6;
    overflow-x: hidden;
    display: flex;
    align-items: center;
    background-color: white;
  }

  .blackTheme .drag-block-style {
    border: 1px solid;
    border-color: var(--TableBorderColor);
    background-color: var(--ContentBG);
  }

  .drag-placeholder-style {
    position: absolute;
    top: calc(50% - 2px);
    left: 0;
    width: 100%;
    color: #CCCCCC;
  }

  .blackTheme .drag-placeholder-style {
    color: var(--TextPrimary);
  }

  .drag-placeholder-style-span {
    padding-left: 16px;
  }

  .blackTheme .theme-border-class {
    color: var(--TextPrimary) !important;
    background-color: var(--ContentBG);
  }

  .blackTheme .padding-lr {
    border-color: var(--TableBorderColor) !important;
  }

  .blackTheme .theme-item-class {
    background-color: var(--MainBG) !important;
    border-color: var(--TableBorderColor) !important;
  }

  .icon-class {
    color: #6c6c6c;
  }

  .blackTheme .icon-class {
    color: #cccccc;
  }

  .result-count {
    width: 80px;
  }

  .radio-span > > > .el-radio__label {
    margin-left: 4px;
  }
</style>

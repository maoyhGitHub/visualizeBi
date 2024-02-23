<template>
  <div
    class="bar-main"
    :class="showEditPosition"
    @mousedown="showLabelInfo"
  >
    <input
      id="input"
      ref="files"
      type="file"
      accept="image/*"
      hidden
      @click="e => {e.target.value = '';}"
      @change="handleFileChange"
    >
    <div
      v-if="linkageAreaShow"
      style="margin-right: -1px;width: 20px"
    >
      <el-checkbox
        v-model="linkageInfo.linkageActive"
        size="medium"
      />
      <linkage-field
        v-if="linkageInfo.linkageActive"
        :element="element"
      />
    </div>
    <div
      v-if="positionCheck('multiplexing') && showMultiplexingCheck"
      style="margin-right: 1px;width: 18px;z-index: 5"
    >
      <el-checkbox
        v-model="multiplexingCheckModel"
        size="medium"
        @change="multiplexingCheck"
      />
    </div>
    <div
      v-if="batchOptAreaShow"
      style="margin-right: -1px;width: 20px;z-index: 5"
    >
      <el-checkbox
        size="medium"
        @change="batchOptChange"
      />
    </div>
    <div v-if="normalAreaShow">
      <span :title="$t('panel.edit')">
        <i
          v-if="activeModel==='edit'&&curComponent&&editFilter.includes(curComponent.type)"
          class="icon iconfont icon-edit"
          @click.stop="edit"
        />
      </span>
      <span :title="$t('panel.matrix')">
        <i
          v-if="activeModel==='edit'&&curComponent.auxiliaryMatrix"
          class="icon iconfont icon-shujujuzhen"
          @click.stop="auxiliaryMatrixChange"
        />
      </span>
      <span :title="$t('panel.suspension')">
        <i
          v-if="activeModel==='edit'&&!curComponent.auxiliaryMatrix"
          class="icon iconfont icon-xuanfuanniu"
          @click.stop="auxiliaryMatrixChange"
        />
      </span>
      <span :title="$t('panel.enlarge')">
        <i
          v-if="enlargeShow"
          class="icon iconfont icon-fangda"
          @click.stop="showViewDetails('enlarge')"
        />
      </span>
      <span :title="$t('panel.details')">
        <i
          v-if="detailsShow"
          class="icon iconfont icon-chakan"
          @click.stop="showViewDetails('details')"
        />
      </span>
      <span :title="$t('route.exportExcel')">
        <i
          v-if="exportExcelShow"
          style="line-height: 24px"
          class="el-icon-document-delete"
          @click.stop="exportExcelDownload()"
        />
      </span>
      <span title="填报数据">
        <i
          v-if="detailsShow && activeModel==='preview' && showFillingConfig"
          class="el-icon-edit"
          style="margin-top: 3px"
          @click.stop="showViewDetails('customData')"
        />
      </span>
      <setting-menu
        v-if="activeModel==='edit'"
        style="float: right;height: 24px!important;"
        @amRemoveItem="amRemoveItem"
        @linkJumpSet="linkJumpSet"
        @boardSet="boardSet"
      >
        <span
          slot="icon"
          :title="$t('panel.setting')"
        >
          <i
            class="icon iconfont icon-shezhi"
            style="margin-top:2px; width: 16px;"
          />
        </span>
      </setting-menu>
      <span :title="$t('panel.cancel_linkage')">
        <i
          v-if="curComponent.type==='view'&&existLinkage"
          class="icon iconfont icon-quxiaoliandong"
          @click.stop="clearLinkage"
        />
      </span>
      <span :title="$t('panel.switch_picture')">
        <i
          v-if="activeModel==='edit'&&curComponent&&curComponent.type==='picture-add'"
          class="icon iconfont icon-genghuan"
          @click.stop="goFile"
        />
      </span>
      <!--更换图片-->
      <span :title="$t('panel.switch_picture')">
        <i
          v-if="activeModel==='edit'&&curComponent&&curComponent.type==='picture-view'"
          class="icon iconfont icon-genghuan"
          @click.stop="openPictureDialog"
        />
      </span>
      <el-popover
        v-if="selectFieldShow"
        width="200"
        trigger="click"
        @mousedown="fieldsAreaDown"
      >
        <fields-list
          :fields="curFields"
          :element="element"
        />
        <i
          slot="reference"
          :disabled="element.editing"
          :title="$t('panel.select_field')"
          class="icon iconfont icon-datasource-select"
          style="margin-left: 4px;cursor: pointer;font-size: 14px;"
        />
      </el-popover>
      <span :title="$t('panel.jump')">
        <a
          v-if="showJumpFlag"
          :title="curComponent.hyperlinks.content "
          :target="curComponent.hyperlinks.openMode "
          :href="curComponent.hyperlinks.content "
        >
          <i class="icon iconfont icon-com-jump"/>
        </a>
      </span>

      <map-layer-controller
        v-if="chart && showMapLayerController"
        :chart="chart"
        :series-id-map="seriesIdMap"
      />
    </div>

    <!--跳转设置-->
    <el-dialog
      :visible.sync="linkJumpSetVisible"
      width="900px"
      class="dialog-css"
      :show-close="true"
      :destroy-on-close="true"
      :append-to-body="true"
    >
      <link-jump-set
        v-if="linkJumpSetVisible"
        :view-id="linkJumpSetViewId"
        @closeJumpSetDialog="closeJumpSetDialog"
      />
    </el-dialog>

    <!--背景设置-->
    <el-dialog
      :visible.sync="boardSetVisible"
      width="750px"
      top="5vh"
      class="dialog-css"
      :close-on-click-modal="false"
      :show-close="false"
      :destroy-on-close="true"
      :append-to-body="true"
    >
      <background
        v-if="boardSetVisible"
        @backgroundSetClose="backgroundSetClose"
      />
    </el-dialog>
    <!--图库-->
    <el-dialog
      v-if="pictureViewVisible"
      title="图库"
      :visible.sync="pictureViewVisible"
      width="62%"
      append-to-body
    >
      <el-row>
        <el-col :span="12">
          <span>图片分类:</span>
          <el-select v-model="imageTypeId" @change="changeImageType" style="width: 122px">
            <el-option
              v-for="item in imageTypeList"
              :key="item.id"
              :label="item.fileCategory"
              :value="item.id"
            />
          </el-select>
        </el-col>
        <el-col :span="12">
          <span style="float: right">
          <el-input
            v-model="imageNameText"
            :placeholder="'请输入图片名称'"
            size="small"
            class="de-input-search"
            @keyup.enter.native="search"
            clearable
          >
            <svg-icon
              slot="prefix"
              icon-class="de-search"
            />
          </el-input>
          </span>
        </el-col>
      </el-row>
      <div v-if="showFlag" style="height: 452px;overflow-y: auto">
        <el-table-column v-for="(item,index) in urlList" :key="index" class="img">
          <el-popover
            :ref="index"
            popper-class="img-popover"
            placement="top-start"
            trigger="hover"
          >
            <div>
              <img
                :src="windowUrl + item.fileName"
                style="width: 120px;max-height: 100px; transform: scale(3); background-color: #f9f7f7;"
                loading="lazy"
              />
            </div>
            <div
              slot="reference"
              class="imgDiv"
            >
              <el-radio
                :label=item.fileName
                v-model="imgRadio"
                @change="onChangeImg(item.fileName)"
              >
                <template #default>
                  <div class="radio-option">
                    <img :src="windowUrl + item.fileName" style="width: 108px;max-height: 68px;"/>
                    <span class="imgSpan" :title="item.fileOriginName">{{
                        item.fileOriginName
                      }}</span>
                  </div>
                </template>
              </el-radio>
            </div>
          </el-popover>
        </el-table-column>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="pictureViewVisible = false">取 消</el-button>
    <el-button type="primary" @click="submitPicture(false)">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import bus from '@/utils/bus'
import SettingMenu from '@/components/canvas/components/editor/SettingMenu'
import LinkageField from '@/components/canvas/components/editor/LinkageField'
import toast from '@/components/canvas/utils/toast'
import FieldsList from '@/components/canvas/components/editor/FieldsList'
import LinkJumpSet from '@/views/panel/linkJumpSet'
import Background from '@/views/background/index'
import MapLayerController from '@/views/chart/components/map/MapLayerController'
import {
  findAllResourceName,
  findResourceNameByCondition,
  uploadFileResult,
  uploadPictureResult
} from '@/api/staticResource/staticResource'
import {findCategory} from "@/api/system/IconCategory";
import eventBus from '@/components/canvas/utils/eventBus'
import { hasDataPermission } from '@/utils/permission'
import { exportExcelDownload } from '@/components/canvas/utils/utils'

export default {
  components: { Background, LinkJumpSet, FieldsList, SettingMenu, LinkageField, MapLayerController },

  props: {
    canvasId: {
      type: String,
      required: true
    },
    terminal: {
      type: String,
      default: 'pc'
    },
    sourceElement: {
      type: Object,
      default: () => {
      }
    },
    element: {
      type: Object,
      required: true
    },
    active: {
      type: Boolean,
      required: false,
      default: false
    },
    // 当前模式 preview 预览 edit 编辑，
    activeModel: {
      type: String,
      required: false,
      default: 'preview'
    },
    previewVisible: {
      type: Boolean,
      default: false
    },
    showPosition: {
      type: String,
      required: false,
      default: 'NotProvided'
    },
    chart: {
      type: Object,
      default: null
    },
    seriesIdMap: {
      type: Object,
      default: () => {
        return {
          id: ''
        }
      }
    }
  },
  data() {
    return {
      systemOS: 'Mac',
      maxImageSize: 15000000,
      boardSetVisible: false,
      linkJumpSetVisible: false,
      linkJumpSetViewId: null,
      curFields: [],
      multiplexingCheckModel: false,
      barWidth: 24,
      componentType: null,
      linkageActiveStatus: false,
      editFilter: [
        'view',
        'custom',
        'custom-button'
      ],
      timer: null,
      viewXArray: [],
      showFillingConfig: false,
      pictureViewVisible:false,
      urlList:[],
      windowUrl: '',
      imgRadio:'',
      fileName:'',
      imageTypeId:'', //图片分类ID
      imageTypeList:[], //图片分类List
      showFlag: false,
      imageNameText:''
    }
  },

  computed: {
    yaxis() {
      if (!this.chart) return []
      return JSON.parse(this.chart.yaxis)
    },
    showMapLayerController() {
      return this.curComponent.type === 'view' && this.terminal === 'pc' && this.curComponent.propValue.innerType && this.curComponent.propValue.innerType === 'map' && this.yaxis.length > 1
    },
    detailsShow() {
      return this.curComponent.type === 'view' && this.terminal === 'pc' && this.curComponent.propValue.innerType && this.curComponent.propValue.innerType !== 'richTextView'
    },
    exportExcelShow() {
      return this.detailsShow && hasDataPermission('export', this.$store.state.panel.panelInfo.privileges) && this.chart
    },
    enlargeShow() {
      return this.curComponent.type === 'view' && this.curComponent.propValue.innerType && this.curComponent.propValue.innerType !== 'richTextView' && !this.curComponent.propValue.innerType.includes('table')
    },
    selectFieldShow() {
      return this.activeModel === 'edit' && this.curComponent.type === 'view' && this.curComponent.propValue.innerType && this.curComponent.propValue.innerType === 'richTextView' && this.curComponent.editing
    },
    curComponentTypes() {
      const types = []
      this.componentData.forEach(component => {
        types.push(component.type)
      })
      return types
    },
    showMultiplexingCheck() {
      return this.element.type !== 'custom-button' || (this.element.type === 'custom-button' && !this.curComponentTypes.includes('custom-button'))
    },
    showEditPosition() {
      if (this.activeModel === 'edit' && !this.linkageAreaShow && !this.batchOptAreaShow) {
        const toRight = (this.canvasStyleData.width - this.element.style.left - this.element.style.width) * this.curCanvasScaleSelf.scalePointWidth
        const toLeft = this.element.style.left * this.curCanvasScaleSelf.scalePointWidth
        if (this.barWidth < toRight) {
          return 'bar-main-right'
        } else if (this.barWidth > toRight && this.barWidth > toLeft) {
          return 'bar-main-left-inner'
        } else {
          return 'bar-main-left-outer'
        }
      } else {
        return 'bar-main-preview'
      }
    },
    showJumpFlag() {
      return this.curComponent && this.curComponent.hyperlinks && this.curComponent.hyperlinks.enable
    },
    // batch operation area
    batchOptAreaShow() {
      return this.batchOptStatus && this.element.type === 'view' && !this.element.isPlugin
    },
    // 联动区域按钮显示
    linkageAreaShow() {
      return this.linkageSettingStatus && this.element !== this.curLinkageView && this.element.type === 'view'
    },
    // 编辑或预览区域显示
    normalAreaShow() {
      return !this.linkageSettingStatus && !this.batchOptStatus && !this.positionCheck('multiplexing')
    },
    existLinkage() {
      let linkageFiltersCount = 0
      this.componentData.forEach(item => {
        if (item.linkageFilters && item.linkageFilters.length > 0) {
          item.linkageFilters.forEach(linkage => {
            if (this.element.propValue.viewId === linkage.sourceViewId) {
              linkageFiltersCount++
            }
          })
        }
      })
      return linkageFiltersCount
    },
    linkageInfo() {
      return this.targetLinkageInfo[this.element.propValue.viewId]
    },
    miniHeight() {
      return this.mobileLayoutStatus ? 1 : 1
    },
    miniWidth() {
      return this.mobileLayoutStatus ? 1 : 1
    },
    curCanvasScaleSelf() {
      return this.curCanvasScaleMap[this.canvasId]
    },
    ...mapState([
      'menuTop',
      'menuLeft',
      'menuShow',
      'curComponent',
      'componentData',
      'canvasStyleData',
      'linkageSettingStatus',
      'targetLinkageInfo',
      'curLinkageView',
      'curCanvasScaleMap',
      'batchOptStatus',
      'mobileLayoutStatus',
      'curBatchOptComponents',
      'panelViewDetailsInfo'
    ])
  },
  watch: {
    linkageAreaShow: {
      handler(val) {
        // 1.当前正在进行联动设置（val） 2.当前视图联动未启用!this.linkageInfo.linkageActive
        // 3.当前视图没有设置过当前目标联动!this.linkageInfo.sourceViewId
        // 4.数据集相同 this.linkageInfo.tableId === this.targetLinkageInfo[this.curLinkageView.propValue.viewId].tableId)
        // 满足以上条件自动勾选
        if (val && !this.linkageInfo.linkageActive && !this.linkageInfo.sourceViewId && this.linkageInfo.tableId === this.targetLinkageInfo[this.curLinkageView.propValue.viewId].tableId) {
          this.linkageInfo.linkageActive = true
        }
      },
      immediate: true
    }
  },
  mounted() {
    //加载图片分类下拉框数据
    findCategory().then((res) => {
      this.imageTypeList = res.data
    })
    if (navigator.platform.indexOf('Mac') === -1) {
      this.systemOS = 'Other'
    }
    this.initCurFields()
    if (this.element.type === 'view') {
      bus.$on('initCurFields-' + this.element.id, this.initCurFields)
      eventBus.$on('viewEnlarge', this.viewEnlarge)
    }
    if (!this.$store.state.changeParam.showStaticDataConfig) {
      this.showFillingConfig = true
    }
    if (this.$store.state.changeParam.linkStaticDataShow){
      this.showFillingConfig = true
    }
  },
  beforeDestroy() {
    eventBus.$off('preview', this.showViewDetails)
  },
  methods: {
    //分类改变事件
    changeImageType(val){
      this.showFlag = true;
      //根据所选分类 加载图库图片
      try {
        findResourceNameByCondition({categoryId:val, name: this.imageNameText}).then((rsp) => {
          this.urlList = rsp.data;
          if(this.urlList.length>0){
            this.showFlag = true;
          }else{
            this.showFlag = false;
          }
          this.windowUrl = window.location.origin + '/static-resource/';
        })
      } catch (e) {
        console.error('findAllResourceName error', e)
      }
    },
    //关键字查询
    search() {
      findResourceNameByCondition({categoryId:this.imageTypeId, name: this.imageNameText}).then((rsp) => {
        this.urlList = rsp.data;
        if(this.urlList.length>0){
          this.showFlag = true;
        }else{
          this.showFlag = false;
        }
        this.windowUrl = window.location.origin + '/static-resource/';
      })
    },
    openPictureDialog(){ //打开图库
      this.pictureViewVisible =true;
    },
    onChangeImg(fileName) { //获取选中图片
      this.fileName = fileName;
    },
    submitPicture(pictureViewVisible){ //确定按钮事件
      if(this.fileName){
        this.handlePicturChange();
      }
      this.pictureViewVisible = pictureViewVisible;
    },
    handlePicturChange() {//背景图片赋值
      uploadPictureResult(this.fileName, (fileUrl) => {
        this.curComponent.propValue = fileUrl
        this.$store.commit('recordSnapshot', 'handleFileChange')
      })
    },
    viewEnlarge() {
      this.showViewDetails('enlarge')
    },
    backgroundSetClose() {
      this.boardSetVisible = false
    },
    linkJumpSet() {
      this.linkJumpSetViewId = this.element.propValue.viewId
      this.linkJumpSetVisible = true
    },
    closeJumpSetDialog() {
      this.linkJumpSetVisible = false
    },
    fieldsAreaDown(e) {
      // ignore
      e.preventDefault()
    },
    initCurFields() {
      if (this.element.type === 'view') {
        const chartInfo = this.panelViewDetailsInfo[this.element.propValue.viewId]
        if (chartInfo) {
          this.curFields = []
          const chartDetails = JSON.parse(chartInfo)
          if (chartDetails.type === 'richTextView' && chartDetails.data) {
            this.curFields = chartDetails.data.fields
          }
        }
      }
    },
    positionCheck(position) {
      return this.showPosition.includes(position)
    },
    multiplexingCheck(val) {
      if (val) {
        // push
        this.$store.commit('addCurMultiplexingComponent', {
          'component': this.sourceElement,
          'componentId': this.element.id
        })
      } else {
        // remove
        this.$store.commit('removeCurMultiplexingComponentWithId', this.element.id)
      }
    },
    closePreview() {
      this.$emit('closePreview')
    },
    destroyTimer() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
    },
    showViewDetails(openType = 'details') {
      this.$emit('showViewDetails', { openType: openType })
    },
    exportExcelDownload() {
      exportExcelDownload(this.chart)
    },
    auxiliaryMatrixChange() {
      if (this.curComponent.auxiliaryMatrix) {
        this.curComponent.auxiliaryMatrix = false
        this.$emit('amRemoveItem')
      } else {
        this.curComponent.x = Math.round(this.curComponent.style.left / this.curCanvasScaleSelf.matrixStyleOriginWidth) + 1
        this.curComponent.y = Math.round(this.curComponent.style.top / this.curCanvasScaleSelf.matrixStyleOriginHeight) + 1
        this.curComponent.sizex = Math.round(this.curComponent.style.width / this.curCanvasScaleSelf.matrixStyleOriginWidth)
        this.curComponent.sizey = Math.round(this.curComponent.style.height / this.curCanvasScaleSelf.matrixStyleOriginHeight)
        this.curComponent.sizey = this.curComponent.sizey > this.miniHeight ? this.curComponent.sizey : this.miniHeight
        this.curComponent.sizex = this.curComponent.sizex > this.miniWidth ? this.curComponent.sizex : this.miniWidth
        this.curComponent.auxiliaryMatrix = true
        this.$emit('amAddItem')
      }
      setTimeout(() => {
        this.recordMatrixCurShadowStyle()
      }, 50)
      this.$store.commit('canvasChange')
      bus.$emit('auxiliaryMatrixChange')
    },
    // 记录当前样式 跟随阴影位置 矩阵处理
    recordMatrixCurShadowStyle() {
      const left = (this.curComponent.x - 1) * this.curCanvasScaleSelf.matrixStyleWidth
      const top = (this.curComponent.y - 1) * this.curCanvasScaleSelf.matrixStyleHeight
      const width = this.curComponent.sizex * this.curCanvasScaleSelf.matrixStyleWidth
      const height = this.curComponent.sizey * this.curCanvasScaleSelf.matrixStyleHeight
      const style = {
        left: left,
        top: top,
        width: width,
        height: height
      }
      this.$store.commit('setShapeStyle', style)
      // resize
      this.$emit('resizeView')
    },
    edit() {
      if (this.curComponent.type === 'custom') {
        bus.$emit('component-dialog-edit', 'update')
      } else if (this.curComponent.type === 'custom-button') {
        bus.$emit('button-dialog-edit')
      } else if (this.curComponent.type === 'v-text' || this.curComponent.type === 'de-rich-text' || this.curComponent.type === 'rect-shape') {
        bus.$emit('component-dialog-style')
      } else {
        bus.$emit('change_panel_right_draw', true)
      }
    },
    linkageEdit() {

    },
    amRemoveItem() {
      this.$emit('amRemoveItem')
    },
    // 清除相同sourceViewId 的 联动条件
    clearLinkage() {
      this.$store.commit('clearViewLinkage', this.element.propValue.viewId)
    },
    goFile() {
      this.$refs.files.click()
    },
    showLabelInfo(e) {
      // ignore
      e.preventDefault()
    },
    sizeMessage() {
      this.$notify({
        message: this.$t('panel.image_size_tips'),
        position: 'top-left'
      })
    },
    handleFileChange(e) {
      const file = e.target.files[0]
      if (!file.type.includes('image')) {
        toast(this.$t('panel.image_size_tips'))
        return
      }
      if (file.size > this.maxImageSize) {
        this.sizeMessage()
      }
      uploadFileResult(file, (fileUrl) => {
        this.curComponent.propValue = fileUrl
        this.$store.commit('recordSnapshot', 'handleFileChange')
      })
    },
    boardSet() {
      this.boardSetVisible = true
    },
    batchOptChange(val) {
      if (val) {
        // push
        this.$store.commit('addCurBatchComponent', this.element.propValue.viewId)
      } else {
        // remove
        this.$store.commit('removeCurBatchComponentWithId', this.element.propValue.viewId)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.img{
  float: left;
  width: 108px;
  margin-left: 15px;
  margin-top: 15px;
}
.imgSpan {
  width: 100px;
  overflow: hidden; /*超出部分隐藏*/
  text-overflow: ellipsis; /*超出部分省略号表示*/
  white-space: nowrap; /*强制单行显示*/
  display: inline-block; /*转换为行内块元素*/
  margin-left: -104px;
  position: absolute;
  margin-top: 70px;
}

.bar-main {
  position: absolute;
  float: right;
  z-index: 10;
  border-radius: 2px;
  padding-left: 3px;
  padding-right: 0px;
  cursor: pointer !important;
  background-color: var(--primary, #3370ff);
}

.bar-main ::v-deep i {
  color: white;
  float: right;
  margin-right: 3px;
}

.bar-main ::v-deep .el-checkbox__inner {
  width: 16px;
  height: 16px;
}

.bar-main ::v-deep .el-checkbox__inner::after {
  width: 4.5px;
}

.bar-main-right {
  width: 22px;
  right: -25px;
}

.bar-main-left-inner {
  width: 22px;
  left: 0px;
}

.bar-main-left-outer {
  width: 22px;
  left: -25px;
}

.bar-main-preview {
  right: 0px;
}
.imgDiv {
  border: 1px solid #d7d7d7;
  height: 107px;
  width: 117px;
  margin-left: -8px;
}
</style>

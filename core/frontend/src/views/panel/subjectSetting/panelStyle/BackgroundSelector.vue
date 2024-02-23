<template>
  <div style="width: 100%;margin-top: 12px">
    <el-form
      ref="overallSettingForm"
      :model="overallSettingForm"
      size="mini"
    >
      <el-col>
        <span class="custom-item-text">{{ $t('panel.panel_background_item') }}</span>
      </el-col>
      <el-col>
        <el-radio-group
          v-model="panel.backgroundType"
          size="mini"
          @change="onChangeType()"
        >
          <el-radio label="color">{{ $t('chart.color') }}</el-radio>
          <el-radio label="image">{{ $t('panel.photo') }}</el-radio>
        </el-radio-group>
      </el-col>
      <el-col
        v-show="panel.backgroundType==='color'"
        :span="24"
      >
        <el-col :span="4">
          <el-color-picker
            v-model="panel.color"
            :predefine="predefineColors"
            size="mini"
            class="color-picker-custom"
            @change="onChangeType"
          />
        </el-col>
        <el-col :span="5">
          <span class="params-title-small">{{ $t('chart.not_alpha') }}</span>
        </el-col>
        <el-col :span="15">
          <el-slider
            v-model="panel.alpha"
            show-input
            :show-input-controls="false"
            input-size="mini"
            @change="onChangeType"
          />
        </el-col>
      </el-col>
      <el-col
        v-show="panel.backgroundType==='image'"
        :span="10"
      >
        <el-form-item label="图片分类">
          <el-select v-model="imageTypeId" @change="changeImageType">
            <el-option
              v-for="item in imageTypeList"
              :key="item.id"
              :label="item.fileCategory"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-row v-if="showFlag" style="width: 252px!important;height: 275px; overflow-y: auto">
          <el-table-column v-for="(item,index) in urlList" prop="headimg" :show-overflow-tooltip="true" class="img">
            <div class="imgDiv">
              <el-radio
                :label=item.fileName
                v-model="imgRadio"
                @change="onChangeImg(item.fileName)"
              >
                <template #default>
                  <div class="radio-option">
                    <img :src="windowUrl + item.fileName" style="width: 108px;max-height: 68px; margin-left: 3px"/>
                    <span class="imgSpan" style="margin-left: -100px;position: absolute;margin-top: 73px;width: 100px;
                       text-overflow: ellipsis" :title="item.fileOriginName">{{
                        item.fileOriginName
                      }}</span>
                  </div>
                </template>
              </el-radio>
            </div>
          </el-table-column>
        </el-row>
      </el-col>
    </el-form>
  </div>
</template>

<script>

import { mapState } from 'vuex'
import { deepCopy, imgUrlTrans } from '@/components/canvas/utils/utils'
import { COLOR_PANEL } from '@/views/chart/chart/chart'
import { uploadFileResult , findResourceNameByCondition} from '@/api/staticResource/staticResource'
import { findCategory } from  '@/api/system/IconCategory'

export default {
  name: 'BackgroundSelector',
  data() {
    return {
      maxImageSize: 15000000,
      imgRadio:'',
      urlList:[],
      windowUrl: '',
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      uploadDisabled: false,
      panel: null,
      predefineColors: COLOR_PANEL,
      overallSettingForm: {},
      imageTypeId:'',
      imageTypeList:[],
      showFlag: false

    }
  },
  computed: mapState([
    'canvasStyleData'
  ]),
  watch: {
    // deep监听panel 如果改变 提交到 store
  },
  created() {
    //加载图片分类下拉框数据
    findCategory().then((res) => {
      this.imageTypeList = res.data
    })
    // 初始化赋值
    this.panel = this.canvasStyleData.panel
    if (this.panel.imageUrl && typeof (this.panel.imageUrl) === 'string') {
      this.fileList.push({ url: imgUrlTrans(this.panel.imageUrl) })
    }
  },
  methods: {
    //分类改变事件
    changeImageType(val){
      this.showFlag = true;
      //根据所选分类 加载图库图片
      try {
        findResourceNameByCondition({categoryId:val,name:''}).then((rsp) => {
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

    //选中照片事件
    onChangeImg(fileName) {
      this.panel.imageUrl = '/static-resource/' + fileName
      this.fileList = [{ url: imgUrlTrans(this.panel.imageUrl) }]
      this.commitStyle()
    },
    goFile() {
      this.$refs.files.click()
    },
    commitStyle() {
      const canvasStyleData = deepCopy(this.canvasStyleData)
      canvasStyleData.panel = this.panel
      this.$store.commit('setCanvasStyle', canvasStyleData)
      this.$store.commit('recordSnapshot', 'commitStyle')
    },
    onChangeType() {
      this.commitStyle()
    },
    handleRemove(file, fileList) {
      this.uploadDisabled = false
      this.panel.imageUrl = null
      this.fileList = []
      this.commitStyle()
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    upload(file) {
      const _this = this
      if (file.size > this.maxImageSize) {
        this.sizeMessage()
      }
      uploadFileResult(file.file, (fileUrl) => {
        _this.$store.commit('canvasChange')
        _this.panel.imageUrl = fileUrl
        _this.fileList = [{ url: imgUrlTrans(this.panel.imageUrl) }]
        _this.commitStyle()
      })
    },
    reUpload(e) {
      const file = e.target.files[0]
      const _this = this
      if (file.size > this.maxImageSize) {
        this.sizeMessage()
      }
      uploadFileResult(file, (fileUrl) => {
        _this.$store.commit('canvasChange')
        _this.panel.imageUrl = fileUrl
        _this.fileList = [{ url: imgUrlTrans(this.panel.imageUrl) }]
        _this.commitStyle()
      })
    },
    sizeMessage() {
      this.$notify({
        message: this.$t('panel.image_size_tips'),
        position: 'top-left'
      })
    }
  }
}
</script>

<style scoped>
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

  /*span标签属于行内元素（inline），所以无法设置高度和宽度；
  如果需要改变其宽高，就需要将其转变为块体元素（block）或行内块体元素（inline-block）*/

}
.avatar-uploader {
  position: relative;
  margin-left: 0px;
  margin-top: 8px;
  height: 80px;
  overflow: hidden;
}

.avatar-uploader ::v-deep .el-upload {
  width: 80px;
  height: 80px;
  line-height: 80px;
}

.avatar-uploader ::v-deep .el-upload-list li {
  width: 80px !important;
  height: 80px !important;
}

.disabled ::v-deep .el-upload--picture-card {
  display: none;
}

.shape-item {
  padding: 6px;
  border: none;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.form-item-slider ::v-deep .el-form-item__label {
  font-size: 12px;
  line-height: 38px;
}

.form-item ::v-deep .el-form-item__label {
  font-size: 12px;
}

.el-select-dropdown__item {
  padding: 0 20px;
}

span {
  font-size: 12px
}

.el-form-item {
  margin-bottom: 6px;
}

.color-picker-custom {
  margin-left: 0px;
  cursor: pointer;
  margin-top: 8px;
  z-index: 1004;
}

.custom-item {
  width: 70px;
}

.re-update-span {
  cursor: pointer;
  color: #3370FF;
  size: 14px;
  line-height: 22px;
  font-weight: 400;
}

.image-hint {
  color: #8F959E;
  size: 14px;
  line-height: 22px;
  font-weight: 400;
}

.custom-item-text {
  font-weight: 400 !important;
  font-size: 14px !important;
  color: var(--TextPrimary, #1F2329) !important;
  line-height: 22px;
}


::v-deep .el-slider__input {
  width: 40px;
  padding-left: 0px;
  padding-right: 0px;
}

::v-deep .el-input__inner {
  padding: 0px !important;
}

::v-deep .el-slider__runway {
  margin-right: 60px !important;
}

.params-title-small {
  font-size: 12px !important;
  color: var(--TextPrimary, #1F2329) !important;
  line-height: 40px;
}
.imgDiv {
  border: 1px solid #d7d7d7;
  height: 107px;
  width: 117px;
  margin-left: -8px;
}
</style>

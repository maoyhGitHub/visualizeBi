<template>
  <de-layout-content>
    <div
      v-loading="$store.getters.loadingMap[$store.getters.currentPath]"
      class="tabs-container flex-tabs"
    >
      <div class="de-tabs-left">
        <icon-category-list
          ref="iconCategoryList"
          :category-type="categoryType"
          :icon-category-list="iconCategoryList"
          @categoryDelete="categoryFolderDelete"
          @categoryEdit="categoryEdit"
          @showCurrentCategory="showCurrentCategory"
          @showCategoryEditDialog="showCategoryEditDialog"
        />
      </div>
      <div class="de-tabs-right">
        <!--        <div
                  v-if="currentTemplateLabel"
                  class="active-template"
                >
                  {{ currentTemplateLabel }}&nbsp;&nbsp;({{
                    currentTemplateShowList.length
                  }})-->
        <div
          class="active-template"
        >
          <el-row class="top-operate">
            <el-col :span="12">
              <el-upload
                class="upload-demo"
                action
                :show-file-list="false"
                :http-request="upload"
                accept=".jpeg,.jpg,.png,.gif,.svg"
                list-type="picture"
                multiple>
                <el-button size="	medium" type="primary">上传图片</el-button>
                <div slot="tip" class="el-upload__tip" style="margin-left: 105px;margin-top: -25px;">只能上传jpg/png文件</div>
              </el-upload>
              <el-button type="primary" icon="el-icon-delete" style="margin-left: 240px;position: absolute;top: -1px;"
                         @click="delImg">删除
              </el-button>
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
        </div>
        <div style="height: calc(100% - 50px);overflow-y: auto ;margin-top: 30px;border:solid;border-color: #F5F6F7">
          <el-table-column
            v-for="(item,index) in urlList"
            :key="index"
            class="img"
          >
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
                <el-checkbox
                  v-model="imgCheckted"
                  :label="item.fileName"
                >
                  <template #default>
                    <div style="height: 120px">
                      <div style="height: 100px; position: absolute">
                        <img
                          :src="windowUrl + item.fileName"
                          style="width: 120px;max-height: 100px; margin-top: 10px"
                          loading="lazy"
                        />
                      </div>
                      <span
                        class="imgSpan"
                        :title="item.fileOriginName"
                      >
                        {{ item.fileOriginName }}
                      </span>
                    </div>
                  </template>
                </el-checkbox>
              </div>
            </el-popover>
          </el-table-column>
        </div>
      </div>
    </div>
    <el-dialog
      :title="dialogTitle"
      :visible.sync="editTemplate"
      append-to-body
      class="de-dialog-form"
      width="600px"
    >
      <el-form
        ref="categoryEditForm"
        class="de-form-item"
        :model="categoryEditForm"
        :rules="categoryEditFormRules"
      >
        <el-form-item
          :label="dialogTitleLabel"
          prop="fileCategory"
        >
          <el-input v-model="categoryEditForm.fileCategory"/>
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <deBtn
          secondary
          @click="close()"
        >{{ $t("commons.cancel") }}
        </deBtn>
        <deBtn
          type="primary"
          @click="saveCategoryEdit(categoryEditForm)"
        >{{ $t("commons.confirm") }}
        </deBtn>
      </div>
    </el-dialog>
  </de-layout-content>
</template>
<script>
import DeLayoutContent from '@/components/business/DeLayoutContent'
import {
  uploadFileResult,
  findAllResourceName,
  findResourceNameByCondition,
  delImg
} from '@/api/staticResource/staticResource'
import {save, categoryDelete, findCategory} from '@/api/system/IconCategory'
import IconCategoryList from './IconCategoryList'
import msgCfm from '@/components/msgCfm/index'

export default {
  components: {DeLayoutContent, IconCategoryList},
  mixins: [msgCfm],
  data() {
    return {
      windowUrl: '',
      urlList: [],
      imgCheckted: [],
      iconCategoryList: [],
      categoryType: '',
      categoryEditForm: {},
      dialogTitleLabel: '',
      editTemplate: false,
      imageNameText: '',
      originName: '',
      categoryEditFormRules: {
        fileCategory: [
          { required: true, trigger: 'blur', validator: this.roleValidator },
          {
            required: true,
            message: this.$t('commons.input_content'),
            trigger: 'blur'
          },
          {
            max: 50,
            message: this.$t('commons.char_can_not_more_50'),
            trigger: 'change'
          }
        ]
      },
    }
  },
  computed: {
    nameList() {
      const { nodeType } = this.categoryEditForm || {}
      if (nodeType === 'folder') {
        return this.iconCategoryList.map((ele) => ele.fileCategory)
      }
      return []
    }
  },
  mounted() {
    this.getTree();
  },
  methods: {
    upload(item) {
      uploadFileResult(item.file, (fileUrl) => {
        this.findResourceNameByCategory(this.categoryType, '');
      }, this.categoryType)
    },
    findResourceNameByCategory(nodeId, searchName) {
      try {
        findResourceNameByCondition({categoryId: nodeId, name: searchName}).then((rsp) => {
          this.urlList = rsp.data;
          this.windowUrl = window.location.origin + '/static-resource/';
        })
      } catch (e) {
        console.error('findAllResourceName error', e)
      }
    },
    delImg() {
      let imgName = this.imgCheckted;
      if (imgName.length < 1) {
        this.$confirm('请选择要删除的图片', '提示', {
          confirmButtonText: '确认',
          type: "warning"
        })
      } else {
        this.$confirm('确认删除选中的图片吗', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: "warning"
        }).then(() => {
          try {
            imgName.join(',')
            delImg(imgName).then((rsp) => {
              if (rsp.data === 0) {
                this.$message.error('删除失败')
              } else {
                this.$message.success('删除成功')
                this.findResourceNameByCategory(this.categoryType);
                this.imgCheckted = [];
              }
            })
          } catch (e) {
            console.error('findAllResourceName error', e)
          }
        })
      }
    },
    showCurrentCategory(node) {
      try {
        findResourceNameByCondition({categoryId: node, name: ''}).then((rsp) => {
          this.urlList = rsp.data;
          this.windowUrl = window.location.origin + '/static-resource/';
          this.categoryType = node;
        })
      } catch (e) {
        console.error('findAllResourceName error', e)
      }
    },
    showCategoryEditDialog(type, categoryInfo) {
      this.categoryEditForm = null
      this.formType = type
      if (type === 'edit') {
        this.categoryEditForm = JSON.parse(JSON.stringify(categoryInfo))
        this.dialogTitle = this.$t(
          `system_parameter_setting.${
            this.categoryEditForm.nodeType === 'folder'
              ? 'edit_classification'
              : 'edit_template'
          }`
        )
        this.originName = this.categoryEditForm.fileCategory
      } else {
        this.dialogTitle = this.$t('panel.add_category')
        this.categoryEditForm = {
          fileCategory: '',
          nodeType: 'folder',
        }
      }
      this.dialogTitleLabel = this.$t(
        `system_parameter_setting.${
          this.categoryEditForm.nodeType === 'folder'
            ? 'classification_name'
            : 'template_name'
        }`
      )
      this.editTemplate = true
    },
    categoryEdit(categoryInfo) {
      this.showCategoryEditDialog('edit', categoryInfo)
    },
    categoryFolderDelete(id) {
      if (id) {
        categoryDelete(id).then((response) => {
          this.openMessageSuccess('commons.delete_success')
          this.getTree()
        })
      }
    },
    saveCategoryEdit(categoryEditForm) {
      this.$refs['categoryEditForm'].validate((valid) => {
        if (valid) {
          save(JSON.stringify(categoryEditForm)).then((response) => {
            this.close()
            this.openMessageSuccess(
              `system_parameter_setting.${
                this.categoryEditForm.id
                  ? 'rename_succeeded'
                  : 'added_successfully'
              }`
            )
            this.getTree()
          })
        } else {
          return false
        }
      })
    },
    close() {
      this.$refs['categoryEditForm'].resetFields()
      this.editTemplate = false
    },
    getTree() {
      findCategory().then((res) => {
        this.iconCategoryList = res.data
        this.showFirst()
      })
    },
    showFirst() {
      // 判断是否默认点击第一条
      if (this.iconCategoryList && this.iconCategoryList.length > 0) {
        let showFirst = true
        this.iconCategoryList.forEach((template) => {
          if (template.id === this.categoryType) {
            showFirst = false
          }
        })
        if (showFirst) {
          this.$nextTick().then(() => {
            const [obj = {}] = this.iconCategoryList
            this.$refs.iconCategoryList.nodeClick(obj)
          })
        } else {
          this.showCurrentCategory(this.categoryType)
        }
      }
    },
    search() {
      this.findResourceNameByCategory(this.categoryType, this.imageNameText);
    },
    roleValidator(rule, value, callback) {
      if (this.nameRepeat(value)) {
        const { nodeType } = this.categoryEditForm || {}
        callback(
          new Error(
            this.$t(
              `system_parameter_setting.${
                nodeType === 'folder'
                  ? 'name_already_exists_type'
                  : 'the_same_category'
              }`
            )
          )
        )
      } else {
        callback()
      }
    },
    nameRepeat(value) {
      if (!this.nameList || this.nameList.length === 0) {
        return false
      }
      // 编辑场景 不能 因为名称重复而报错
      if (this.formType === 'edit' && this.originName === value) {
        return false
      }
      return this.nameList.some((name) => name === value)
    }
  }
}
</script>

<style scoped lang="scss">
.img {
  float: left;
  width: 170px;
  margin-top: 30px;
}
.imgSpan {
  width: 100px;
  overflow: hidden; /*超出部分隐藏*/
  display: inline-block; /*转换为行内块元素*/
  position: absolute; margin-top: 107px;
  text-overflow: ellipsis
}
/*!*悬浮展示图片*!
img:hover{
  transform: scale(3);
  background-color: #f9f7f7;
  position: absolute;
  z-index: 999;
}*/
.imgDiv {
  border: 1px solid #d7d7d7;
  height: 130px;
  margin-left: 10px;
}
.tabs-container {
  height: calc(100% - 0px);
  background: var(--ContentBG, #ffffff);
  overflow-x: auto;
}

.flex-tabs {
  display: flex;
  background: #f5f6f7;
}

.de-tabs-left {
  background: #fff;
  width: 269px;
  border-right: 1px solid rgba(31, 35, 41, 0.15);
  padding: 24px;
}

.de-tabs-right {
  flex: 1;
  background: #fff;
  padding: 24px 0 24px 24px;
  overflow: hidden;

  .template-box {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
    box-sizing: border-box;
    align-content: flex-start;
    height: calc(100% - 10px);
    width: 100%;
    padding-bottom: 24px;
  }

  /*.active-template {
    margin: 4px 0 20px 0;
    padding-right: 24px;
    font-family: "PingFang SC";
    font-style: normal;
    font-weight: 500;
    font-size: 16px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: var(--deTextPrimary, #1f2329);
  }*/
}
</style>

<!--特殊样式，不能scope，单独处理-->
<style lang="scss">
  .img-popover {
    background-color: rgba(116, 255, 114, 0) !important;
    border-color: rgba(241, 255, 253, 0) !important;
    box-shadow: 0 0 0 rgba(25, 25, 25, 0) inset;
  }
</style>


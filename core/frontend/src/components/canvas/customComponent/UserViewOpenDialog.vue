<template>
  <div>
    <el-dialog
      :visible="showOpenDialogVisible"
      :width=dialogWidth
      :fullscreen="isFullscreen"
      class="open-dialog-css"
      :destroy-on-close="true"
      :show-close="true"
      :append-to-body="isEdit"
      :close-on-click-modal="true"
      @close="closeDialogSetDialog"
    >
      <iframe
        :style="{ width: '100%', height: dialogHeight + 'vh', position : 'absolute'}"
        align="center" scrolling="auto" frameborder="0" allowTransparency="true"
        :src="openDialogUrl"
      />
      <div>
        <span class="closeButton" @click="closeDialogSetDialog"></span>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {queryPanelLinkJumpInfoParams} from "@/api/panel/linkJump";

export default {
  name: 'UserViewOpenDialog',
  props: {
    openDialogUrl: {
      type: String,
      default: ''
    },
    isEdit: {
      type: Boolean,
      require: false,
      default: true
    },
    jumpInfoData: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      showOpenDialogVisible: false,
      isFullscreen: false,
      dialogHeight: '',
      dialogWidth: '60%'
    }
  },
  mounted() {
    this.showOpenDialogVisible = true
    queryPanelLinkJumpInfoParams(this.jumpInfoData.id).then(rsp => {
      if (rsp.data.isFullscreen === 'isFullscreen') {
        this.isFullscreen = true
        this.dialogHeight = 100
      }else if (rsp.data.isFullscreen === 'otherSize'){
        this.dialogHeight = rsp.data.dialogHeight
        this.dialogWidth = rsp.data.dialogWidth + '%'
      }else {
        this.dialogHeight = 70
      }
    })
  },
  methods: {
    closeDialogSetDialog() {
      this.$emit('closeDialogSetDialog')
    }
  }

}
</script>

<style lang="scss" scoped>
//右上角关闭按钮样式
.closeButton {
  width: 12px;
  height: 12px;
  margin-top: 5px;
  margin-right: 5px;
  float: right;
  cursor: pointer;
  color: white;
  &::before,
  &::after {
    position: absolute;
    // right: 0;
    width: 2px;
    height: 12px;
    margin-left: 2px;
    content: '';
    background: #7C7F83;
  }
  &::before {
    transform: rotate(-45deg);
  }

  &::after {
    transform: rotate(45deg);
  }
}


.open-dialog-css ::v-deep .el-dialog__body {
  padding: 0;
  background-color: rgba(0, 0, 0, 0); /* 设置半透明背景 */
}

.open-dialog-css ::v-deep .el-dialog__header {
  display: none;
}

.open-dialog-css{
  ::v-deep .el-dialog__wrapper {
    display: flex;
    align-items: stretch;
    justify-content: center;
  }

  ::v-deep .dialog-content {
    height: 100%;
    overflow: auto;
  }
}
</style>

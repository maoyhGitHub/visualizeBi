<template>
  <el-upload
    action=""
    :multiple="false"
    :show-file-list="false"
    :file-list="fileList"
    accept=".xls,.xlsx,.csv"
    :on-error="uploadFail"
    :http-request="upload"
    name="file"
  >
    <el-button
      size="mini"
      icon="el-icon-upload2"
    >导入excel
    </el-button>
  </el-upload>
</template>

<script>
import { importCustomExcel } from '@/api/link'
import { getToken, setToken } from '@/utils/auth'
import i18n from '@/lang'
import { $alert } from '@/utils/message'
import store from '@/store'
import msgCfm from '@/components/msgCfm/index'
import cancelMix from '@/views/dataset/add/cancelMix'
import Config from '@/settings'

const token = getToken()
const RefreshTokenKey = Config.RefreshTokenKey

export default {
  name: 'AddCustomExcel',
  mixins: [msgCfm, cancelMix],
  props: {
    param: {
      type: Object,
      default: null
    },
    tableId: {
      type: String,
      default: ''
    },
    editType: {
      type: Number,
      default: 0
    },
    nameList: {
      type: Array,
      default: () => []
    },
    originName: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      loading: false,
      showLeft: true,
      sheetObj: { datasetName: ' ', fields: [] },
      sheets: [],
      mode: '1',
      fileList: [],
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: token,
        'Accept-Language': i18n.locale.replace('_', '-')
      },
      baseUrl: process.env.VUE_APP_BASE_API,
      path: '',
      uploading: false,
      props: {
        label: 'excelLabel',
        children: 'sheets'
      },
      count: 1,
      excelData: [],
      defaultExpandedKeys: [],
      defaultCheckedKeys: []
    }
  },
  mounted() {
    console.log('to', token)
  },
  methods: {
    beforeUpload(file) {
      this.uploading = true
    },
    uploadFail(response, file, fileList) {
      let myError = response.toString()
      myError = myError.replace('Error: ', '')
      if (myError.indexOf('AuthenticationException') >= 0) {
        const message = i18n.t('login.tokenError')
        $alert(
          message,
          () => {
            store.dispatch('user/logout').then(() => {
              location.reload()
            })
          },
          {
            confirmButtonText: i18n.t('login.re_login'),
            showClose: false
          }
        )
        return
      }

      const errorMessage =
        JSON.parse(myError).message + ', ' + this.$t('dataset.parse_error')
      this.path = ''
      this.fields = []
      this.sheets = []
      this.fileList = []
      this.uploading = false
      this.$message({
        type: 'error',
        message: errorMessage,
        showClose: true
      })
    },
    uploadSuccess(response, file, fileList) {
      store.dispatch('changeParam/setExcelCustomData', response.data)
      this.uploading = false
      this.fileList = fileList
      if (response.headers[RefreshTokenKey]) {
        const refreshToken = response.headers[RefreshTokenKey]
        setToken(refreshToken)
        store.dispatch('user/refreshToken', refreshToken)
      }
    },
    upload(file) {
      const formData = new FormData()
      formData.append('file', file.file)
      formData.append('tableId', this.param.tableId)
      formData.append('viewId', this.param.viewId)
      importCustomExcel(formData).then(response => {
        store.dispatch('changeParam/setExcelCustomData', response.data)
      })
    }
  }
}
</script>

<style scoped>
</style>


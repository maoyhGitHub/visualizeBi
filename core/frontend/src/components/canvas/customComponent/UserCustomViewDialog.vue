<template>
  <de-container
    v-loading="$store.getters.loadingMap[$store.getters.currentPath]"
  >
    <de-main-container>
      <table-edit-normal
        v-if="staticDataShow"
        ref="tableEditNormalRef"
        :enable-scroll="false"
        :chart="chartStaticData"
        :show-summary="false"
        class="table-class-dialog"
      />
    </de-main-container>
  </de-container>
</template>

<script>

import TableEditNormal from '@/views/chart/components/table/TableEditNormal'
import DeMainContainer from '@/components/dataease/DeMainContainer'
import DeContainer from '@/components/dataease/DeContainer'
import { mapState } from 'vuex'
import { exportDetails, innerExportDetails } from '@/api/panel/panel'
import { getLinkToken, getToken } from '@/utils/auth'
import { saveTempStaticData } from '@/api/link'

export default {
  name: 'UserCustomViewDialog',
  components: {
    DeMainContainer,
    DeContainer,
    TableEditNormal
  },
  props: {
    chart: {
      type: Object,
      default: null
    },
    chartTable: {
      type: Object,
      default: null
    },
    openType: {
      type: String,
      default: 'details'
    }

  },
  data() {
    return {
      refId: null,
      element: {},
      lastMapChart: null,
      chartStaticData: {},
      staticDataShow: false
    }
  },
  computed: {
    ...mapState([
      'isClickComponent',
      'curComponent',
      'componentData',
      'canvasStyleData',
      'lastViewRequestInfo'
    ])
  },
  created() {
    this.getCurStaticData()
  },
  mounted() {
  },
  methods: {
    exportExcel() {
      const _this = this
      _this.exportExcelDownload()
    },
    setLastMapChart(data) {
      this.lastMapChart = JSON.parse(JSON.stringify(data))
    },
    exportExcelDownload(snapshot, width, height, containData) {
      const excelHeader = JSON.parse(JSON.stringify(this.chart.data.fields)).map(item => item.name)
      const excelTypes = JSON.parse(JSON.stringify(this.chart.data.fields)).map(item => item.deType)
      const excelHeaderKeys = JSON.parse(JSON.stringify(this.chart.data.fields)).map(item => item.dataeaseName)
      // let excelData = JSON.parse(JSON.stringify(this.chart.data.tableRow)).map(item => excelHeaderKeys.map(i => item[i]))
      let excelData = []
      const excelName = this.chart.name
      let detailFields = []
      if (this.chart.data.detailFields?.length) {
        detailFields = this.chart.data.detailFields.map(item => {
          const temp = {
            name: item.name,
            deType: item.deType,
            dataeaseName: item.dataeaseName
          }
          return temp
        })
        if (containData) {
          excelData = JSON.parse(JSON.stringify(this.chart.data.tableRow)).map(item => {
            const temp = excelHeaderKeys.map(i => {
              if (i === 'detail' && !item[i] && Array.isArray(item['details'])) {
                const arr = item['details']
                if (arr?.length) {
                  return arr.map(ele => detailFields.map(field => ele[field.dataeaseName]))
                }
                return null
              }
              return item[i]
            })
            return temp
          })
        }
      }
      const request = {
        viewId: this.chart.id,
        viewName: excelName,
        header: excelHeader,
        details: excelData,
        excelTypes: excelTypes,
        snapshot: snapshot,
        snapshotWidth: width,
        snapshotHeight: height,
        componentFilterInfo: this.lastViewRequestInfo[this.chart.id],
        excelHeaderKeys: excelHeaderKeys,
        detailFields
      }
      let method = innerExportDetails
      const token = this.$store.getters.token || getToken()
      const linkToken = this.$store.getters.linkToken || getLinkToken()
      if (!token && linkToken) {
        method = exportDetails
      }
      method(request).then((res) => {
        const blob = new Blob([res], { type: 'application/vnd.ms-excel' })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = URL.createObjectURL(blob)
        link.download = excelName + '.xlsx' // 下载的文件名
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
      })
    },

    renderComponent() {
      return this.chart.render
    },
    getCurStaticData() {
      const excelHeaderKeys = JSON.parse(JSON.stringify(this.chart.data.fields)).map(item => item.dataeaseName)
      const excelData = JSON.parse(JSON.stringify(this.chart.data.tableRow)).map(item => excelHeaderKeys.map(i => item[i]))
      const request = {
        viewId: this.chart.id,
        tableId: this.chart.tableId,
        jsonDetails: JSON.stringify(this.chart.data),
        excelKeys: JSON.stringify(this.chart.data.x),
        excelData: JSON.stringify(excelData)
      }
      saveTempStaticData(request).then((res) => {
        const jsonDetails = JSON.parse(res.data.jsonDetails)
        let data = jsonDetails.tableRow
        if (res.data.status === 2) {
          data = JSON.parse(res.data.newJsonDetails)
        }
        this.staticDataShow = true
        this.chartStaticData = this.chartTable
        this.chartStaticData.data.tableRow = data
        this.chartStaticData.data.x = jsonDetails.x
      })
    },
    getPlxTableData() {
      return this.$refs.tableEditNormalRef.getPlxTableData()
    }
  }
}
</script>

<style lang="scss" scoped>
.ms-aside-container {
  height: 70vh;
  min-width: 400px;
  max-width: 400px;
  padding: 0 0;
}

.ms-main-container {
  height: 70vh;
  border: 1px solid #E6E6E6;
}

.chart-class {
  height: 100%;
}

.table-class-dialog {
  height: 100%;
  overflow-y: auto !important;
}

.canvas-class {
  position: relative;
  width: 100%;
  height: 100%;
  background-size: 100% 100% !important;
}

.abs-container {
  position: absolute;
  width: 100%;
  margin-left: -20px;

  .ms-main-container {
    padding: 0px !important;
  }
}

.svg-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

</style>

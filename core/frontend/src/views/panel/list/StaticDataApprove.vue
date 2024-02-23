<template>
  <div>
    <el-table
      :data="tableData"
      stripe
      border
      height="450"
      style="width: 100%"
    >
      <el-table-column
        show-overflow-tooltip
        prop="viewName"
        label="视图名称"
        width="300"
      />
      <el-table-column
        prop="updateTime"
        label="提交时间"
      />
      <el-table-column
        align="center"
        label="审批"
        width="290"
      >
        <template slot-scope="scope">
          <el-popover
            trigger="click"
            placement="left"
            width="720"
          >
            <ux-grid
              :ref="plxTable+scope.row.viewId"
              size="mini"
              :width-resize="true"
              class="table-class"
            >
              <ux-table-column
                v-for="field in scope.row.fields"
                :key="field.name"
                min-width="160px"
                :field="field.dataeaseName"
                :resizable="true"
                :title="field.name"
              />
            </ux-grid>
            <el-button
              slot="reference"
              type="primary"
              plain
              size="mini"
              @click="initTableData(scope.row)"
            >审批数据预览</el-button>
          </el-popover>
          <el-popconfirm
            title="确认通过所选视图的填报数据？"
            @confirm="approvePass(scope.row)"
            placement="bottom-end"
          >
            <el-button
              slot="reference"
              size="mini"
              type="primary"
              style="margin-left: 10px"
              plain
              @click.stop=""
            >通过</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { staticDataList } from '@/api/dataset/dataset'
import { updateTempStaticData } from '@/api/link'

export default {
  name: 'StaticDataApprove',
  props: {
    panelInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      tableData: [],
      plxTable: 'plxTable'
    }
  },
  mounted() {
    this.initTable()
  },
  methods: {
    initTable() {
      const param = {
        status: 1
      }
      staticDataList(param).then(res => {
        this.tableData = []
        res.data.forEach(item => {
          const fields = JSON.parse(item.jsonDetails).fields
          const data = JSON.parse(item.newJsonDetails)
          this.tableData.push({
            tableId: item.tableId,
            viewId: item.viewId,
            viewName: item.viewName,
            updateTime: item.updateTime,
            fields: fields,
            data: data
          })
        })
      })
    },
    approvePass(row) {
      const param = {
        tableId: row.tableId,
        viewId: row.viewId,
        status: 2
      }
      updateTempStaticData(param).then(res => {
        if (res.success) {
          this.$message({
            message: '审批通过',
            type: 'success'
          })
        } else {
          this.$message({
            message: '审批异常',
            type: 'error'
          })
        }
        this.initTable()
      })
    },
    initTableData(row) {
      this.$refs[`${this.plxTable + row.viewId}`].reloadData(row.data)
    }
  }
}
</script>

<style scoped>

</style>

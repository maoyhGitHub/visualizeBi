
<template xmlns:el-col="http://www.w3.org/1999/html">

  <de-layout-content>
      <div v-loading="$store.getters.loadingMap[$store.getters.currentPath]" class="tabs-container flex-tabs">
        <div class="de-tabs-left">
          <el-row>
        <span class="header-title">
          指标库
          <el-button
            style="float: right; padding-right: 7px; margin-top: -8px"
            icon="el-icon-plus"
            type="text"
            @click="addIndicators('lob')"
          />
        </span>
          </el-row>
          <el-col class="custom-tree-container de-tree">
            <div class="block">
              <el-tree
                ref="panel_indicators_tree"
                :data="tData"
                node-key="id"
                highlight-current="true"
                :check-on-click-node="true"
                :auto-expand-parent="true"
                :default-expanded-keys="[selectKey]"
                @node-click="nodeClick"
              >
            <span
              slot-scope="{ node, data }"
              class="custom-tree-node-list father"
            >
              <span style="display: flex; flex: 1 1 0%; width: 0px">
                <span v-if="data.nodeType === 'indicatorsType'">
                  <svg-icon
                    icon-class="de-classify"
                  />
                </span>
                <span v-if="data.nodeType === 'lob'">
                  <svg-icon icon-class="scene"/>
                </span>
                <span
                  style="
                    margin-left: 6px;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                  "
                  :title="data.name"
                >{{ data.name }}</span>
              </span>
              <span
                class="child"
              >
                <span
                  v-if="data.nodeType === 'lob'"
                  @click.stop
                >
                  <el-dropdown
                    trigger="click"
                    size="small"
                    @command="(type) => addMore(type, data)"
                  >
                    <span class="el-dropdown-link">
                      <el-button
                        icon="el-icon-plus"
                        type="text"
                        size="small"
                      />
                    </span>
                    <el-dropdown-menu slot="dropdown">
<!--                      <el-dropdown-item
                        command="lob"
                      >
                        <svg-icon icon-class="scene"/>
                        <span style="margin-left: 5px">添加业务域</span>
                      </el-dropdown-item>-->
                      <el-dropdown-item
                        command="indicatorsType"
                      >
                        <svg-icon
                          icon-class="de-classify"

                        />
                        <span>添加指标分类</span>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </span>
                  <el-dropdown
                    trigger="click"
                    size="small"
                    @command="(type) => clickMore(type, data)"
                  >
                    <span class="el-dropdown-link">
                      <el-button
                        icon="el-icon-more"
                        type="text"
                        size="small"
                      />
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item
                        icon="el-icon-edit"
                        command="edit"
                      >
                        编辑
                      </el-dropdown-item>
                      <el-dropdown-item
                        icon="el-icon-delete"
                        command="del"
                      >
                        删除
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </span>
              </span>
              </el-tree>
            </div>
          </el-col>
        </div>
        <div class="de-tabs-right">
          <div>
          <el-row style="margin-bottom: 20px">
            <el-col :span="12">
              <el-button type="primary" icon="el-icon-add"
                         @click="addIndicators('indicators')">添加
              </el-button>
              <el-button  icon="el-icon-edit"
                         @click="edit">编辑
              </el-button>
              <el-button icon="el-icon-delete"
                         @click="del">删除
              </el-button>
            </el-col>
            <el-col :span="12">
              <span style="float: right">
                <el-input
                  v-model="indicatorsText"
                  :placeholder="'请输入指标名称'"
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
          <template>
            <el-table
              :data="tableData"
              style="width: 100%"
              tooltip-effect="dark"
              height="700px"
              @selection-change="handleSelectionChange"
            >
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                prop="name"
                label="指标名称"
                width="300">
              </el-table-column>
              <el-table-column
                prop="indicatorsNo"
                label="指标编号"
                width="300">
              </el-table-column>
              <el-table-column
                prop="indicatorsExplain"
                label="指标说明">
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.datasetTableId == null || scope.row.datasetTableId == '' ">
                    <el-dropdown
                      size="small"
                      placement="bottom-start"
                      @command="(type) => clickAddData(scope.row, type, data)"
                    >
                    <span class="el-dropdown-link" style="color: #076cf1">
                      关联数据集
                      <i @click.stop/>
                    </span>
                    <el-dropdown-menu
                      slot="dropdown"
                      class="de-dataset-dropdown de-card-dropdown"
                    >
                      <el-dropdown-item command="db">
                        <svg-icon
                          icon-class="ds-db"
                          class="ds-icon-db"
                        />
                        {{ $t('dataset.db_data') }}
                      </el-dropdown-item>
                      <el-dropdown-item command="sql">
                        <svg-icon
                          icon-class="ds-sql"
                          class="ds-icon-sql"
                        />
                        {{ $t('dataset.sql_data') }}
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="excel"
                        :disabled="!kettleRunning && engineMode !== 'simple'"
                      >
                        <svg-icon
                          icon-class="ds-excel"
                          class="ds-icon-excel"
                        />
                        {{ $t('dataset.excel_data') }}
                      </el-dropdown-item>
                      <el-dropdown-item command="union">
                        <svg-icon
                          icon-class="ds-union"
                          class="ds-icon-union"
                        />
                        {{ $t('dataset.union_data') }}
                      </el-dropdown-item>
                      <el-dropdown-item command="api">
                        <svg-icon
                          icon-class="ds-api"
                          class="ds-icon-api"
                        />
                        {{ $t('dataset.api_data') }}
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  </span>
                  <span style="color: #076cf1;margin-left: 15px" >
                      <a @click="viewDetail('detail',scope.row)">查看</a>
                  </span>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div>
      </div>

      <!--指标添加页面-->
      <el-dialog
        v-dialogDrag
        :title="this.addFlag == 'lob'?'业务域':this.addFlag == 'indicatorsType'?'分类':'指标'"
        :visible.sync="operationVisible"
        :show-close="true"
        width="600px"
      >
        <IndicatorsAdd
          v-if="operationVisible"
          :addFlag="addFlag"
          :operateType="operateType"
          :mainData="mainData"
          @close = "operationVisible = false"
          @refresh="getIndicatorsTree"
        />
      </el-dialog>
  </de-layout-content>
</template>

<script>
import DeLayoutContent from '@/components/business/DeLayoutContent'
import {deleteByIds, findIndicatorsTree, getTableList} from '@/api/panel/indicators'
import IndicatorsAdd from "./indicatorsAdd";
import { engineMode } from '@/api/system/engine'
export default {
  name: 'PanelMain',
  components: {IndicatorsAdd,DeLayoutContent },
  mixins: [],
  data() {
    return {
      tData:[],//指标树数据
      tableData:[],//列表数据
      mainData:{},
      checkedData:{},//选中节点数据
      operateType:'',
      operationVisible:false,
      addFlag:'',
      multipleSelection: [],//指标选中的指标数据
      selectKey:'',
      indicatorsText:'',
      engineMode: 'local'
    }
  },
  computed: {
  },
  created() {
    engineMode().then((res) => {
      this.engineMode = res.data
    })
  },
  mounted() {
    this.getIndicatorsTree()
  },
  methods: {
    //树点击事件
    nodeClick(data){
      this.mainData = data;
      this.checkedData = data;
      let pids ='';
      if(data.level == 1){//业务域
        if(data.children && data.children.length>0){
          pids =  data.children.map(item=>{return   item.id}).join(',');
        }else{//没有分类情况
          pids='-1';
        }
      }else if(data.level == 2){//分类
        pids = data.id;
      }
      getTableList({'pids':pids,'name':''}).then((res) => {
        this.tableData = res.data
      })
    },
    //勾选的数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //加号下拉菜单点击事件
    addMore(type,data){
      this.mainData = data;
      this.addIndicators(type);
    },
    clickMore(type,data){
      this.mainData = data;
      if('edit' == type){ // 编辑
        this.editIndicators(data);
      }else if('del' == type){ //删除
        this.delIndicators(data.nodeType,data);
      }
    },
    //打开编辑页面
    editIndicators(data){
      if(!data || data.length==0){
        this.$message({
          message: this.$t('请选择要编辑的数据！'),
          type: 'warning',
          showClose: true
        })
        return
      }else if(data.length > 1) {
        this.$message({
          message: this.$t('只能选择一条数据编辑！'),
          type: 'warning',
          showClose: true
        })
        return
      }
      this.mainData = data;
      this.operationVisible = true;
      this.addFlag = data.nodeType;
      this.operateType = 'edit';
    },
    //指标项编辑
    edit(){
      if(this.multipleSelection.length  != 1){
        this.$message({
          message: this.$t('请选择一条且选择一条要编辑的数据！'),
          type: 'warning',
          showClose: true
        })
        return
      }else{
        this.editIndicators(this.multipleSelection[0]);
      }
    },
    del(){
      if(this.multipleSelection.length  == 0){
        this.$message({
          message: this.$t('请选择要删除的数据！'),
          type: 'warning',
          showClose: true
        })
      }else{
        let ids = this.multipleSelection.map(item=>{return   item.id}).join(',');
        this.delIndicators('indicators',ids);
      }
    },
    //删除方法
    delIndicators(type,data){
        let ids = '';
        if('lob' == type){ //删除业务域情况
          if(data.children && data.children.length>0){
            this.$message({
              message: this.$t('此业务域存在指标分类不可删除!'),
              type: 'warning',
              showClose: true
            })
          }else{
            ids = data.id;
          }
        }else if('indicatorsType' == type){//删除分类情况
          if(this.tableData.length > 0){
            this.$message({
              message: this.$t('此分类存在指标不可删除!'),
              type: 'warning',
              showClose: true
            })
          }else {
            ids = data.id;
          }
        }else {//删除指标情况
         ids = data;
        }

      this.$confirm('确认删除选中的数据吗', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: "warning"
      }).then(() => {
        try {
          deleteByIds(ids).then((rsp) => {
            if (rsp.data === 0) {
              this.$message.error('删除失败');
            } else {
              this.$message.success('删除成功');
              this.getIndicatorsTree(); // 刷新页面
            }
          })
        } catch (e) {
          console.error('findAllResourceName error', e)
        }
      })
    },
    //打开添加指标表单
    addIndicators(type){
      if('indicators' == type && this.mainData.level != 2){
        this.$message({
          message: this.$t('请选择对应的分类添加指标！'),
          type: 'warning',
          showClose: true
        })
        return
      }
      this.operationVisible = true;
      this.addFlag = type;
      this.operateType = 'add';
    },
    //加载树
    getIndicatorsTree() {
          findIndicatorsTree().then((res) => {
            this.tData = res.data
            //默认选中第一行
            this.$nextTick(() => {
              if(this.checkedData.id){
                this.selectKey =this.checkedData.id
                this.nodeClick(this.checkedData);
              }else{
                this.selectKey = this.tData[0].id;
                this.nodeClick(this.tData[0]);
              }
              this.$refs.panel_indicators_tree.setCurrentKey(this.selectKey) // 第一次加载默认选中第一个节点
            })
          })
        },
    //模糊查询
    search(){
       let data =  this.mainData;
        let pids ='';
        if(data.level == 1){//业务域
          if(data.children && data.children.length>0){
            pids =  data.children.map(item=>{return   item.id}).join(',');
          }else{//没有分类情况
            pids='-1';
          }
        }else if(data.level == 2){//分类
          pids = data.id;
        }
        getTableList({'pids':pids,'name':this.indicatorsText}).then((res) => {
          this.tableData = res.data
        })
    },
    clickAddData(rowData, datasetType, param = {}) {
      this.currGroup = param
      this.$store.dispatch('dataset/setSceneData', this.currGroup.id)
      this.$router.push({
        path: '/dataset-form',
        query: {
          datasetType,
          sceneId: rowData.pid,
          dataSource: 'indicators',
          indicatorName: rowData.name,
          indicatorId: rowData.id
        }
      })
    },
    //查看数据集
    viewDetail(val,data){
      let typeName = '';
      let lobName ='';
      foo: for(let i=0;i<this.tData.length;i++){
        for(let j=0;j<this.tData[i].children.length;j++){
          if(data.pid == this.tData[i].children[j].id){
            typeName = this.tData[i].children[j].name;
            lobName = this.tData[i].name;
            break foo;
          }
        }
      }
      this.operateType = val;
      this.mainData = data;
      this.mainData.typeName = typeName;
      this.mainData.lobName = lobName;
      this.addFlag = 'indicators';
      this.operationVisible = true;

    }
  }
}
</script>

<style lang="scss" scoped>
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

}




.default-expansion {
  height: 40px;
  width: 232px;
  cursor: pointer;
  margin: 0;
  font-family: PingFang SC;
  font-size: 14px;
  font-weight: 400;
  color: var(--deTextSecondary, #646a73);
  padding-left: 22px;
  display: flex;
  align-items: center;

  i {
    margin-left: 5px;
  }
}

.main-area-input {
  ::v-deep.el-input-group__append {
    width: 70px;
    background: transparent;

    .el-input__inner {
      padding-left: 12px;
    }
  }
}

.header-title {
  font-size: 14px;
  flex: 1;
  color: var(--TextPrimary, #606266);
  font-weight: bold;
  display: block;
  height: 100%;
  /*line-height: 36px;*/
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.custom-tree-node-list {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding: 0 8px;
}

.dialog-css ::v-deep .el-dialog__body {
  padding: 15px 20px;
}

.dialog-css ::v-deep .el-dialog__body {
  padding: 10px 20px 20px;
}

.father .child {
  /*display: none;*/
  visibility: hidden;
}

.father:hover .child {
  /*display: inline;*/
  visibility: visible;
}
</style>



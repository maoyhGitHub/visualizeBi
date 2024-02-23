<template>
  <el-form ref="form" :rules="rules" :model="form" :disabled="operateType== 'detail' " label-width="100px">
    <el-input style="display: none" v-model="form.id" />
    <el-input style="display: none" v-model="form.pid" />
    <el-input style="display: none" v-model="form.nodeType" />
    <el-input style="display: none" v-model="form.level" />
    <el-form-item v-if="operateType== 'detail' " label="业务域名称" >
      <el-input v-model="form.lobName" placeholder="请输入名称"/>
    </el-form-item>
    <el-form-item v-if="operateType== 'detail' " label="分类名称" >
      <el-input v-model="form.typeName" placeholder="请输入名称"/>
    </el-form-item>
    <el-form-item :label="addFlag == 'lob'?'业务域名称':addFlag == 'indicatorsType'?'分类名称':'指标名称' " prop="name" >
      <el-input v-model="form.name" placeholder="请输入名称"/>
    </el-form-item>
    <el-form-item v-if="addFlag === 'indicators'" label="指标编号" prop="indicatorsNo" >
      <el-input v-model="form.indicatorsNo" placeholder="指标编号" />
    </el-form-item>
    <el-form-item v-if="addFlag != 'indicators'" label="排序" prop="sort">
      <el-input-number :min="0" :precision="0" placeholder="请输入排序" v-model="form.sort"/>
    </el-form-item>
    <el-form-item v-if="addFlag === 'indicators'" label="指标说明">
      <el-input type="textarea" rows="3" v-model="form.indicatorsExplain"/>
    </el-form-item>
    <el-form-item v-if="operateType !='detail' " style="margin-left: 300px">
      <el-button @click="cancel">取消</el-button>
      <el-button type="primary" @click="onSubmit('form')">确定</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { panelSave, panelUpdate } from '@/api/panel/panel'
import { showTemplateList } from '@/api/system/template'
import { deepCopy, imgUrlTrans } from '@/components/canvas/utils/utils'
import {save, checkName} from '@/api/panel/indicators'

export default {
  components: {},
  props: {
    addFlag: {//添加类型
      type: String,
      default: ''
    },
    operateType: {//操作类型
      type: String,
      default: ''
    },
    mainData: {// 选中数据
      type: Object,
    }

  },
  data() {
    var checkUnique = (rule, value, callback) => {
      if(!value){
        callback();
      }
      let checkData = {};
      let pid ='';
      if(this.operateType == 'add'){
        if('indicators' == this.addFlag || 'indicatorsType' == this.addFlag){
          pid = this.mainData.id
        }else{
          if(this.mainData.pid){
            pid =  this.mainData.pid
          }else{
            pid = '-1'
          }
        }
        checkData =  {id:'',name:value,pid:pid};
      }else if('edit' == this.operateType){
        checkData =  {id:this.mainData.id,name:value,pid:this.mainData.pid};
      }
      // 发送请求
      checkName (checkData).then((res) => {
        if (res.success) {
          if(res.data == 0){
            callback();
          }else {
            callback("已存在相同名称");
          }
        }
      })
    };
    return {
      form: {
        id:'',
        pid:'',
        nodeType:'',
        level:1,
        name: '',
        sort: '',
        indicatorsNo: '',
        indicatorsExplain: '',
        datasetTableId: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { validator: checkUnique, message: '已存在相同名称', trigger: 'blur' }
        ],
        indicatorsNo: [
          { required: true, message: '请输入指标编号', trigger: 'blur' }
        ],
        sort: [
          { type: 'date', required: true, message: '请输入排序', trigger: 'blur' }
        ]
      },
      loading: false,
      inputType: 'new',
      fieldName: 'name',
      tableRadio: null,
      keyWordSearch: '',
      columnLabel: this.$t('panel.belong_to_category'),
      templateList: [],
      importTemplateInfo: {
        snapshot: ''
      },
      editPanel: null,
      templateSelected: false
    }
  },
  computed: {

  },
  watch: {
  },
  created() {
  },
  mounted() {
    if('edit' == this.operateType || 'detail' == this.operateType){//编辑页面回填数据
      Object.assign(this.form,this.mainData);
    }
  },
  destroyed() {

  },
  methods: {
    //取消按钮
    cancel(){
      this.$emit('close');
    },
    //确定按钮
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => { //表单校验
        if (valid) {//校验通过
          var formData = this.form;
          if('add' == this.operateType){
            if(this.addFlag == 'lob'){
              formData.level = 1;
            }else if(this.addFlag ==  'indicatorsType'){
              formData.level = 2;
              formData.pid = this.mainData.id;
            }else{
              formData.level = 3;
              formData.pid = this.mainData.id;
            }
            formData.nodeType = this.addFlag; //lob 业务域 indicatorsType 分类 indicators 指标
          }
          save(formData).then((response) => {
            if(response.success){
              this.$message({
                message: this.$t('commons.save_success'),
                type: 'success',
                showClose: true
              })
            }else{
              this.$message({
                message: this.$t('commons.save_failed'),
                type: 'error',
                showClose: true
              })
            }
            this.$emit('refresh');//刷新主表数
            this.$emit('close'); //关闭页面
          })
        } else {
          return ;
        }
      });
    },

  }
}
</script>



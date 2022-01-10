<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-button style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
      <el-button type="primary" style="margin:20px;" :disabled="selectData.length ==0?true:false" @click="exportExcelSelect">导出Excel</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%" @selection-change="handleSelectionChange">
      <el-table-column type="selection"/>
      <el-table-column prop="process" label="状态" :filters="[
        { text: '内审', value: '内审' },
        { text: '答辩', value: '答辩' },
      ]"
                       :filter-method="filterHandler"/>
      <el-table-column prop="id" v-if=false label="序号" />
      <el-table-column prop="committeeid" label="上会场次" />
      <el-table-column prop="status" v-if=false label="status" />
      <el-table-column prop="thesisid" label="论文编号" />
      <el-table-column prop="title" label="论文题目" />
      <el-table-column prop="department" label="所属系"  />
      <el-table-column prop="comment" label="评审意见"  />
      <el-table-column prop="voters" label="投票人数" />
      <el-table-column prop="noes" label="未通过票数"  />
      <el-table-column prop="yesses" label="通过票数"  />
      <el-table-column prop="results" label="上会结果"  />
      <el-table-column fixed="right" label="确认结果" width="100">
        <template v-slot="scope">
          <el-button type="primary" @click="changestatus(scope.row)" :disabled="scope.row.status == 5 || scope.row.status == 6 || scope.row.status == 9 || scope.row.status == 11 ">更新状态</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <el-dialog title="上会结果保存预览" width="70%" v-model="dialogVisible">
      <el-table :data="selectData" id="selectTable" height="380px">
        <el-table-column prop="process" label="状态" />
        <el-table-column prop="id" v-if=false label="序号" />
        <el-table-column prop="committeeid" label="上会场次" />
        <el-table-column prop="thesisid" label="论文编号" />
        <el-table-column prop="title" label="论文题目" />
        <el-table-column prop="department" label="所属系"  />
        <el-table-column prop="results" label="上会结果"  />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="exportExcel">确定保存</el-button>
      </div>
    </el-dialog>

    <el-dialog
        v-model="batchDialogVisible"
        title="选择论文批次"
        width="30%">
      <div style="margin:10px 0 ">
        选择学年：<el-select v-model="academicYear" style="margin-left: 20px;width: 160px" placeholder="2020-2021">
        <el-option
            v-for="item in distinctYear"
            :key="item.academicYear"
            :label="item.academicYear"
            :value="item.academicYear"
        >
        </el-option>
      </el-select>
      </div>
      <div style="margin:10px 0 ">
        选择学期：
        <el-radio v-model="semester" label="第一学期">第一学期</el-radio>
        <el-radio v-model="semester" label="第二学期">第二学期</el-radio>
      </div>
      <div style="margin:10px 0 ">
        选择论文类型：
        <el-radio v-model="type" label="学硕">学硕</el-radio>
        <el-radio v-model="type" label="专硕">专硕</el-radio>
      </div>
      <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="load">确定</el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../utils/request";
import htmlToExcel from '../utils/htmlToExcel'
import { ref, defineComponent } from 'vue'
export default {
  name: "CommitteeVote",
  data() {
    return {
      batchDialogVisible:false,
      sign:0,  //指示是否选过batch
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      selectData:[],
      dialogVisible:false,
      form:{},
      centerDialogVisible: ref(false),
      // search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData:[],
      rowItem:{},
      inforForm:{
        userid:'',
        role:'',
        info:'',
        send:'',
      },
    }
  },
  created() {
    this.batchDialogVisible=true
  },
  mounted() {
    this.getYear();
  },
  methods: {
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()

    },
    // watch:{
    //   checkedValue:function(){
    //     this.answer=[];
    //     this.answer.push(this.checkedValue);
    //   }
    // },
    getYear(){
      console.log("getYear")
      request.put("/batch/distinctYear").then(res=>{
        console.log("year",res)
        this.distinctYear=res.data
        this.total=res.data.total
      })
    },
    load() {
      new Promise((resolve,reject)=>{
        request.get("/batch/getbatchID",{
          params:{
            year:this.academicYear,
            semester:this.semester,
            type:this.type
          }
        }).then(res=>{
          console.log(res)
          resolve(res.data)
        })
      }).then(res1=>{
        if (res1==null){
          this.$message({
            type: "error",
            message: "不存在该批次"
          })
        }else{
          request.get("/committeevote",{
            params: {
              batch:res1
            }
          }).then(res=>{
            console.log(res)
            this.tableData=res.data
            this.total=res.data.total
          })
          this.sign=1
          this.batchDialogVisible=false
        }
      })
    },
    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    exportExcel() {
      htmlToExcel.getExcel('#selectTable','上会结果')
    },
    exportExcelSelect(){
      console.log("this.selectData",this.selectData)
      if (this.selectData.length < 1){
        this.$message.error('请选择要导出的内容！');
        return false;
      }
      for(let i=0;i<this.selectData.length;i++){
        if(this.selectData[i].status!=5 && this.selectData[i].status!=6 && this.selectData[i].status!=9 && this.selectData[i].status!=11 )
        {this.$message.error('导出前请全部确认！');
          return false;}
      }
      this.dialogVisible = true;
    },
    handleSelectionChange(val) {
      console.log("val",val)
      this.selectData = val;
    },
    getRow(row)
    {
      //console.log(row);
      this.rowItem=row;
      this.centerDialogVisible = true;
    },
    changestatus(row){
      this.form = JSON.parse(JSON.stringify(row));
      if(this.form.process=="内审"){
        if(this.form.results=="通过")
        {this.form.status="5";
          request.get("/roleallocation/staff").then(res=>{
            for(var i = 0;i < res.data.length;i++) {
            this.inforForm.userid=res.data[i]
            this.inforForm.role="工作人员"
            this.inforForm.info="有一篇文章已经完成会评，请及时查看！"
            this.inforForm.send="学位评定委员会"
              request.post("/information",this.inforForm).then(res=>{
              if (res.code ==='0' && i==res.data.length-1) {
                this.$message({
                  type: "success",
                  message: "已经通知所有工作人员"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }})
          }})
        }
        else
        {this.form.status="6";
          request.get("/roleallocation/staff").then(res=>{
            for(var i = 0;i < res.data.length;i++) {
              this.inforForm.userid=res.data[i]
              this.inforForm.role="工作人员"
              this.inforForm.info="有一篇文章已经完成会评，请及时查看！"
              this.inforForm.send="学位评定委员会"
              request.post("/information",this.inforForm).then(res=>{
                if (res.code ==='0' && i==res.data.length-1) {
                  this.$message({
                    type: "success",
                    message: "已经通知所有工作人员"
                  })
                } else {
                  this.$message({
                    type: "error",
                    message: res.msg
                  })
                }})
            }})
        }}
      else{if(this.form.results=="通过")
      {this.form.status="9";
        request.get("/roleallocation/staff").then(res=>{
          for(var i = 0;i < res.data.length;i++) {
            this.inforForm.userid=res.data[i]
            this.inforForm.role="工作人员"
            this.inforForm.info="有一篇文章已经完成答辩会评，请及时查看！"
            this.inforForm.send="学位评定委员会"
            request.post("/information",this.inforForm).then(res=>{
              if (res.code ==='0' && i==res.data.length-1) {
                this.$message({
                  type: "success",
                  message: "已经通知所有工作人员"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }})
          }})
      }
      else
      {this.form.status="12";
        request.get("/roleallocation/staff").then(res=>{
          for(var i = 0;i < res.data.length;i++) {
            this.inforForm.userid=res.data[i]
            this.inforForm.role="工作人员"
            this.inforForm.info="有一篇文章已经完成答辩会评，请及时查看！"
            this.inforForm.send="学位评定委员会"
            request.post("/information",this.inforForm).then(res=>{
              if (res.code ==='0' && i==res.data.length-1) {
                this.$message({
                  type: "success",
                  message: "已经通知所有工作人员"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }})
          }})
      }}
      //this.form.status=this.radio1
      console.log("this.form.status",this.form.status)
      request.put("/thesis", this.form).then(res2 => {});
      this.load();
      this.load();
      this.centerDialogVisible = false;
      this.load();
      this.load();
    },
  },
}

</script>

<style scoped>

</style>
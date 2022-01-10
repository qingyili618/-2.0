<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-button @click="autoAllocate" :disabled="multipleSelection.length ==0?true:false">自动分配已选论文</el-button>
      <el-button @click="toggleSelection()" :disabled="multipleSelection.length ==0?true:false">清除选择</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <el-table :data="tableData"
              ref="multipleTable"
              :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%"
              @selection-change="handleSelectionChange"
              @select="select"
              @select-all="selectAll">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="thesisid" label="ID" sortable align="center" />
      <el-table-column prop="title" label="题目" align="center"  />
      <el-table-column prop="department" label="所在系" align="center"/>
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" align="center" />
      <el-table-column prop="memo" label="说明" align="center"/>
      <el-table-column prop="url" label="论文编号" align="center"/>
      <el-table-column prop="departmentallocation" label="系统自动分配系" align="center"/>
      <el-table-column fixed="right" label="操作" width="160" align="center">
        <template #default="scope" >
          <el-button size="mini"  @click="previewPDF(scope.row)">修改答辩分配系</el-button>
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

      <el-dialog
          v-model="dialogVisible"
          title="选择答辩分配系"
          width="1250px"
          height="1000px">
        <div id="container" style="width:100%;height:100%;overflow:hidden;">

          <div  >
            <iframe :src="pdfUrl" frameborder="1" style="width:700px;height:1000px;float:left;"></iframe>
          </div>
          <div id="scoretable" style="width:500px;padding-left:10px;float:left;">
            <!-- canvas渲染方式 -->
            <el-form :model="form" >
              <el-form-item label="论文题目">
                <el-input v-model="form.title" disabled></el-input>
              </el-form-item>
              <el-form-item label="论文编号">
                <el-input v-model="thesisid" disabled></el-input>
              </el-form-item>
              <el-form-item label="选择答辩所分配系">
                <el-table ref="singleTable"
                          :data="DepartmentData"
                          highlight-current-row
                          style="width: 100%"
                          @current-change="handleCurrentChange1">
                  <el-table-column prop="department" label="系" align="center" />
                  <el-table-column prop="name" label="系主任" align="center"  />
                </el-table>
              </el-form-item>
            </el-form>
            <div>
              <span >
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-popconfirm
                    confirm-button-text="是"
                    cancel-button-text="否"
                    :icon="InfoFilled"
                    icon-color="red"
                    title="确认更改论文答辩分配系？"
                    @confirm="save"
                    @cancel="cancelEvent">
                <template #reference>
                  <el-button>确定</el-button>
                </template>
                </el-popconfirm>
              </span>
            </div>
          </div>
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
  </div>
</template>

<script>

import request from "../utils/request";
import pdf from "@/components/pdf/pdf";
import {ref} from 'vue';
export default {
  name: 'Home',
  components: {
    pdf
  },
  data() {
    return {
      batchDialogVisible:false,
      sign:0,  //指示是否选过batch
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      form:{
      },
      form1:{
      },
      evalTeacher: {
        thesisid: null,
        reviewer: '',
      },
      dtableData:[],
      DepartmentData:[],
      thesis:{},
      currentRow: null,
      message: '',
      textarea: '',
      dialogVisible: false,
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData:[],
      scr: null,
      pdfPage: 1,
      pageCount: 0,
      pdfUrl: "",
      evaluation: null,
      thesisid: null,
      qualified: ref(1),
      searchTeacher:'',
      currentPageTeacher: 1,
      pageSizeTeacher: 10,
      multipleSelection: [],
      formsubmit:{},
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
  methods:{
    submitthesis(){
      for(var i = 0;i < this.multipleSelection.length;i++){
        this.formsubmit = this.multipleSelection[i]
        this.formsubmit.status = 7
        request.put("/thesis", this.formsubmit)
      }
      this.load()
    },


    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection)
    },
    handleCurrentChange1(val) {
      this.currentRow = val
    },
    save(row){
      console.log(this.currentRow.department)
      this.form.status = 7;//状态改为已分配评审
      this.form.departmentallocation=this.currentRow.department
      request.put("/thesis", this.form).then(res => {
        console.log(res)

        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })

      this.dialogVisible = false
      this.load()
    },
    //PDF论文预览
    previewPDF(row){
      this.thesisid=row.url;
      this.dialogVisible = true;
      this.form=JSON.parse(JSON.stringify(row));
      // this.pdfUrl="/"+row.url+".pdf";
      this.pdfUrl="/pdf/"+row.thesisid+"_"+row.title+".pdf";
      request.post("/teacher/departmentHead").then(res=>{
        this.DepartmentData=res.data
        this.$refs.singleTable.setCurrentRow(res.data[0])
      })
      // this.loadDepartment();
    },


    //关闭窗口初始化PDF页码
    closeDialog(){
      this.pdfPage = 1;
    },

    //PDF改变页数
    previousPage(){
      let p = this.pdfPage
      p = p>1?p-1:this.pageCount
      this.pdfPage = p
    },
    nextPage(){
      let p = this.pdfPage
      p = p<this.pageCount?p+1:1
      this.pdfPage = p
    },
    getYear(){
      request.put("/batch/distinctYear").then(res=>{
        console.log(res)
        this.distinctYear=res.data
        this.total=res.data.total
      })
    },
    load(){
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
          request.get("/thesis",{
            params: {
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              status:"5",
              batch:res1
            }
          }).then(res=>{
            console.log(res)
            this.tableData=res.data.records
            for(var i = 0;i < res.data.records.length;i++){
              this.tableData[i].departmentallocation=this.tableData[i].department
            }
            console.log(this.tableData)
            this.total=res.data.total
          })
          this.batchDialogVisible=false
          this.sign=1
        }

      })
    },

    handleSizeChange(pageSize){
      this.pageSize=pageSize
      if (this.sign==1){
        this.load()
      }

    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      if (this.sign==1){
        this.load()
      }
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    autoAllocate(){
      console.log(this.multipleSelection)
      for(var i = 0;i < this.multipleSelection.length;i++){
        this.form=this.multipleSelection[i]
        this.form.status = 7;//状态改为已分配评审
        console.log(this.form)
        request.put("/thesis", this.form)
      }
      this.load()
    }
  },

}
</script>

<style lang="scss" scoped>
.filePreview{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
  background: rgba($color: #000000, $alpha: 0.4);
  min-height: 500px;
  overflow: auto;
  .filePreview_center{
    width: 50%;
    height: 100%;
    left: 25%;
    background: white;
    border-top-right-radius: 3px;
    border-top-left-radius: 3px;
    position: relative;
    transform: translateY(-50% -50%);
    padding: 16px;
    .center_header{
      padding-bottom: 10px;
      border-bottom: 1px solid #dee2ed;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-radius: 3px;
      .header_left{
        font-weight: bold;
      }
      .header_right{
        cursor: pointer;
        color: #99a1ad;
      }
    }
    .center_center{
      border-radius: 3px;
      width: 100%;
      height: 100%;
    }
  }
}
.el-pagination {
  text-align: center;
}
</style>
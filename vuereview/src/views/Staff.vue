<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%">
      <el-table-column prop="thesisid" label="论文编号" sortable align="center" width="50px"/>
      <el-table-column prop="title" label="题目"  width="150px"/>
      <el-table-column prop="author" label="学生姓名" align="center" />
      <el-table-column prop="supervisor" label="导师姓名" align="center" />
      <el-table-column prop="department" label="所在系" align="center" />
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" align="center" />
      <el-table-column prop="reviewernum" label="已分配专家数" align="center"/>
      <el-table-column prop="memo" label="说明" align="center" />
<!--      <el-table-column prop="url" label="论文编号" />-->

      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template #default="scope">
          <el-button size="mini"  @click="previewPDF(scope.row)">分配</el-button>
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
          title="论文预览PDF"
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
                <el-input v-model="form.thesisid" disabled></el-input>
              </el-form-item>

              <div style="margin: 10px 0">
                <el-input v-model="searchTeacher" placeholder="请输入老师姓名" style="width: 80%" clearable></el-input>
                <el-button type="primary" style="margin-left: 5px" @click="loadTeacher">查询</el-button>

              </div>

              <el-table :data="dtableData"
                        ref="multipleTable"
                        border
                        style="width: 100%"
                        @selection-change="handleSelectionChange"
                        @select="select"
                        @select-all="selectAll">

                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <el-table-column prop="name" label="专家姓名"  />
                <el-table-column prop="discipline" label="学科"  />
                <el-table-column prop="direction" label="研究方向"  />
                <el-table-column prop="department" label="系"  />
                <el-table-column prop="field" label="涉及领域"  />
<!--                <el-table-column prop="grade" label="评分"  >-->
<!--                  &lt;!&ndash; vue2.6后自定义列放弃了slot-scope插槽(网上相关的方法会导致控件显示不了），改用v-slot，这里用了简化版，让没有input和每行数据绑定 &ndash;&gt;-->
<!--                  &lt;!&ndash; 搜素： vue3使用element-plus的自定义列表格&ndash;&gt;-->
<!--                  <template #default="scope">-->
<!--                    <el-input v-model="scope.row.grade"></el-input>-->
<!--                  </template>-->
<!--                </el-table-column>-->
              </el-table>
              <div style="margin: 10px 0">
                <el-pagination
                    v-model:currentPage="currentPageTeacher"
                    :page-sizes="[5, 10]"
                    :page-size="pageSizeTeacher"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange">
                </el-pagination>
              </div>

            </el-form>

          </div>

        </div>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
          <el-popconfirm
              confirm-button-text="是"
              cancel-button-text="否"
              icon="el-icon-info"
              icon-color="red"
              @confirm="saveTeacher"
              @cancel="cancelEvent"
              title="确认提交?"
          >
            <template #reference>
              <el-button size="mini">确定</el-button>
            </template>
          </el-popconfirm>
          </span>
        </template>
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
// @ is an alias to /src


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
      reviewerlist:{},
      presentsupervisor:"",
      backreviewer:"",

      batchDialogVisible:false,
      changeBtn:true,
      visible:false,
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      //teacher
      evalTeacher: {//write into grade table
        thesisid: null,
        reviewer: '',
      },
      dtableData:[],
      searchTeacher:'',
      currentPageTeacher: 1,
      pageSizeTeacher: 10,
      multipleSelection: [],//teacher multiple choice
      //thesis
      form:{
      },
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
      inforForm:{
        userid:'',
        role:'',
        info:'',
        send:'',
      },
      sign:0,  //指示是否选过batch
    }
  },

  created() {
    this.batchDialogVisible=true
  },
  mounted() {
    this.getYear();
  },
  methods:{
    changebutton(){
      this.visible=false
      this.changeBtn=false
    },
    loadTeacher(){
      request.get("/teacher",{
        params: {
          pageNum: this.currentPageTeacher,
          pageSize: this.pageSizeTeacher,
          search: this.searchTeacher
        }
      }).then(res=>{
        console.log(res)
        this.dtableData=res.data.records
        this.total=res.data.total
      })
    },
    select(selection, row) {
      console.log("selection.length:",selection.length,"this.form.reviewernum",this.form.reviewernum)
      if (selection.length > 3-this.form.reviewernum) {
        let del_row = selection.shift()
        this.$refs.multipleTable.toggleRowSelection(del_row, false)
        this.$message({
          type: "error",
          message: "已选择超过三位评审专家！"
        })
      }
    },
    selectAll(selection){
      console.log("selection.length:",selection.length,"this.form.reviewernum",this.form.reviewernum)
      if (selection.length > 3-this.form.reviewernum) {
        let del_row = selection.shift()
        this.$refs.multipleTable.toggleRowSelection(del_row, false)
        this.$message({
          type: "error",
          message: "已选择超过三位评审专家！"
        })
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log("multipleSelection:",this.multipleSelection)
    },
    saveTeacher(){
      for(var i = 0;i < this.multipleSelection.length;i++) {
        for(var j = 0;j < this.reviewerlist.length;j++){
          if(this.multipleSelection[i].employeeId==this.reviewerlist[j].reviewer){
            this.$message.error('请勿选择当前论文已选专家:'+this.multipleSelection[i].name);
            return false;
          }
        }
      }
      for(var i = 0;i < this.multipleSelection.length;i++) {
        if(this.multipleSelection[i].employeeId==this.presentsupervisor){
          this.$message.error('请勿选择当前论文导师:'+this.multipleSelection[i].name);
          return false;
        }
      }
      for(var i = 0;i < this.multipleSelection.length;i++) {
        for(var j = 0;j < this.backreviewer.length;j++){
          if(this.multipleSelection[i].employeeId==this.backreviewer[j].reviewer){
            this.$message.error('请勿选择已退回当前论文专家:'+this.multipleSelection[i].name);
            return false;
          }
        }
      }
      //修改状态
      if(this.multipleSelection.length+this.form.reviewernum==3)
      {this.form.status = 2};//状态改为已分配评审
      this.form.reviewernum=this.multipleSelection.length+this.form.reviewernum
        new Promise((resolve, reject)=>{
          for(var i = 0;i < this.multipleSelection.length;i++) {
            this.evalTeacher.reviewer = this.multipleSelection[i].employeeId;
            this.evalTeacher.thesisid = this.thesisid;
            request.post("/grade/insert", this.evalTeacher).then(res => {
            console.log("this.evalTeacher",this.evalTeacher)
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "分配成功"
              })
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
            resolve();
            })
            }
        }).then(() => {
          for(var i = 0;i < this.multipleSelection.length;i++) {
            this.inforForm.userid= this.multipleSelection[i].employeeId;
            console.log("this.inforForm.userid",this.inforForm.userid)
            this.inforForm.role="评审专家"
            this.inforForm.info="您有一篇论文待评审，请及时查看！"
            this.inforForm.send="工作人员"
            request.post("/information",this.inforForm).then(res=>{
            console.log("/information")
            console.log("this.inforForm.userid",this.inforForm.userid)
              if (res.code ==='0') {
                this.$message({
                  type: "success",
                  message: "论文分配已通知评审专家"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            }).then(()=> {
              this.load()
              this.savethesis()
            }
            )}
        })
    },
    savethesis(){
      request.put("/thesis", this.form).then(res => {});
      this.dialogVisible=false;
      this.load();
    },
    //PDF论文预览
    previewPDF(row){
      console.log("row:",row)
      this.presentsupervisor=row.supervisor;
      console.log("this.presentsupervisor",this.presentsupervisor)
      this.thesisid=row.thesisid;
      this.dialogVisible = true;
      this.form=JSON.parse(JSON.stringify(row));
      this.pdfUrl="/pdf/"+row.thesisid+"_"+row.title+".pdf";
      // this.pdfUrl="/"+row.url+".pdf";
      // this.pdfUrl="/3.pdf";
      this.loadTeacher();
      request.get("/grade/getreviewer",{
        params: {
          thesisid:row.thesisid,
        }
      }).then(res => {
        console.log("/reviewers",res)
        this.reviewerlist=res.data
        console.log("this.reviewerlist",this.reviewerlist)
      })
      request.get("/teacher/getemployeeid",{
        params: {
          name:row.supervisor,
        }
      }).then(res => {
        console.log("/teacher/getemployeeid",res)
        this.presentsupervisor=res.data[0].employeeId
        console.log("this.presentsupervisor",this.presentsupervisor)
      })
      request.get("/back/getbackreviewer",{
        params: {
          thesisid:row.thesisid,
        }
      }).then(res => {
        console.log("/back/getbackreviewer",res)
        this.backreviewer=res.data
        console.log("this.backreviewer",this.backreviewer)
      })
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
          resolve(res.data);
        })
      }).then(res1=> {
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
              batch: res1,
              status:'1'
            }
          }).then(res=>{
            console.log("res",res)
            this.tableData=res.data.records
            this.total=res.data.total
          })
          this.sign=1
          this.batchDialogVisible=false
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


  }
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
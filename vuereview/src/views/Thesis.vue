<template>
  <div style="padding:10px">
    <!-- 功能区域 -->
    <div style="margin: 10px 0">
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <div style="margin: 5px;
    margin-left: 10px;
    font-size: 14px;
    color: rgb(102, 102, 102);">
      待评审
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 100%">
      <el-table-column prop="thesisid" label="论文编号" width="100px" sortable />
      <el-table-column prop="title" label="题目" width="250px" />
      <!--      <el-table-column prop="author" label="学生姓名"  />-->
      <!--      <el-table-column prop="supervisor" label="导师姓名"  />-->
      <el-table-column prop="department" label="所在系" width="100px" />
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" width="100px" />
      <el-table-column prop="reviewernum" v-if=false label="已分配专家数" />
      <el-table-column prop="memo" label="说明" />
      <!--      <el-table-column prop="url" label="论文编号" />-->

      <el-table-column fixed="right" label="操作" width="160">
        <template #default="scope">
          <!--:disabled="this.isfinish==1"-->
          <el-button size="mini"   @click="previewPDF(scope.row)" >评审</el-button>
          <el-popconfirm title="确定不评审吗?" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button size="mini" type="danger"  @click="back(scope.row)">退回</el-button>
            </template>
          </el-popconfirm>
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
                <el-input v-model="thesisid" disabled></el-input>
              </el-form-item>
              <!--<el-row>
                <el-col :span="6"><div class="grid-content bg-purple">评价指标</div></el-col>
                <el-col :span="6"><div class="grid-content bg-purple-light">分值</div></el-col>
                <el-col :span="6"><div class="grid-content bg-purple"></div>评价内容</el-col>
                <el-col :span="6"><div class="grid-content bg-purple-light">评分</div></el-col>
              </el-row>
              -->
              <el-table :data="dtableData" border>
                <el-table-column prop="index" label="评价指标" width="100px" />
                <el-table-column prop="score" label="分值" width="80px" />
                <el-table-column prop="factor" label="评价要素" width="200px" />
                <el-table-column prop="grade" label="评分" width="100px" >
                  <!-- vue2.6后自定义列放弃了slot-scope插槽(网上相关的方法会导致控件显示不了），改用v-slot，这里用了简化版，让没有input和每行数据绑定 -->
                  <!-- 搜素： vue3使用element-plus的自定义列表格-->
                  <template #default="scope">
                    <el-input v-model="scope.row.grade"></el-input>
                  </template>
                </el-table-column>
              </el-table>

              <el-form-item label="综合评价" style="padding-top:10px">
                <el-radio-group v-model="qualified" label="综合评价"  >
                  <el-radio :label="1">合格</el-radio>
                  <el-radio :label="2">不合格</el-radio>
                </el-radio-group>
              </el-form-item>


              <el-form-item label="修改意见:">
                <el-input
                    v-model="textarea"
                    :rows="20"
                    type="textarea"
                    placeholder="修改意见"
                />
              </el-form-item>
            </el-form>
          </div>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-popconfirm
                confirm-button-text="是"
                cancel-button-text="否"
                :icon="InfoFilled"
                icon-color="red"
                title="确认修改状态？"
                @confirm="save"
                @cancel="cancelEvent">
                <template #reference>
                  <el-button>确定</el-button>
                </template>
            </el-popconfirm>
          </span>
        </template>
      </el-dialog>

    </div>
    <div style="margin: 5px;
    margin-left: 10px;
    font-size: 14px;
    color: rgb(102, 102, 102);">
      已评审
    </div>

    <el-table :data="tableData2" :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 100%">
      <el-table-column prop="thesisid" label="论文编号" width="100px" sortable />
      <el-table-column prop="title" label="题目" width="250px" />
      <!--      <el-table-column prop="author" label="学生姓名"  />-->
      <!--      <el-table-column prop="supervisor" label="导师姓名"  />-->
      <el-table-column prop="department" label="所在系" width="100px" />
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" width="100px"/>
      <el-table-column prop="reviewernum" v-if=false label="已分配专家数" />
      <el-table-column prop="memo" label="说明" />
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage2"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize2"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total2"
          @size-change="handleSizeChange2"
          @current-change="handleCurrentChange2">
      </el-pagination>

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
      backform:{},
      batchDialogVisible:false,
      reviewednum:0,
      isfinish:0,
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      gradeid:"",
      form:{
      },
      eval: {
        thesisid: null,
        reviewer: '',
        grade1: null,
        grade2:null,
        grade3: null,
        grade4: null,
        grade5: null,
        total: null,
        qualified: null,
        comments: '',
        submitTime: null,
      },

      dtableData:[
        {
          index: '选题与综述',
          score: 20,
          factor: '选题来源于企业管理实践，符合专业学位要求。文献系统，评述客观',
          grade: null,

        },
        {
          index: '理论知识',
          score: 30,
          factor: '基础理论坚实',
          grade: null,

        },
        {
          index: '方法与能力',
          score: 15,
          factor: '科学合理的研究方法',
          grade: null,

        },
        {
          index: '应用价值',
          score: 15,
          factor: '具有实践应用价值',
          grade: null,

        },
        {
          index: '论文写作',
          score: 20,
          factor: '逻辑严谨，行文规范',
          grade: null,

        },
      ],
      message: '',
      textarea: '',
      dialogVisible: false,
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      currentPage2: 1,
      pageSize2: 10,
      total2: 0,
      tableData:[],
      tableData2:[],
      scr: null,
      pdfPage: 1,
      pageCount: 0,
      pdfUrl: "",
      evaluation: null,
      thesisid: null,
      qualified: ref(1),
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
    //PDF论文预览
    previewPDF(row){
      this.form.id=row.id
      this.isfinish=0
      this.thesisid=row.thesisid;
      this.dialogVisible = true;
      this.form=JSON.parse(JSON.stringify(row));
      // D:\qq文件\合并2.0\vuereview\src\assets\pdf\0-商学院硕士研究生学位论文评审表（MBA）.doc
      this.pdfUrl="/pdf/"+row.thesisid+"_"+row.title+".pdf";
      // this.pdfUrl="3.pdf";
      console.log("this.pdfUrl",this.pdfUrl)
      let userJson=sessionStorage.getItem("user");
      let user=JSON.parse(userJson)
      request.get("/grade/returnID/", {
        params: {
          thesisid:row.thesisid,
          reviewer:user.username,
          //option: this.options,
        }
      }).then(res => {
        this.gradeid=res.data
        this.eval.id=this.gradeid
        // console.log("this.gradeid",this.gradeid)
      })
      console.log("row.thesisid",row.thesisid)
      request.get("/thesis/getreviewednum",{
        params: {
          thesisid:row.thesisid,
        }
      }).then(res => {
        console.log("res",res)
        this.reviewednum=res.data[0].reviewednum
        console.log("this.reviewednum",this.reviewednum)
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
          console.log("/getbatchID",res)
          resolve(res.data)
        })
      }).then(res1=>{
        if (res1==null){
          this.$message({
            type: "error",
            message: "不存在该批次"
          })
        }else{
          let userJson=sessionStorage.getItem("user")
          let user=JSON.parse(userJson)
          request.get("/thesis/review",{
            params: {
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              reviewer:user.username,
              batch:res1
            }
          }).then(res=>{
            console.log("/review",res)
            this.tableData=res.data.records
            this.total=res.data.records.length
          })

          request.get("/thesis/havereviewed",{
            params: {
              pageNum: this.currentPage2,
              pageSize: this.pageSize2,
              reviewer:user.username
            }
          }).then(res=>{
            console.log("/havereviewed",res)
            this.tableData2=res.data.records
            this.total2=res.data.records.length
          })
          this.sign=1
          this.batchDialogVisible=false
        }
      })
    },
    add(){
      this.dialogVisible=true;
      this.form={};

    },
    save() {
      if(this.dtableData[0].grade<0 || this.dtableData[0].grade>20 ||
          this.dtableData[1].grade<0 || this.dtableData[1].grade>30 ||
          this.dtableData[2].grade<0 || this.dtableData[2].grade>15 ||
          this.dtableData[3].grade<0 || this.dtableData[3].grade>15 ||
          this.dtableData[4].grade<0 || this.dtableData[4].grade>20)
      {this.$message.error('请在正确范围内打分！');
        return false;}
      //保存评审结果
      this.eval.thesisid=this.thesisid;
      let userJson=sessionStorage.getItem("user");
      let user=JSON.parse(userJson)
      console.log("this.eval.id",this.eval.id)
      this.eval.reviewer=user.username;
      this.eval.grade1=this.dtableData[0].grade;
      this.eval.grade2=this.dtableData[1].grade;
      this.eval.grade3=this.dtableData[2].grade;
      this.eval.grade4=this.dtableData[3].grade;
      this.eval.grade5=this.dtableData[4].grade;
      let total=0;
      for(let i=0;i<this.dtableData.length;i++){
        total=total+parseInt(this.dtableData[i].grade);
      }
      this.eval.total=total;
      this.eval.qualified=this.qualified;
      this.eval.comments=this.textarea;
      this.eval.submitTime=new Date();
      request.post("/grade", this.eval).then(res => {
        console.log(res)
        if (res.code==='0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()
        // this.isfinish=1
        this.dialogVisible=false

      })
      this.form.reviewednum=this.reviewednum+1
      if(this.form.reviewednum==3){
        this.form.status=3

      }
      this.savethesis()
    },
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogVisible=true

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
    handleSizeChange2(pageSize){
      this.pageSize2=pageSize
      if (this.sign==1){
        this.load()
      }
    },
    handleCurrentChange2(pageNum){
      this.currentPage2=pageNum
      if (this.sign==1){
        this.load()
      }
    },
    back(row){
      let userJson=sessionStorage.getItem("user");
      let user=JSON.parse(userJson)
      this.eval.reviewer=user.username;
      console.log(row.thesisid,user.username)
      this.backform.reviewer=user.username;
      this.backform.thesisid=row.thesisid;
      request.get("/grade/returnID/", {
        params: {
          thesisid:row.thesisid,
          reviewer:user.username,
          //option: this.options,
        }
      }).then(res => {
        this.gradeid=res.data
        console.log("this.gradeid",this.gradeid)
      })
    },
    handleDelete(row) {
      request.delete("/grade/" + this.gradeid).then(res => {
        if (res.code ==='0') {
          this.$message({
            type: "success",
            message: "退回成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()
      })
      console.log("this.backform",this.backform)
      request.put("/back/insert",this.backform)
      console.log("have:/back/insert")
      this.form.status="1"
      this.form.id=row.id
      console.log("row",row)
      this.form.reviewernum=row.reviewernum-1
      this.savethesis()
    },
    savethesis(){
      console.log("this.form.reviewednum",this.form.reviewednum)
      request.put("/thesis", this.form)
      console.log("putsuccess")
      this.load()
    }

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
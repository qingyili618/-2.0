<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <!-- 表格区域-->
    <el-table :data="tableData"  :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%">
      <el-table-column prop="thesisid" label="ID" sortable align="center" />
      <el-table-column prop="title" label="题目" align="center" />
      <el-table-column prop="department" label="所在系" align="center"/>
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" align="center"/>
      <el-table-column prop="memo" label="说明" align="center"/>
      <el-table-column prop="url" label="论文编号" align="center"/>
      <!--点击按钮区域-->
      <el-table-column fixed="right" label="投票" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" type="primary" @click="previewPDF(scope.row)" :class="disable?'click-disable':''" align="center" v-show='this.flag==true'>投票</el-button>
          <el-button size="mini" type="primary"  align="center" v-show='this.flag==false'>已投票</el-button>
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

      <!--弹窗批改区域-->
      <el-dialog
          v-model="dialogVisible"
          title="论文预览PDF"
          width="1250px"
          height="1000px">
        <div id="container" style="width:100%;height:100%;overflow:hidden;">

          <div>
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

              <el-table :data="dtableData" style="width: 100%">
                <div>专家评分</div>
                <el-table-column label="总分" prop="total" />
                <el-table-column label="综合评价">
                  <template #default="props">
                    <span v-if="props.row.qualified===1">通过</span>
                    <span v-if="props.row.qualified===2">不通过</span>
                  </template>
                </el-table-column>

                <el-table-column label="评价" width="300">
                  <template #default="props">
                    <el-input v-model="props.row.comments" :rows="4" type="textarea" disabled></el-input>
                  </template>
                </el-table-column>

              </el-table>
              <el-form-item label="投票评价" style="padding-top:10px">
                <el-radio-group v-model="vote" label="投票通过"  >
                  <el-radio :label="1">通过</el-radio>
                  <el-radio :label="2">不通过</el-radio>
                  <el-radio :label="3">弃权</el-radio>
                </el-radio-group>
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
                title="确认投票？"
                @confirm="save"
                @cancel="cancelEvent">
                <template #reference>
                  <el-button>确定</el-button>
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
  name: 'committee',
  components: {
    pdf

  },
  data() {
    return {
      flag:true,
      disable:false,
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      form:{
      },
      eval: {
        //thesisid: null,
        reviewer: '',
        total: null,
        qualified: null,
        vote:null,
        comments: '',
        submitTime: null,
      },
      message: '',
      dialogVisible: false,
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData:[],
      dtableData:[],
      scr: null,
      pdfPage: 1,
      pageCount: 0,
      pdfUrl: "",
      evaluation: null,
      thesisid: null,
      committeeid: null,
      vote: ref(1),
      sign:0,  //指示是否选过batch
      batchDialogVisible:false
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
      this.thesisid=row.thesisid;
      this.dialogVisible = true;
      this.form=JSON.parse(JSON.stringify(row));
      // this.pdfUrl="/"+row.url+".pdf";
      this.pdfUrl="/pdf/"+row.thesisid+"_"+row.title+".pdf";
      request.post("/committee/review",this.form).then(res => {
        console.log(res)
        //渲染数据
        this.dtableData = res.data
        this.total = res.data.total
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
          resolve(res.data)
        })
      }).then(res1=>{
        if (res1==null){
          this.$message({
            type: "error",
            message: "不存在该批次"
          })
        }else{
          this.batch = res1
          request.get("/thesis",{
            params: {
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              status: "4",
              batch:res1
            }
          }).then(res=>{
            console.log(res)
            this.tableData=res.data.records
            this.total=res.data.total
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
      if(this.vote==1){
        this.form.id = 1;
      }
      else if(this.vote==2){
        this.form.id = 2;
      }
      else{
        this.form.id = 3;
      }
      console.log(this.vote);
      console.log(this.thesisid,"4444",this.batch)
      new Promise((resolve,reject)=> {
        request.get("/committeevote/getcommitteeid",{
          params: {
            thesisid:this.thesisid,
            batch:this.batch
          }
        }).then(res=>{
          console.log(res)
          this.form.memo = res.data
          // console.log(this.form)
          resolve();
        })
      }).then(()=>{
        console.log(this.form)
        request.put('/committee/vote',this.form).then(res=>{
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "投票成功"
            })
            //sessionStorage.setItem("user", JSON.stringify(this.form))
            // 触发Layout更新用户信息
            //this.$emit("userInfo")
            this.load()  //刷新表格的数据
            this.dialogVisible = false //关闭弹窗
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      })
      this.disable = true
      this.flag = false
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
  }
}
</script>

<style lang="scss" scoped>
.click-disable{
  pointer-events: none;
}
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
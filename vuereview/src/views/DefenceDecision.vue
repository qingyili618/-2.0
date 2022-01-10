<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%">
      <el-table-column prop="id" v-if=false label="序号" align="center" />
      <el-table-column prop="thesisid" label="论文编号" align="center" />
      <el-table-column prop="url" v-if=false label="论文地址"  align="center"/>
      <el-table-column prop="title" label="论文题目" align="center"/>
      <el-table-column prop="department" label="所属系"  align="center"/>
      <el-table-column prop="batch" label="批次" align="center" />
      <el-table-column fixed="right" label="上传答辩结果" width="160" align="center">
        <template #default="scope">
          <el-button type="text" @click="previewPDF(scope.row)">填写</el-button>
          <el-dialog v-model="centerDialogVisible" title="上传答辩结果" width="80%" center>
            <el-scrollbar height="400px">
            <div>
              <iframe :src="pdfUrl" frameborder="1" style="width:700px;height:1000px;float:left"></iframe>
            </div>
        <el-form ref="form" :model="form" >
          <el-form-item label="论文题目">
            <el-input v-model="form.title" disabled></el-input>
          </el-form-item>
          <el-form-item label="论文编号">
            <el-input v-model="form.thesisid" disabled></el-input>
          </el-form-item>
              <el-form-item label="答辩成绩">
                <el-input v-model="form.grade"></el-input>
              </el-form-item>
          <el-form-item label="通过票数">
            <el-input v-model="form.yesses"></el-input>
          </el-form-item>
            <el-form-item label="不通过票数">
              <el-input v-model="form.noes"></el-input>
            </el-form-item>

              <el-form-item label="答辩结果">
                <el-radio-group v-model="form.decision">
                  <el-radio label="9">通过</el-radio>
                  <el-radio label="10">上会</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="答辩意见">
                <el-input v-model="form.comment" type="textarea"></el-input>
              </el-form-item>
              <el-form-item label="答辩记录">
                <el-input v-model="form.record" type="textarea"></el-input>
              </el-form-item>
        </el-form>
            </el-scrollbar>

            <template #footer>
          <span class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取消</el-button>
            <el-popconfirm
                confirm-button-text="是"
                cancel-button-text="否"
                :icon="InfoFilled"
                icon-color="red"
                title="确认结果填写无误？"
                @confirm="getresults(scope.row)"
                @cancel="cancelEvent">
              <template #reference>
                <el-button>确定</el-button>
              </template>
            </el-popconfirm>
            </span>
            </template>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>

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

<script >
import request from "@/utils/request";
import { ref, defineComponent } from 'vue'

export default{
  name: "CommitteeVote",
  data() {
    return {
      sign:0,  //指示是否选过batch
      batchDialogVisible:false,
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      form: {
        id:"",
        thesisid:'',
        grade: "",
        yesses: "",
        noes: "",
        decision:"",
        comment: "",
        record: "",
        status:""},
      newform: {
        thesisid:'',
        grade: "",
        yesses: "",
        noes: "",
        decision:"",
        comment: "",
        record: "",},
      pdfUrl: "",
      centerDialogVisible: ref(false),
      // search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData:[],
    }
  },
  created() {
    this.batchDialogVisible=true
  },
  mounted() {
    this.getYear();
  },
  methods: {
    getYear(){
      request.put("/batch/distinctYear").then(res=>{
        console.log(res)
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
          request.get("/defencedecision",{
            params: {
              batch: res1
            }
          }).then(res=>{
            console.log(res)
            this.tableData=res.data
            this.total=res.data.total
          })
          this.batchDialogVisible=false
          this.sign=1
        }

      })
    },
    previewPDF(row){
      this.centerDialogVisible = true
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
      console.log(row.url);
      // this.pdfUrl="/"+row.url+".pdf"
      this.pdfUrl="/pdf/"+row.thesisid+"_"+row.title+".pdf";
      console.log(this.pdfUrl);
    },

    getresults(row){
      // console.log(this.form)
      // console.log(this.form.decision);
      this.form.status=this.form.decision
      console.log(this.form.status)
      this.form.thesisid=JSON.parse(JSON.stringify(row)).thesisid;
      this.form.id=JSON.parse(JSON.stringify(row)).id;
      this.form=JSON.parse(JSON.stringify(this.form))
      console.log(this.form)
      request.put("/thesis", this.form).then(res2 => {});

      this.newform.thesisid=this.form.thesisid
      this.newform.grade=this.form.grade
      this.newform.yesses=this.form.yesses
      this.newform.noes=this.form.noes
      this.newform.comment=this.form.comment
      this.newform.record=this.form.record
      this.newform.decision=this.form.status
      console.log(this.newform)
      request.post("/defencedecision", this.newform).then(res3 => {
        console.log(res3)
        if (res3.code === '0') {
          this.$message({
            type: "success",
            message: "记录成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res3.msg
          })
        }
      });
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
.el-pagination {
  text-align: center;
}
</style>
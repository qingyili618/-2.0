<template>
  <div style="margin:20px 20px;">
    <el-steps :active= this.step>
      <el-step title="评审专家评审中" ></el-step>
      <el-step title="会评中"></el-step>
      <el-step title="会评结束"></el-step>
    </el-steps>

  <el-table :data="tableData" border :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" style="width: 95%" >
    <el-table-column label="专家评分" align="center" >
    <el-table-column prop="name" label="姓名" align="center" />
    <el-table-column prop="thesisid" label="论文编号" align="center"  />
    <el-table-column prop="title" label="论文题目" align="center"/>
    <el-table-column prop="department" label="研究领域" align="center" />
    <el-table-column prop="grade1" label="打分1" align="center" />
    <el-table-column prop="grade2" label="打分2" align="center" />
    <el-table-column prop="grade3" label="打分3" align="center" />
    <el-table-column prop="grade4" label="打分4" align="center" />
    <el-table-column prop="grade5" label="打分5" align="center" />
    <el-table-column prop="total" label="总分" align="center" />
    <el-table-column prop="comments" label="评分意见" align="center" />
      </el-table-column>
  </el-table>
    <div style="margin-top: 20px">
    <el-table :data="tableData1" border :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" style="width: 95%">
      <el-table-column label="会评情况" align="center" >
        <el-table-column prop="yesses" label="同意通过" align="center" />
        <el-table-column prop="noes" label="不同意通过" align="center"  />
        <el-table-column prop="equal" label="弃权" align="center"  />
        <el-table-column prop="status" label="论文会评后通过情况" align="center"  />
      </el-table-column>
    </el-table>
</div>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Student",
  components: {

  },
  data() {
    return {
      // form:{},
      // dialogVisible: false,
      // search:'',
      // currentPage: 1,
      // pageSize: 10,
      // total: 0,
      tableData:[],
      tableData1:[],
      thesisid:'',
      batch:'',
      status:'',
      step:""
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      let userJson = sessionStorage.getItem("user");
      let user = JSON.parse(userJson)
      console.log(user.username)
      request.post("/student", user).then(res => {
        console.log(res)
        this.tableData = res.data
        if (res.data[0].thesisid != null & res.data[0].batch != null) {
          this.thesisid = res.data[0].thesisid
          this.batch = res.data[0].batch
        }
        request.get("/student", {
          params: {
            thesisid: this.thesisid,
            batch: this.batch
          }
        }).then(res=>{
          console.log(res),
            this.tableData1 = res.data
        }
        )
        request.get("/student/getstate",{
          params: {
            thesisid: this.thesisid,
            batch: this.batch
          }
      }).then(res=>{
            console.log(res)
            this.status = res.data
          if(this.status == "1"||this.status =="2"||this.status =="3"){
            console.log(this.status)
            this.step = "1"
          }
          else if(this.status == "4"){
            this.step = "2"
          }
          else{
            this.step = "3" }
      })

      })
    },

  }
}
</script>


<template>
  <div style="margin:10px 0;flex:1 1 0%;">
    <div style="margin-top: 5px;
    margin-left: 10px;
    font-size: 14px;
    color: rgb(102, 102, 102);">
      当前位置：主页
    </div>
    <div style="height:0.8px;background-color: rgb(102, 102, 102);margin-top: 5px" ></div>
    <div style="margin:10px 0;margin-left: 30px">
      <el-alert style="width: 30%;margin:20px 0px;"
          title="请先右上角选择角色"
          type="warning"
          close-text="知道了">
      </el-alert>
<!--    <el-breadcrumb separator="/">-->
<!--      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
<!--      <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item>-->
<!--      <el-breadcrumb-item>活动列表</el-breadcrumb-item>-->
<!--      <el-breadcrumb-item>活动详情</el-breadcrumb-item>-->
<!--    </el-breadcrumb>-->
      <el-row :gutter="12">
        <el-badge :value="value" class="item">
          <el-button size="big">待办事宜</el-button>
        </el-badge>
        <el-col :span="3">

        </el-col>

<!--        <el-col :span="8">-->
<!--          <el-card shadow="hover"> 公告 </el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="8">-->
<!--          <el-card shadow="never"> 欢迎 </el-card>-->
<!--        </el-col>-->
      </el-row>
    </div>
    <div style="margin:20px 20px;">
      <el-table :data="tableData" border
                :default-sort = "{prop: 'time', order: 'descending'}"
                :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" style="width: 95%">
        <el-table-column prop="role" label="任务角色 " width=200px align="center"/>
        <el-table-column prop="send" label="发送方"  align="center"/>
        <el-table-column prop="info" label="消息内容"  align="center"/>
        <el-table-column prop="time" label="发送时间"  align="center" sortable/>
        <el-table-column prop="status" label="阅读状态"  sortable align="center">
          <template #default="scope1">
            <span v-if="JSON.parse(JSON.stringify(scope1.row)).status== '已读'" style="color: #11b95c">已读</span>
            <span v-else style="color: red">未读</span>
          </template>

        </el-table-column>
<!--        <el-table-column prop="status" label="阅读状态"  sortable align="center"/>-->

        <el-table-column fixed="right" label="操作" width="160" align="center">
          <template #default="scope">
            <el-button type="primary" size="mini"  @click="confirm(scope.row)">确认</el-button>

          </template>
        </el-table-column>


      </el-table>
    </div>
    <div id="main" style="margin: 20px 0">
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
    <div style="
    color:rgb(102, 102, 102);
    margin-left: 40px;
    margin-top: 40px;
    padding-bottom: 40px;
    font-family: 华文仿宋 ">
      <div>
        咨询电话：16673135278
      </div>
      <div>
        咨询时间：工作日8:30-17:30
      </div>
      <div>
        1、关于常规业务流程、操作方法等问题，可直接询问管理员；
      </div>
      <div>
        2、如有其他技术问题，可将问题详情和系统截图发送至邮箱：1789043007@qq.com。
      </div>
    </div>

  </div>
</template>
<script>
import request from "../utils/request";
import global from "../layout/Layout"

export default {
  name: "Home",
  data() {
    return {
      dialogVisible:false,
      role1:{
        role:''
      },
      value:'',
      tableData:[],//add_lqy
      currentPage: 1,
      pageSize: 10,
      total: 0,
      form:{},
      status:'',
      multipleSelection: [],//role choice
      // path: this.$route.path  //设置默认高亮 前面可以加user:{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    confirm(row){
      this.form=JSON.parse(JSON.stringify(row))
      this.$confirm(this.form.info, this.form.role, {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认',
        cancelButtonText: '取消'
      }).then(() => {
            this.$message({
              type: 'info',
              message: '保存修改'
            });
            this.form.status="已读"
            request.put("/information", this.form)
            this.load()
            this.load()
          })

    },
    load(){



      let userStr = sessionStorage.getItem("user") || "{}"
      this.user = JSON.parse(userStr)
      // const id = parseInt(this.user)
      console.log(this.user.username)

      //let id=1
      //role
      request.get("/roleallocation",{
        params: {
          employeeId: this.user.username
        }
      }).then(res =>{
        // console.log(res)
        if(res.code==='0') {
          this.dtableData=res.data
          this.dialogVisible=true
        }
      })
      //info
      request.get("/information/count",{
        params: {
          userid: this.user.username
        }
      }).then(res=>{
        // console.log(res)
        if(res.data==0){
          this.value=''
        }
        else{
          this.value=res.data
        }


      })
      request.get("/information",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          userid: this.user.username
        }
      }).then(res=>{
        // console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })

    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()

    },

    save(){
      this.role1.role = this.multipleSelection[0].name
      sessionStorage.setItem("role",JSON.stringify(this.role1))
      this.dialogVisible=false
      this.$message({
        type: "success",
        message: "角色选择成功"
      })
      global.methods.reload()
    },
    select(selection, row) {
      if (selection.length > 1) {
        let del_row = selection.shift()
        this.$refs.multipleTable.toggleRowSelection(del_row, false)
        // this.$message({
        //   type: "error",
        //   message: "已选择超过三位评审专家！"
        // })
      }
    },
    selectAll(selection){
      if (selection.length > 1) {
        selection.length = 1

      }

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection)
    },

  }
}

</script>
<style scoped>
.el-pagination {
  text-align: center;
}

</style>
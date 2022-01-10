<template>
  <div style="padding:10px">
    <el-Button type="primary" @click ="DownTemp" style="margin-left:10px;margin-top: 10px" size="small">下载模板</el-Button>
    <el-input v-model="search" placeholder="请输入关键字" style="margin-left: 5px;width: 20%" clearable></el-input>
    <el-button type="primary" style="margin-left: 7px" @click="load">查询</el-button>
    <!-- 搜索区域 -->
    <div style="margin: 10px 10px 10px 10px">

    </div>

    <div style="margin:20px 20px;">
    <el-table :data="batchData" :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%">
      <el-table-column prop="id" label="ID" sortable width="70" align="center"/>
      <el-table-column prop="academicYear" label="学年" align="center" />
      <el-table-column prop="semester" label="学期" align="center" />
      <el-table-column prop="type" label="硕士类型" align="center" />
      <el-table-column prop="startTime" label="起始时间" align="center" />
      <el-table-column prop="endTime" label="截止时间" align="center" />
      <el-table-column prop="thesisNumber" label="论文数量" align="center" />
      <el-table-column fixed="right" label="操作" width="260" align="center">
          <template #default="scope">
            <el-button size="mini"  @click="handleEdit(scope.row)">修改信息</el-button>
            <el-upload
                class="upload-demo inline-block margin-right-10"
                ref="upload"
                :show-file-list=false
                :http-request="handleUploadFile"
                :before-upload="beforeUpload"
                v-on:click="getbatch(scope.row)"
                accept=".xls, .xlsx">
              <el-Button type="primary" size="mini" style="margin-left:10px">导入论文</el-Button>
            </el-upload>
            <!-- 设置下载模板 -->
            <div slot="tip" class="el-upload__tip" style="margin-left:10px">仅支持.xlsx,.xls 文件大小≤4M</div>
          </template>
      </el-table-column>
    </el-table>

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

      <el-dialog
          v-model="dialogVisible"
          title="修改批次信息"
          width="30%">
        <el-form :model="fetchData" label-width="150px">
          <el-form-item label="学年">
            <el-input v-model="fetchData.academicYear" placeholder="2021-2022"></el-input>
          </el-form-item>
          <el-form-item label="学期">
            <el-radio-group v-model="fetchData.semester">
              <el-radio label="第一学期"></el-radio>
              <el-radio label="第二学期"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="起止日期">
            <el-col :span="11">
              <el-date-picker
                  v-model="fetchData.startTime"
                  type="datetime"
                  placeholder="选择起始时间"
                  style="width: 100%"
              ></el-date-picker>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
              <el-date-picker
                  v-model="fetchData.endTime"
                  type="datetime"
                  placeholder="选择截止时间"
                  style="width: 100%"
              ></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="硕士类型">
            <el-radio-group v-model="fetchData.type">
              <el-radio label="学硕"></el-radio>
              <el-radio label="专硕"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="论文数量">
            <el-input v-model="fetchData.thesisNumber"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="save">确定</el-button>
            </span>
        </template>
      </el-dialog>
      </div>
      </div>

</template>

<script>
import request from "@/utils/request";
import {ref} from "vue";
import { dataConversionUtil } from '@/utils/data2Excel'

import axios from 'axios'

export default {
  name: "Batch",
  components:{},
  data() {
    return {
      batchData:[],
      fetchData:{},
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible:false,
      fileUploadInstance:null
    }

  },
  created() {
    this.load()
    this.fileUploadInstance = axios.create({
      baseURL:"/api",
      timeout: 5000,
      headers: {
        'Content-Type': 'multipart/form-data ',
      }
    })
  },
  methods:{
    load(){
      request.get("/batch",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res)
        this.batchData=res.data.records
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
    handleEdit(row){
      // this.fetchData=JSON.parse(JSON.stringify(row))
      // console.log(this.fetchData)
      // this.dialogVisible=true
      console.log(row)
      sessionStorage.setItem('ifBatchExist',1)
      sessionStorage.setItem('batchdata',JSON.stringify(row))
      this.$router.push('/newbatch')
    },
    save(){
      if (this.fetchData.id) {
        request.put("/batch", this.fetchData).then(res => {
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
          this.dialogVisible=false

        })
      } else {
        request.post("/batch", this.fetchData).then(res => {
          console.log(res)

          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()
          this.dialogVisible = false
        })
      }
    },
    // 上传文件之前
    beforeUpload(file) {
      let fileSize = file.size
      const FOUR_M= 4*1024*1024;
      //大于4M，不允许上传
      if(fileSize>FOUR_M){
        this.$message.error("最大上传4M")
        return  false
      }
      return true
    },
    // 读取文件
    handleUploadFile(val){
      const param = new FormData()
      // 将上传文件数组依次添加到参数paramsData中
      param.append("file",val.file)
      param.append("batch",this.batch)
      console.log(param.values())
      let data={
        data:param
      }
      this.fileUploadInstance.post("/thesis/upload",param).then(res => {
        console.log(res)
        if(res.data.code==0){
          this.$message.success("上传成功！")
        }
        else{
          this.$message.error(res.data.msg)
        }
      })
    },
    getbatch(row){
      this.batch = row.id
    },
    DownTemp(){
      var data = []
      var tableHeader = [["序号","论文题目","姓名","导师姓名","导师所属系所","研究方向","学生学号"]]
      dataConversionUtil.dataToExcel('附件1:论文名单模板', data, tableHeader)
    },
  }
}
</script>

<style lang="scss" scoped>
.el-pagination {
  text-align: center;
}
.inline-block {
  display: inline-block;
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
</style>
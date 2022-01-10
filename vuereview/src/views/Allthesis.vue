<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入论文信息" style="width: 80%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
   <el-table :data="tableData"
              :header-cell-style="{background:'#f5f7fa',color:'#02468f'}"
              border
              style="width: 95%">
      <el-table-column prop="thesisid" label="论文编号" sortable align="center" width="100"/>
      <el-table-column prop="title" label="题目"  width="200px"/>
      <el-table-column prop="author" label="学生姓名" align="center" width="80"/>
      <el-table-column prop="supervisor" label="导师姓名" align="center" width="80"/>
      <el-table-column prop="department" label="所在系" :filters="[
        { text: '金融', value: '金融' },
        { text: '管信', value: '管信' },
        { text: '企管', value: '企管' },
      ]"
                       :filter-method="filterHandler"/>
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" align="center" />
      <el-table-column prop="memo" label="说明" align="center" />
      <el-table-column fixed="right" prop="stringstatus" label="状态" align="center" width="80" :filters="[
        { text: '未分配', value: '未分配' },
        { text: '已分配', value: '已分配' },
        { text: '已评审', value: '已评审' },
        { text: '评审已分配上会', value: '评审已分配上会' },
        { text: '会评通过', value: '会评通过' },
        { text: '会评不通过', value: '会评不通过' },
        { text: '已分配给系主任', value: '已分配给系主任' },
        { text: '已安排答辩', value: '已安排答辩' },
        { text: '答辩通过', value: '答辩通过' },
        { text: '答辩上会', value: '答辩上会' },
        { text: '答辩已分配上会', value: '答辩已分配上会' },
        { text: '答辩上会未通过', value: '答辩上会未通过' },
      ]"
                       :filter-method="filterHandler"/>
    </el-table>
    <div style="padding:10px">
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
  </div>

</template>

<script>
import request from "@/utils/request";
import {ref} from "vue";
import { dataConversionUtil } from '@/utils/data2Excel'

import axios from 'axios'

export default {
  name: "Allthesis",
  components:{},
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search:'',
      tableData:[],
      distinctYear:[],
      academicYear:'',
      semOptions:ref([
        {
          semester: '第一学期',
          label: '第一学期',
        },
        {
          semester: '第二学期',
          label: '第二学期',
        }]),
      semester:ref(''),
      typeOptions:ref([
        {
          type: '学硕',
          label: '学硕',
        },
        {
          type: '专硕',
          label: '专硕',
        }]),
      type:ref(''),
      batch:'',
    }

  },
  created() {
    this.load()
  },
  methods:{
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()

    },
    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    loadThesis(){
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
    load(){
      request.get("/thesis/getallthesis",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
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
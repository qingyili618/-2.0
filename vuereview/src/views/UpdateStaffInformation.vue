<template>
  <el-form ref="form" :model="form" label-width="120px" style="margin-top: 20px;margin-right: 30px">
    <el-form-item label="姓名" style="width: 30%">
      <el-input v-bind:model-value ="name" disabled></el-input>
    </el-form-item>
      <el-form-item label="工号" style="width: 30%">
        <el-input v-bind:model-value ="employeeId" disabled></el-input>
      </el-form-item>
        <el-form-item label="所在院系" style="width: 30%">
          <el-input v-bind:model-value ="department" disabled></el-input>
        </el-form-item>
            <el-form-item label="研究方向" style="width: 50%">
              <el-input v-model="form.direction"></el-input>
            </el-form-item>
    <el-form-item label="研究领域" style="width: 50%">
      <el-input v-model="form.field"></el-input>
    </el-form-item>
    <el-form-item label="生日" style="width: 30%">
      <el-date-picker v-model="form.birthday" type="datetime"  :clearable="false"
                           placeholder="选择日期" :picker-options="startDateLimit">
    </el-date-picker>
    </el-form-item>
    <el-popconfirm title="确认修改吗?" @confirm="handleUpdate(form)">
      <template #reference>
        <el-button type="primary" size="small" style="margin-left: 100px">确定修改</el-button>
      </template>
    </el-popconfirm>
  </el-form>

</template>

<script>
import request from "@/utils/request";
import { ref, defineComponent } from 'vue'

export default {
  name: "UpdateStaffInformation",
  data() {
    return {
      name:"",
      employeeId:'',
      department:'',
      form: {
        displine:'',
        birthday:'',
        direction:'',
        field:''
      },
    }},
  created(){
    this.load()
  },
  methods:{
    load(){
      let userJson = sessionStorage.getItem("user");
      let user = JSON.parse(userJson)
      request.get("/teacher/staffinfo",{params:{
        username: user.username
        }}).then(res=>{
          console.log(res)
        this.form = res.data
        this.name = res.data.name
        this.employeeId = res.data.employeeId
        this.department = res.data.department
      })
    },
    handleUpdate(form){
      request.post("/teacher/updateinfo",form).then(res=>{
        console.log(res)
        this.$message({
          type: "success",
          message: "修改成功"
        })
      })
    }
  },
}
</script>

<style scoped>

</style>
<template>
  <div style="padding:50px">
    <el-form ref="form" :model="batchform" label-width="120px">
      <el-form-item label="学年">
        <el-input v-model="batchform.academicYear" placeholder="2021-2022"></el-input>
      </el-form-item>
      <el-form-item label="学期">
        <el-radio-group v-model="batchform.semester">
          <el-radio label="第一学期"></el-radio>
          <el-radio label="第二学期"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="起止日期">
        <el-col :span="11">
          <el-date-picker
              v-model="batchform.startTime"
              type="datetime"
              placeholder="选择起始时间"
              style="width: 100%"
          ></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-date-picker
              v-model="batchform.endTime"
              type="datetime"
              placeholder="选择截止时间"
              style="width: 100%"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <!--      <el-form-item label="Instant delivery">-->
      <!--        <el-switch v-model="form.delivery"></el-switch>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="Activity type">-->
      <!--        <el-checkbox-group v-model="form.type">-->
      <!--          <el-checkbox label="Online activities" name="type"></el-checkbox>-->
      <!--          <el-checkbox label="Promotion activities" name="type"></el-checkbox>-->
      <!--          <el-checkbox label="Offline activities" name="type"></el-checkbox>-->
      <!--          <el-checkbox label="Simple brand exposure" name="type"></el-checkbox>-->
      <!--        </el-checkbox-group>-->
      <!--      </el-form-item>-->
      <el-form-item label="硕士类型">
        <el-radio-group v-model="batchform.type">
          <el-radio label="学硕"></el-radio>
          <el-radio label="专硕"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="论文数量">
        <el-input v-model="batchform.thesisNumber"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";
import global from "../views/Batch"
export default {
  name: "Newbatch",
  data() {
    return {
      batchform: {
          // id: '',
          // academicYear:'',
          // semester:'',
          // startTime: '',
          // endTime: '',
          // type: '',
          // thesisNumber: '',
      },

    }
  },
  created() {
    if (sessionStorage.getItem('ifBatchExist')==1){
      let batchJson=sessionStorage.getItem('batchdata')
      let batch=JSON.parse(batchJson)
      request.get("/batch/getbatch",{
        params:{
          batchid:batch.id
        }
      }).then(res=>{
        console.log(res)
        this.batchform=res.data
      })
      sessionStorage.setItem('ifBatchExist',0)
    }
  },
  methods: {
    save() {
      if (this.batchform.id) {
        request.put("/batch", this.batchform).then(res => {
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
        request.post("/batch", this.batchform).then(res => {
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

  },
}
</script>

<style scoped>
.el-pagination {
  text-align: center;
}
</style>
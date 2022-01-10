<template>
  <div style="height:50px;line-height:50px;border-bottom:1px solid #ccc;display:flex">
    <div style="width:200px; padding-left: 30px;font-weight:bold;color:dodgerblue">毕业论文内审系统</div>
    <div style="flex:1"></div>
    <div style="width:100px">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{user.username}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>

            <el-dropdown-item @click="$router.push('/login')" divided>退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Header",
  data() {
    return {
      user:{},
      dialogVisible:false,
      role:[],
      // path: this.$route.path  //设置默认高亮 前面可以加user:{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      let userStr = sessionStorage.getItem("user") || "{}"
      this.user = JSON.parse(userStr)
      const id = parseInt(this.user.id)

      // let id=1
      request.get("/roleallocation",id).then(res =>{
        console.log(res)
        if(res.code==='0') {
          this.user=res.data
        }
      })
    }

  }
}
</script>

<style scoped>

</style>
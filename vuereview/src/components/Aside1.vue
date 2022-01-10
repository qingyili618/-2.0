<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh-50px)"
        default-active="path"
        router
        class="el-menu-vertical-demo">

        <el-sub-menu index="1" v-if="userNew.role==='工作人员'">
          <template #title>系统管理</template>
          <el-menu-item index="/user">用户管理</el-menu-item>
        </el-sub-menu>
<!--工作人员-->
      <el-menu-item index="/staff" v-if="userNew.role==='工作人员'" >内审分配论文</el-menu-item>
      <!--      评审专家-->
      <!--        <el-menu-item index="/thesis" v-if="user.role===2">论文评审</el-menu-item>-->
      <el-menu-item index="/thesis" v-if="userNew.role==='评审专家'">论文评审</el-menu-item>

      <el-menu-item index="/staffcommittee" v-if="userNew.role==='工作人员'">内审上会</el-menu-item>
      <!--      委员会-->
      <el-menu-item index="/committee" v-if="userNew.role==='学位评定委员会'">委员会投票</el-menu-item>
      <el-menu-item index="/committeevote" v-if="userNew.role==='工作人员'">整理内审结果</el-menu-item>
      <el-menu-item index="/staffdefence" v-if="userNew.role==='工作人员'">答辩分配论文到系主任</el-menu-item>
      <!--      系主任-->
      <el-menu-item index="/defense" v-if="userNew.role==='系主任'">（系主任）安排答辩</el-menu-item>
      <el-menu-item index="/defencedecision" v-if="userNew.role==='工作人员'">答辩结果</el-menu-item>
      <el-menu-item index="/defenceCommittee" v-if="userNew.role==='工作人员'">答辩上会</el-menu-item>
<!--学生-->
      <el-menu-item index="/student" v-if="userNew.role==='学生'">（学生）查看论文结果</el-menu-item>

        <!--<el-menu-item index="/pdf" >PDF预览</el-menu-item> -->


    </el-menu>
  </div>



</template>

<script>
import request from "../utils/request";

export default {
  name: "Aside",
  data() {
    return {
      user:{},//账号、密码
      userNew: {
        employeeId:'',
        role:''
      },//employeeId,role
      path: this.$route.path  //设置默认高亮 前面可以加user:{}
    }
  },

  created() {
    this.load()
  },
  methods:{
    load(){
      let userStr=sessionStorage.getItem("user") || "{}"
      this.user=JSON.parse(userStr)
      // this.userNew.employeeId=parseInt(this.user.id)
      this.userNew.employeeId=this.user.username

      let userRole=sessionStorage.getItem("role") || "{}"
      this.userNew.role=JSON.parse(userRole).role
      console.log(this.userNew)

      request.get("/user/"+this.user.id).then(res =>{
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
el-menu:hover{
  background-color: red;
}
.aside{
  background-color: #02468f;
}
</style>
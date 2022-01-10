<template>
  <div class="Aside" style="background-color: #02468f">
    <el-menu
        style="width: 200px;padding-left:10px; padding-top:20px;height: calc(100%);background-color:rgba(255,255,255,0.96)"
        default-active="path"
        router
        class="el-menu-vertical-demo">
      <div style="width:200px; padding-left: 30px;font-weight:bold;color:dodgerblue">

      </div>
        <el-sub-menu index="1" v-if="$store.state.role==='超级管理员'">
          <template #title>
            <span class="iconfont icon-ziyuanxhdpi"></span>
            系统管理
          </template>
          <el-menu-item index="/user">
            <span class="iconfont hhtxyonghuguanli1"></span>用户管理</el-menu-item>
        </el-sub-menu>
      <el-menu-item index="/home" >
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-newmail"></use>
        </svg>
        主页 </el-menu-item>
<!--工作人员-->
      <el-menu-item index="/newbatch" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-wallet3"></use>
        </svg>
        批次管理
      </el-menu-item>
      <el-menu-item index="/allthesis" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-reviews"></use>
        </svg>
        查看所有论文
      </el-menu-item>
      <el-menu-item index="/batch" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-uploadtocloud1"></use>
        </svg>
        导入论文

      </el-menu-item>
      <el-menu-item index="/staff" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-switch2"></use>
        </svg>
        内审分配论文

      </el-menu-item>
      <el-menu-item index="/updateStaffInformation" v-if="$store.state.role==='评审专家'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-admin"></use>
        </svg>
        修改个人信息</el-menu-item>

      <!--      评审专家-->
      <!--        <el-menu-item index="/thesis" v-if="user.role===2">论文评审</el-menu-item>-->
      <el-menu-item index="/thesis" v-if="$store.state.role==='评审专家'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-lanuch2"></use>
        </svg>
        论文评审</el-menu-item>

      <el-menu-item index="/staffcommittee" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-Flat-1-r"></use>
        </svg>
        整理内审材料上会</el-menu-item>

      <!--      委员会-->
      <el-menu-item index="/committee" v-if="$store.state.role==='学位评定委员会'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-great"></use>
        </svg>
        委员会投票（内审）</el-menu-item>

      <el-menu-item index="/committee1" v-if="$store.state.role==='学位评定委员会'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-great"></use>
        </svg>
        委员会投票（答辩）</el-menu-item>

      <el-menu-item index="/committeevote" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-way2"></use>
        </svg>
        整理上会结果</el-menu-item>

      <el-menu-item index="/staffdefence" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-switch2"></use>
        </svg>
        答辩分配论文到系主任</el-menu-item>
      <!--      系主任-->
      <el-menu-item index="/defense" v-if="$store.state.role==='系主任'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-idea3"></use>
        </svg>
        （系主任）安排答辩
      </el-menu-item>
      <el-menu-item index="/defencedecision" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-event"></use>
        </svg>
        提交答辩结果
      </el-menu-item>

      <el-menu-item index="/defenceCommittee" v-if="$store.state.role==='工作人员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-uploadtocloud1"></use>
        </svg>
        整理答辩材料上会</el-menu-item>
<!--学生-->
      <el-menu-item index="/student" v-if="$store.state.role==='学生'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-a-winner2"></use>
        </svg>
        （学生）查看结果</el-menu-item>

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
.Aside{
  background-color: #02468f;
}
.icon {
  width: 1.25em; height: 1.25em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

</style>



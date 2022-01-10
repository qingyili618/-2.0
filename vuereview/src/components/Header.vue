<template>
  <div style="height:75px;line-height:50px;display:flex;width: 100%;background-color:#02468f"  class="head"  >
<!--    <div style="width:200px; padding-left: 30px;font-weight:bold;color:dodgerblue">后台管理</div>-->
    <div style="width:200px; padding-left: 40px;font-weight:bold;" class="logo">

    </div>
    <div style="width:280px; padding-left: 30px;font-weight:bold;color:white;font-size:25px" class="image">毕业论文内审系统</div>
    <div style="flex:1" class="image"></div>
    <div style="width:200px;margin-right: 50px" class="image">
      <svg class="icon" aria-hidden="true">
        <use xlink:href="#icon-admin"></use>
      </svg>
      <el-button type="text" style="color: white; font-size: 14px;margin-left: 5px;margin-right: 10px" @click="dialogVisible = true">角色选择</el-button>
      <svg class="icon" aria-hidden="true">
        <use xlink:href="#icon-power"></use>
      </svg>
      <el-button type="text" style="color: white; font-size: 14px;margin-left: 5px;margin-right: 10px" @click="$router.push('/login')" divided>退出</el-button>
<!--      <el-dropdown>-->
<!--        <span class="el-dropdown-link" >-->
<!--            {{user.username}}-->
<!--          <i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--        </span>-->
<!--        <template #dropdown>-->
<!--          <el-dropdown-menu>-->
<!--            <el-dropdown-item @click="dialogVisible = true">角色选择</el-dropdown-item>-->

<!--            <el-dropdown-item @click="$router.push('/login')" divided>退出系统</el-dropdown-item>-->
<!--          </el-dropdown-menu>-->
<!--        </template>-->

<!--      </el-dropdown>-->
    </div>
    <div style="width:1600px;height:35px; font-weight:lighter;padding-left: 650px;color:white;font-size:14px" class="bottom">版权所有©数据科学与商业分析李青仪小组</div>

  </div>
  <el-dialog v-model="dialogVisible"
             title="角色选择"
             width="450px"
             height="600px">
    <el-table :data="dtableData"
              ref="multipleTable"
              border
              style="width: 100%"
              @selection-change="handleSelectionChange"
              @select="select"
              @select-all="selectAll">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="name" label="角色"  />

      <!--                <el-table-column prop="grade" label="评分"  >-->
      <!--                  &lt;!&ndash; vue2.6后自定义列放弃了slot-scope插槽(网上相关的方法会导致控件显示不了），改用v-slot，这里用了简化版，让没有input和每行数据绑定 &ndash;&gt;-->
      <!--                  &lt;!&ndash; 搜素： vue3使用element-plus的自定义列表格&ndash;&gt;-->
      <!--                  <template #default="scope">-->
      <!--                    <el-input v-model="scope.row.grade"></el-input>-->
      <!--                  </template>-->
<!--                      </el-table-column>-->
    </el-table>
    <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save">确定</el-button>
          </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../utils/request";
import global from "../layout/Layout"
import "@/assets/css/global.css"

export default {
  name: "Header",
  data() {
    return {
      dialogVisible:false,
      role1:{
        role:''
      },
      multipleSelection: [],//role choice
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
      // const id = parseInt(this.user)
      console.log(this.user.username)

      //let id=1
      request.get("/roleallocation",{
        params: {
          employeeId: this.user.username
        }
      }).then(res =>{
        console.log(res)
        if(res.code==='0') {
          this.dtableData=res.data
        }
      })
    },

    save(){
      this.role1.role = this.multipleSelection[0].name
      sessionStorage.setItem("role",JSON.stringify(this.role1))
      this.dialogVisible=false
      this.$message({
        type: "success",
        message: "角色修改成功"
      })
      this.$store.commit('SET_ROLE', this.role1);
      console.log(this.$store.state.role)
      this.$router.push("/home")
      // global.methods.reload()
      this.$router.push("/")
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
.image{
  display: flex;
  justify-content: left;
  align-items: center;
  height: 100%;
  z-index:1;
  font-family: 幼圆;
  background-image: url("../assets/images/蓝色头部.png");
  background-size: cover;
  background-attachment:fixed;
  background-repeat:no-repeat;
}
.el-dropdown-link{
  color:white;
  font-size: 20px;
  padding-right: 30px;
}
.logo{
  display: flex;
  justify-content: left;
  align-items: center;
  height: 100%;
  background-color: #02468f;
  margin-left: 100px;

  background-image: url("../assets/images/logo3.png");
  background-repeat:no-repeat;
  background-position: center;
  background-size: 80%;
}
.bottom{
  position:fixed;
  bottom:0;
  background-color: #02468f;
  font-family: 华文仿宋;
}
.icon {
  width: 1em; height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
  /*padding-left: 10px;*/
}
</style>
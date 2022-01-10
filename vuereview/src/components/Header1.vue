<template>
  <div style="height:50px;line-height:50px;border-bottom:1px solid #ccc;display:flex">
    <div style="width:200px; padding-left: 30px;font-weight:bold;color:dodgerblue">后台管理</div>
    <div style="flex:1"></div>
    <div style="width:150px">
      <el-dropdown>
        <span class="el-dropdown-link" >
            {{user.username}}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="dialogVisible = true">角色选择</el-dropdown-item>

            <el-dropdown-item @click="$router.push('/login')" divided>退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>

      </el-dropdown>
    </div>

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
      <!--                </el-table-column>-->
    </el-table>
  </el-dialog>
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
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    const id = parseInt(this.user.id)

    //let id=1
    request.get("/roleallocation",id).then(res =>{
      console.log(res)
      if(res.code==='0') {
        this.user=res.data
      }
    })
  },
  methods:{
    choicerole(){},
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

</style>
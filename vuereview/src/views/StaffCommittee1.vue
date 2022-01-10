<template>
  <div style="padding:10px">
    <!-- 功能区域 -->
    <div style="margin:10px 0">
<!--      <el-button type="primary">新增</el-button>-->
<!--      <el-button type="primary">导入</el-button>-->
<!--      <el-button type="primary">导出</el-button>-->
      <el-button @click="handleJudgeResult">判断结果</el-button>
      <el-button @click="handleArrange">安排会评</el-button>
      <el-button @click="toggleSelection()">清除选择</el-button>
    </div>
    <!-- 搜索区域 -->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-table
        ref="multipleTable"
        :data="tableData"
        :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column prop="id" label="论文编号" sortable align="center"/>
      <el-table-column prop="title" label="题目" show-overflow-tooltip align="center"/>
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" align="center"/>
      <el-table-column prop="supervisor" label="指导老师" align="center" />
      <el-table-column prop="department" label="所在系" align="center" />
      <el-table-column prop="memo" label="说明" align="center"/>
      <el-table-column prop="avgScore" label="平均得分" align="center" />
      <el-table-column prop="minScore" label="最低得分" align="center"/>
      <el-table-column prop="lowerNum1" label="低于基准1的个数" align="center"/>
      <el-table-column prop="lowerNum2" label="低于基准2的个数" align="center"/>
      <el-table-column prop="status" label="内审结果指派" align="center"/>
      <el-table-column fixed="right" label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini"  @click="handleEdit(scope.row)">修改信息</el-button>
        </template>
      </el-table-column>
    </el-table>


    <div style="margin: 10px 0">
      <!--      <el-pagination-->
      <!--          v-model:currentPage="currentPage"-->
      <!--          :page-sizes="[5, 10, 20]"-->
      <!--          :page-size="pageSize"-->
      <!--          layout="total, sizes, prev, pager, next, jumper"-->
      <!--          :total="total"-->
      <!--          @size-change="handleSizeChange"-->
      <!--          @current-change="handleCurrentChange">-->
      <!--      </el-pagination>-->

      <el-dialog
          v-model="dialogVisible1" title="Tips" width="80%">
        <el-form :model="form" label-width="120px">

          <el-form-item label="会评主持人">
            <div style="margin: 10px 0">
              <el-input v-model="searchTeacher" placeholder="请输入查询条件" style="width: 80%" clearable></el-input>
              <el-button type="primary" style="margin-left: 5px" @click="loadTeacher">查询</el-button>

            </div>
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
<!--              <el-table-column label="选择">-->
<!--                <template slot-scope="scope">-->
<!--                  <el-radio v-model="radio" :label="scope.$index"-->
<!--                            @change.native="radioChange(scope.row, scope.$index)"-->
<!--                  >$nbsp;-->
<!--                  </el-radio>-->
<!--                </template>-->
<!--              </el-table-column>-->
              <el-table-column prop="employeeId" label="专家编号"  />
              <el-table-column prop="name" label="专家姓名"  />
              <el-table-column prop="discipline" label="学科"  />
              <el-table-column prop="direction" label="研究方向"  />
              <el-table-column prop="department" label="系"  />
              <el-table-column prop="field" label="涉及领域"  />
            </el-table>
            <div style="margin: 10px 0">
              <el-pagination
                  v-model:currentPage="currentPageTeacher"
                  :page-sizes="[5, 10]"
                  :page-size="pageSizeTeacher"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange">
              </el-pagination>
            </div>
          </el-form-item>
          <el-form-item label="应到人数">
            <el-input v-model="commiteeForm.supposedNumber" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="实到人数">
            <el-input v-model="commiteeForm.attendanceNumber" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="会评地点">
            <el-input type="textarea" v-model="commiteeForm.address" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="会评时间">
            <div class="block">
<!--              <span class="demonstration">默认</span>-->
              <div class='startTime'>
                开始时间：<el-date-picker v-model="commiteeForm.startTime" type="datetime"  @change="changeStartTime" :clearable="false"
                                     placeholder="选择日期" :picker-options="startDateLimit">
              </el-date-picker>
                <span class="important">*</span>
              </div>
              <!-- 结束时间 -->
              <div class="endTime">
                结束时间：<el-date-picker v-model="commiteeForm.endTime" :picker-options="endDateLimit" type="datetime" :clearable="false" @change="changeEndTime" placeholder="选择日期">
              </el-date-picker>
                <span class="important">*</span>
              </div>
            </div>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Committee",
  components:{},
  data() {
    return {
      //judgement
      lower_num:"2",
      lower_avg1:"75",
      lower_avg2:"85",
      lower_min:"70",
      lower1:"75",
      lower2:"85",
      thesis_status:"2",
      startDateLimit: {
        disabledDate: (time) => {
          // if (this.value2 != "") {
          //   return time.getTime() > new Date(this.value2).getTime();
          // }else{
          return time.getTime() < Date.now() - 8.64e7;//禁用当前日期之前的时间；如果没有后面的-8.64e6就是不可以选择今天的
          // }
        }
      },
      endDateLimit: {
        disabledDate: (time) => {
          if(this.commiteeForm.endTime!= ""){
            return time.getTime() < new Date(this.value1).getTime()//禁用开始日期之前的日期
          }
          return time.getTime() < Date.now() - 8.64e7; //减去一天的时间代表可以选择同一天;
        }
      },
      commiteeForm: {//write into committee table
        id:null,
        startTime: null, //开始时间
        endTime: null, //结束时间
        address: null,
        afterDefence: null,
        attendanceNumber:null,
        chairmanId:null,
        supposedNumber:null,
        thesisNumber:null
      },
      commiteevoteForm: {//write into commiteevote table
        thesisid:'',
        committeeid:'',


      },
      committeeid:'',
      dtableData:[],
      searchTeacher:'',
      currentPageTeacher: 1,
      pageSizeTeacher: 10,
      multipleSelection: [],//teacher multiple choice
      dialogVisible1:false,
      tableData:[],
      multipleSelectionthesis:[]
    }
  },

  created() {
    this.load()
  },
  methods:{
    select(selection, row) {
      if (selection.length > 1) {
        let del_row = selection.shift()
        this.$refs.multipleTable.toggleRowSelection(del_row, false)
        // this.$message({
        //   type: "error",
        //   message: "已选择超过一位评审专家！"
        // })
      }
    },
    selectAll(selection){
      if (selection.length > 1) {
        selection.length = 1

      }

    },

    save(){
      for(var i = 0;i < this.multipleSelectionthesis.length;i++) {
        this.multipleSelectionthesis[i].status='9'//不记得了随便写的 反正要改一个状态
        request.put("/thesis", this.multipleSelectionthesis[i])
      }
      this.dialogVisible1=false;
      this.commiteeForm.chairmanId = this.multipleSelection[0].employeeId;
      console.log(this.commiteeForm)
      request.post("/committee", this.commiteeForm).then(res => {
        console.log(res)
        this.committeeid=res.data;
        // if (res.code === '0') {
        //   console.log(this.commiteevoteForm)
        //   this.$message({
        //     type: "success",
        //     message: "安排成功"
        //   })
        // } else {
        //   this.$message({
        //     type: "error",
        //     message: res.msg
        //   })
        // }

      });

      // console.log(this.commiteevoteForm)
      for(var i = 0;i < this.multipleSelectionthesis.length;i++) {
        this.commiteevoteForm.thesisid = this.multipleSelectionthesis[i].id
        this.commiteevoteForm.committeeid = this.committeeid
        console.log(this.commiteevoteForm)
        request.post("/committeevote", this.commiteevoteForm).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "分配成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

        });
      }
      this.load()
    },

    loadTeacher(){
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
    handleArrange(){

      this.dialogVisible1=true;

      this.loadTeacher();
    },
    //表格多选
    handleSelectionChange(val) {

      if(this.dialogVisible1==true){
        this.multipleSelection = val
        console.log(this.multipleSelection)
      }else {
        this.multipleSelectionthesis = val
        console.log(this.multipleSelectionthesis)
      }
    },
    load(){
      request.get("/committee",{
        params: {
          lower_num:"2",
          lower_avg1:"75",
          lower_avg2:"85",
          lower_min:"70",
          lower1:"75",
          lower2:"85",
          thesis_status:"3"
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data
        this.total=res.data.total
      })
    },
  }

}
</script>

<style scoped>

</style>
<template>
  <div style="padding:10px">
    <div style="margin: 10px 0">
      <el-button type="primary" :disabled="multipleSelectionthesis.length ==0?true:false" @click="handleArrange">安排会评</el-button>
      <el-button :disabled="multipleSelectionthesis.length ==0?true:false" @click="toggleSelection()">清除选择</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <el-table
        ref="multipleTable"
        :data="tableData"
        :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" border style="width: 95%"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="id" v-if=False label="序号" sortable align="center"/>
      <el-table-column prop="batch" v-if=False label="批次" align="center"/>
      <el-table-column prop="thesisid" label="论文编号" sortable align="center" />
      <el-table-column prop="title" label="题目" show-overflow-tooltip align="center"/>
      <!--      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" />-->
      <el-table-column prop="supervisor" label="指导老师" align="center" />
      <el-table-column prop="department" label="所在系" align="center"/>
      <el-table-column prop="memo" label="说明" align="center"/>
      <el-table-column prop="grade" label="得分" align="center"/>
      <el-table-column prop="noes" label="否决票数" align="center"/>
      <el-table-column prop="yesses" label="通过票数" align="center"/>
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
            <el-popconfirm
                confirm-button-text="是"
                cancel-button-text="否"
                :icon="InfoFilled"
                icon-color="red"
                title="确认上传安排？"
                @confirm="save"
                @cancel="cancelEvent">
                <template #reference>
                  <el-button>确定</el-button>
                </template>
            </el-popconfirm>
          </span>
        </template>
      </el-dialog>

      <el-dialog
          v-model="batchDialogVisible"
          title="选择论文批次"
          width="30%">
        <div style="margin:10px 0 ">
          选择学年：<el-select v-model="academicYear" style="margin-left: 20px;width: 160px" placeholder="2020-2021">
          <el-option
              v-for="item in distinctYear"
              :key="item.academicYear"
              :label="item.academicYear"
              :value="item.academicYear"
          >
          </el-option>
        </el-select>
        </div>
        <div style="margin:10px 0 ">
          选择学期：
          <el-radio v-model="semester" label="第一学期">第一学期</el-radio>
          <el-radio v-model="semester" label="第二学期">第二学期</el-radio>
        </div>
        <div style="margin:10px 0 ">
          选择论文类型：
          <el-radio v-model="type" label="学硕">学硕</el-radio>
          <el-radio v-model="type" label="专硕">专硕</el-radio>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="load">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ref} from "vue";

export default {
  name: "Committee",
  components:{},
  data() {
    return {
      batchDialogVisible:false,
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
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
        afterDefence: 2,
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
      thesisid:"",
      multipleSelectionthesis:[],
      thesisform:{
        id:'',
        status:'',
      },
      inforForm:{
        userid:'',
        role:'',
        info:'',
        send:'',
      },
    }
  },

  created() {
    this.batchDialogVisible=true
  },
  mounted() {
    this.getYear();
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
      new Promise((resolve,reject)=>{
        for(var i = 0;i < this.multipleSelectionthesis.length;i++) {
          this.thesisform.id=this.multipleSelectionthesis[i].id;
          this.thesisform.status="11";
          request.put("/thesis",this.thesisform)
        }
        this.dialogVisible1=false;
        this.commiteeForm.chairmanId = this.multipleSelection[0].employeeId;
        console.log(this.commiteeForm)
        new Promise((resolve,reject)=>{
          request.post("/committee", this.commiteeForm).then(res => {
            console.log("aaa",res)
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
            resolve();
          });
        }).then(()=>{
          // console.log(this.commiteevoteForm)
          for(var i = 0;i < this.multipleSelectionthesis.length;i++) {
            this.commiteevoteForm.thesisid = this.multipleSelectionthesis[i].thesisid
            this.commiteevoteForm.committeeid = this.committeeid
            console.log("-------------------------------",this.commiteevoteForm)
            this.commiteevoteForm.batch = this.multipleSelectionthesis[i].batch
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
        })
        let userJson=sessionStorage.getItem("user");
        let user=JSON.parse(userJson)
        this.inforForm.userid=user.username
        this.inforForm.role="学位评定委员会会员"
        this.inforForm.info="您有一场已安排的学位评定会议，请及时参与！"
        this.inforForm.send="工作人员"
        request.post("/information",this.inforForm).then(res=>{
          if (res.code ==='0') {
            this.$message({
              type: "success",
              message: "上会安排已通知学位评定委员会"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          resolve();
        })
      }).then(()=>{
        this.load()
        this.load()
      })
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
    handleCurrentChange(pageNum){
      this.currentPageTeacher=pageNum
      this.loadTeacher()

    },
    handleSizeChange(pageSize){
      this.pageSizeTeacher=pageSize
      this.loadTeacher()

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
    //选定表格指定列
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    getYear(){
      request.put("/batch/distinctYear").then(res=>{
        console.log(res)
        this.distinctYear=res.data
        this.total=res.data.total
      })
    },
    getBatch(){
      request.get("/batch/getbatchID",{
        params:{
          year:this.academicYear,
          semester:this.semester,
          type:this.type
        }
      }).then(res=>{
        console.log(res)
        this.batch=res.data
        this.total=res.data.total
      })
    },
    load(){
      new Promise((resolve,reject)=>{
        request.get("/batch/getbatchID",{
          params:{
            year:this.academicYear,
            semester:this.semester,
            type:this.type
          }
        }).then(res=>{
          console.log(res)
          resolve(res.data)
        })
      }).then(res1=>{
        if (res1==null){
          this.$message({
            type: "error",
            message: "不存在该批次"
          })
        }else{
          request.get("/defense/defCommitteeVO",{
            params: {
              batch:res1
            }
          }).then(res=>{
            console.log(res)
            this.tableData=res.data
            this.total=res.data.total
          })
          this.batchDialogVisible=false
        }
      })
    },

  }

}
</script>

<style scoped>
.el-pagination {
  text-align: center;
}
</style>
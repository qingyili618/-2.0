<template>
  <div style="padding:10px;flex:1 1 0%;">
    <!-- 功能区域 -->
    <div style="margin:10px 0 ">
      <el-button @click="ruleChange">判断结果</el-button>
      <el-button type="primary" @click="handleArrange" :disabled="multipleSelectionthesis.length ==0?true:false">安排会评</el-button>
      <el-button @click="toggleSelection()" :disabled="multipleSelectionthesis.length ==0?true:false">清除选择</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
    </div>
    <div style="margin:20px 20px;">
      <el-table
          ref="multipleTable"
          :data="tableData"
          @selection-change="handleSelectionChange"
          border :header-cell-style="{background:'#f5f7fa',color:'#02468f'}" style="width: 90%"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" v-if=False label="序号" sortable />
        <el-table-column prop="batch" v-if=False label="批次" />
        <el-table-column prop="thesisid" label="论文编号" sortable />
        <el-table-column prop="title" label="题目" show-overflow-tooltip />
        <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" />
        <el-table-column prop="supervisor" label="指导老师" />
        <el-table-column prop="department" label="所在系" />
        <el-table-column prop="memo" label="说明" />
        <el-table-column prop="avgScore" label="平均得分" />
        <el-table-column prop="minScore" label="最低得分" />
        <el-table-column prop="lowerNum1" label="低于基准1的个数" />
        <el-table-column prop="lowerNum2" label="低于基准2的个数" />
        <el-table-column prop="status" label="建议结果指派" />
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button size="mini"  style="float:left" @click="changeStatus(scope.row)">更新状态</el-button>
            <el-button size="mini"  style="float:left" @click="getGradeDetails(scope.row)">评分详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="margin: 10px 0">
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

    <div style="margin: 10px 0">
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
                  @size-change="handleSizeChange3"
                  @current-change="handleCurrentChange3">
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
          v-model="dialogVisible2"
          title="" width="100%">
        <el-table :data="gradedata" border style="width: 100%">
          <el-table-column prop="reviewer" label="评阅人工号" />
          <el-table-column prop="grade1" label="选题和综述得分"  />
          <el-table-column prop="grade2" label="理论知识得分" />
          <el-table-column prop="grade3" label="方法与能力得分" />
          <el-table-column prop="grade4" label="应用价值得分" />
          <el-table-column prop="grade5" label="论文写作得分" />
          <el-table-column prop="total" label="总分" />
          <el-table-column prop="qualified" label="综合评价" />
          <el-table-column prop="submitTime" label="评分时间" />
        </el-table>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">确定</el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog
          v-model="dialogVisible3"
          title="确认论文内审结果"
          width="30%">
        <el-radio v-model="confirmedStatus" label="5">内审通过</el-radio>
        <el-radio v-model="confirmedStatus" label="6">内审不通过</el-radio>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible3 = false">取消</el-button>
            <el-popconfirm
                confirm-button-text="是"
                cancel-button-text="否"
                :icon="InfoFilled"
                icon-color="red"
                title="确认修改状态？"
                @confirm="saveStatusChange"
                @cancel="cancelEvent">
                <template #reference>
                  <el-button>确定</el-button>
                </template>
            </el-popconfirm>
          </span>
        </template>
      </el-dialog>

      <el-dialog
          v-model="dialogVisible4"
          title="更改结果指派规则"
          width="30%">
        <h>1) 评审专家中如有<el-input v-model="lower_num" @change="onChange" style="width: 10%"></el-input>位（含）以上专家评分低于
          <el-input v-model="lower1" style="width: 12%"></el-input>分，或评审成绩平均分低于<el-input v-model="lower_avg1" style="width: 12%"></el-input>
          分时，不予组织答辩，半年后重新送审论文；<br><br>
          2) 如有1位专家评分低于<el-input v-model="lower_min" style="width: 12%"></el-input>分，
          或<el-input v-model="lower_num" style="width: 10%"></el-input>位（含）以上评审专家评分在<el-input v-model="lower2" style="width: 12%"></el-input>分（含）
          以下，或评审成绩平均分低于<el-input v-model="lower_avg2" style="width: 12%"></el-input>分时，须提交学位评定委员会会评表决。</h>
        <div v-text="remark" style="white-space: pre-wrap;"></div>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="saveRuleChange">确定</el-button>
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
      currentPage:'',
      pagesize:'',
      batchDialogVisible:false,
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      //judgement
      lower_num:"2",
      lower_avg1:"75",
      lower_avg2:"85",
      lower_min:"70",
      lower1:"75",
      lower2:"85",
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
        afterDefence: 1,
        attendanceNumber:null,
        chairmanId:null,
        supposedNumber:null,
        thesisNumber:null
      },
      commiteevoteForm: {//write into commiteevote table
        thesisid:'',
        committeeid:'',
        batch:'',
      },
      committeeid:'',
      dtableData:[],
      searchTeacher:'',
      currentPageTeacher: 1,
      pageSizeTeacher: 10,
      multipleSelection: [],//teacher multiple choice
      dialogVisible1:false,
      dialogVisible2:false,
      dialogVisible3:false,
      dialogVisible4:false,
      confirmedStatus:'',
      tableData:[],
      thesisid:"",
      multipleSelectionthesis:[],
      gradedata:[],
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
  // watch:{
  //
  // },
  methods:{
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()

    },
    onChangelower_num(e){
      var { value } = e.target;
      this.lower_num = { value }//把数据存入inputData
    },
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
          this.thesisform.status="4";
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
          request.get("/committee",{
            params: {
              lower_num:this.lower_num,
              lower_avg1:this.lower_avg1,
              lower_avg2:this.lower_avg2,
              lower_min:this.lower_min,
              lower1:this.lower1,
              lower2:this.lower2,
              thesis_status:"3",
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
    getGradeDetails(row){
      this.dialogVisible2=true;
      this.loadgrades(row);
    },
    loadgrades(row){
      request.get("/grade", {
        params: {
          thesisid:row.thesisid
        }
      }).then(res => {
        console.log(res)
        console.log(res)
        this.gradedata = res.data
        this.total = res.data.total
        console.log(this.gradedata)
      })
    },
    changeStatus(row){
      this.thesisform.id=row.id;
      this.dialogVisible3=true;
    },
    saveStatusChange(){
      this.thesisform.status=this.confirmedStatus;
      request.put("/thesis",this.thesisform);
      this.dialogVisible3=false;
      this.load();
    },
    ruleChange(){
      this.dialogVisible4=true;
    },
    saveRuleChange(){
      this.dialogVisible4=false;
      this.load();
    }
  }

}
</script>

<style scoped>
.el-pagination {
  text-align: center;
}

::-webkit-scrollbar{width:5px;}
::-webkit-scrollbar-track{background-color:#02468f;}
::-webkit-scrollbar-thumb{background-color:#d6d6d6;}

</style>
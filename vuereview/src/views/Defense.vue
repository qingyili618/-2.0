<template>
  <div style="padding:10px">
    <!-- 功能区域 -->
    <div style="margin:10px 0">
      <el-button type="primary" style="margin-left: 10px" @click="batchDialogVisible=true">更改论文批次</el-button>
      <el-button @click="handleArrange" :disabled="multipleSelectionthesis.length ==0?true:false">安排答辩</el-button>
    </div>
    <el-table
        ref="multipleTable"
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="thesisid" label="论文编号" sortable />
      <el-table-column prop="title" label="题目" show-overflow-tooltip />
      <el-table-column prop="submitTime" value-format="YYYY-MM-DD" label="提交时间" />
      <el-table-column prop="supervisor" label="指导老师" />
      <el-table-column prop="department" label="所在系" />

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

          <el-form-item label="答辩专家">
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

          <el-form-item label="答辩秘书">
            <div style="margin: 10px 0">
              <el-input v-model="searchTeacher1" placeholder="请输入查询条件" style="width: 80%" clearable></el-input>
              <el-button type="primary" style="margin-left: 5px" @click="loadTeacher">查询</el-button>

            </div>
            <el-table :data="dtableData1"
                      ref="dtableData1"
                      border
                      style="width: 100%"
                      @row-click="singleElection">
              <el-table-column label="" width="65">
                <template #default="scope">
                  <el-radio class="radio" v-model="templateSelection" :label="scope.$index">&nbsp;</el-radio>
                </template>
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
                  v-model:currentPage="currentPageTeacher1"
                  :page-sizes="[5, 10]"
                  :page-size="pageSizeTeacher1"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total1"
                  @size-change="handleSizeChange1"
                  @current-change="handleCurrentChange1">
              </el-pagination>
            </div>
          </el-form-item>
          <el-form-item label="答辩地点">
            <el-input type="textarea" v-model="defenseForm.place" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="答辩时间">
            <div class="block">
              <!--              <span class="demonstration">默认</span>-->
              <div class='startTime'>
                开始时间：<el-date-picker v-model="defenseForm.startTime" type="datetime"  @change="changeStartTime" :clearable="false"
                                     placeholder="选择日期" :picker-options="startDateLimit">
              </el-date-picker>
                <span class="important">*</span>
              </div>
              <!-- 结束时间 -->
              <div class="endTime">
                结束时间：<el-date-picker v-model="defenseForm.endTime" :picker-options="endDateLimit" type="datetime" :clearable="false" @change="changeEndTime" placeholder="选择日期">


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
                title="确认提交该答辩安排？"
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
import {ref} from 'vue';
export default {
  name: "Defense",
  components:{},
  data() {
    return {
      batchDialogVisible:false,
      sign:0,  //指示是否选过batch
      templateSelection: '',
      distinctYear:[],
      academicYear:'',
      semester:'',
      type:'',
      batch:'',
      //judgement
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
          if(this.defenseForm.endTime!= ""){
            return time.getTime() < new Date(this.value1).getTime()//禁用开始日期之前的日期
          }
          return time.getTime() < Date.now() - 8.64e7; //减去一天的时间代表可以选择同一天;
        }
      },
      defenseForm: {//write into committee table
        startTime: null, //开始时间
        endTime: null, //结束时间
        place: null,
        secretary:null,
        expert1:null,
        expert2:null,
        expert3:null,
        expert4:null,
        expert5:null,
      },
      thesisform:{
        id:'',
        status:'',
      },
      dialogVisible1:false,
      tableData:[],

      dtableData:[],
      searchTeacher:'',
      currentPageTeacher: 1,
      pageSizeTeacher: 10,
      multipleSelection: [],//teacher multiple choice
      multipleSelectionthesis:[],
      total:'',

      dtableData1:[],
      searchTeacher1:'',
      currentPageTeacher1: 1,
      pageSizeTeacher1: 10,
      total1:'',
    }
  },
  created() {
    this.batchDialogVisible=true
  },
  mounted() {
    this.getYear();
  },
  methods:{
    singleElection (row) {
      this.templateSelection = this.dtableData1.indexOf(row);
      this.templateRadio = row.id;
      this.defenseForm.secretary=row.employeeId
    },
    select(selection, row) {
      if (selection.length > 5) {
        let del_row = selection.shift()
        this.$refs.multipleTable.toggleRowSelection(del_row, false)
        // this.$message({
        //   type: "error",
        //   message: "已选择超过一位评审专家！"
        // })
      }
    },

    selectAll(selection){
      if (selection.length > 5) {
        selection.length = 5

      }

    },

    save(){
      for(var j =0; j < this.multipleSelection.length;j++){
        if(j==0){
          this.defenseForm.expert1 = this.multipleSelection[0].employeeId;
        }
        if(j==1){
          this.defenseForm.expert2 = this.multipleSelection[1].employeeId;
        }
        if(j==2){
          this.defenseForm.expert3 = this.multipleSelection[2].employeeId;
        }
        if (j==3){
          this.defenseForm.expert4 = this.multipleSelection[3].employeeId;
        }
        if (j==4){
          this.defenseForm.expert5 = this.multipleSelection[4].employeeId;
        }
      }
      console.log(this.defenseForm)
      request.post("/defense",this.defenseForm).then(res => {
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
      // console.log(this.multipleSelectionthesis)
      for(var i = 0;i < this.multipleSelectionthesis.length;i++) {
        this.thesisform.id=this.multipleSelectionthesis[i].id;
        this.thesisform.status="8";
        request.put("/thesis",this.thesisform)
        // this.defenseForm.thesisid = this.multipleSelectionthesis[i].thesisid;
      }
      this.dialogVisible1=false;
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
    loadTeacher1(){
      request.get("/teacher",{
        params: {
          pageNum: this.currentPageTeacher1,
          pageSize: this.pageSizeTeacher1,
          search: this.searchTeacher1
        }
      }).then(res=>{
        console.log(res)
        this.dtableData1=res.data.records
        this.total1=res.data.total
      })
    },
    handleArrange(){
      this.dialogVisible1=true;
      this.loadTeacher();
      this.loadTeacher1();
    },
    handleSizeChange(pageSize){
      this.pageSizeTeacher=pageSize
      this.loadTeacher()

    },
    handleCurrentChange(pageNum){
      this.currentPageTeacher=pageNum
      this.loadTeacher()

    },
    handleSizeChange1(pageSize){
      this.pageSizeTeacher1=pageSize
      this.loadTeacher1()

    },
    handleCurrentChange1(pageNum){
      this.currentPageTeacher1=pageNum
      this.loadTeacher1()

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
    getYear(){
      request.put("/batch/distinctYear").then(res=>{
        console.log(res)
        this.distinctYear=res.data
        this.total=res.data.total
      })
    },
    load(){
      new Promise((resolve,reject)=>{
        let userJson=sessionStorage.getItem("user");
        let user=JSON.parse(userJson)
        request.get("/teacher/getDepartment",{
          params:{
            teacherid:user.username
          }
        }).then(res1=>{
          // console.log("sssssssssssssssssss",res1)
          resolve(res1)
        })
      }).then(res1=>{
        new Promise((resolve,reject)=>{
          console.log(this.academicYear,"22",this.semester,"33",this.type)
          request.get("/batch/getbatchID",{
            params:{
              year:this.academicYear,
              semester:this.semester,
              type:this.type
            }
          }).then(res=>{
            // console.log(res)
            resolve(res);
          })
        }).then(res=>{
          if (res.data==null){
            this.$message({
              type: "error",
              message: "不存在该批次"
            })
          }else{
            request.get("/thesis/defence",{
              params: {
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                status: "7",
                batch:res.data,
                department:res1.data[0].department
              }
            }).then(res=>{
              console.log(res)
              this.tableData=res.data.records
              this.total=res.data.total
            })
            this.batchDialogVisible=false
            this.sign=1
          }
        })
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
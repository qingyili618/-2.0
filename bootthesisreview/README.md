研究生论文内审系统
===========================
## 介绍
本系统可以用来完成对于论文的内审，致力于更高效的完成论文评审的过程

## 开始使用
### 环境依赖
Node.js  14.17.6\
vue 3.0\
springboot 2.6.1\
JDK 8.0

### 部署步骤
1. npm install -g  @vue/cli //安装vue运行环境
2. npm install  //安装运行环境
3. npm run serve  //启动项目
4. npm install element-plus --save //引入element plus组件

### 目录结构描述
#### 前端
-- src   \
   |-- App.vue  
   |-- assets\
   |   |-- css\
   |   |-- icon\
   |   |-- icon2 \
   |   |-- icon3\
   |   |-- images\
   |-- components\
   |-- layout\
   |-- main.js\
   |-- router     
   |-- store\
   |-- utils\
   |   |-- data2Excel.js\
   |   |-- htmlToExcel.js\
   |   -- request.js\
   -- views\
       |-- Batch.vue\
       |-- Committee.vue\
       |-- Committee1.vue\
       |-- CommitteeVote.vue\
       |-- DefenceCommittee.vue\
       |-- DefenceDecision.vue\
       |-- Defense.vue\
       |-- Home.vue\
       |-- Login.vue\
       |-- Newbatch.vue\
       |-- Register.vue\
       |-- Staff.vue\
       |-- StaffCommittee.vue\
       |-- StaffCommittee1.vue\
       |-- StaffDefence.vue\
       |-- Student.vue\
       |-- Thesis.vue\
       |-- UpdateStaffInformation.vue\
       `-- User.vue

#### 后端
|-- src\
|   |-- main\
|   |   |-- java\
|   |   |   -- com\
|   |   |       -- example\
|   |   |           -- bootthesisreview\
|   |   |               |-- BootthesisreviewApplication.java\
|   |   |               |-- Excel\
|   |   |               |   |-- ThesisExcel.java\
|   |   |               |   |-- excelRead.java\
|   |   |               |   -- excelRescoure.java\
|   |   |               |-- common\
|   |   |               |   |-- MybatisPlusConfig.java\
|   |   |               |   -- Result.java\
|   |   |               |-- controller\
|   |   |               |   |-- BackController.java\
|   |   |               |   |-- BatchController.java\
|   |   |               |   |-- CommitteeController.java\
|   |   |               |   |-- CommitteeVoteController.java\
|   |   |               |   |-- DefenceDecisionController.java\
|   |   |               |   |-- DefenseController.java\
|   |   |               |   |-- GradeController.java\
|   |   |               |   |-- InformationController.java\
|   |   |               |   |-- RoleAllocationController.java\
|   |   |               |   |-- StudentController.java\
|   |   |               |   |-- TeacherController.java\
|   |   |               |   |-- ThesisController.java\
|   |   |               |   -- UserController.java\
|   |   |               |-- entity\
|   |   |               |   |-- Back.java\
|   |   |               |   |-- Batch.java\
|   |   |               |   |-- Committee.java\
|   |   |               |   |-- CommitteeVoteVO.java\
|   |   |               |   |-- Committeevote.java\
|   |   |               |   |-- DefenceDecision.java\
|   |   |               |   |-- DefenceDecisionVO.java\
|   |   |               |   |-- Defense.java\
|   |   |               |   |-- Grade.java\
|   |   |               |   |-- GradeVO.java\
|   |   |               |   |-- Information.java\
|   |   |               |   |-- Role.java\
|   |   |               |   |-- RoleAllocation.java\
|   |   |               |   |-- Student.java\
|   |   |               |   |-- StudentVO.java\
|   |   |               |   |-- Teacher.java\
|   |   |               |   |-- Thesis.java\
|   |   |               |   |-- User.java\
|   |   |               |   |-- defCommitteeVO.java\
|   |   |               |   -- gradedThesisVO.java\
|   |   |               |-- handler\
|   |   |               |   -- MyMetaObjectHandler.java\
|   |   |               |-- mapper\
|   |   |               |   |-- BackMapper.java\
|   |   |               |   |-- BatchMapper.java\
|   |   |               |   |-- CommitteeMapper.java\
|   |   |               |   |-- CommitteeVoteMapper.java\
|   |   |               |   |-- DefenceDecisionMapper.java\
|   |   |               |   |-- DefenseMapper.java\
|   |   |               |   |-- GradeMapper.java\
|   |   |               |   |-- GradeThesisMapper.java\
|   |   |               |   |-- InformationMapper.java\
|   |   |               |   |-- RoleAllocationMapper.java\
|   |   |               |   |-- StudentMapper.java\
|   |   |               |   |-- TeacherMapper.java\
|   |   |               |   |-- ThesisMapper.java\
|   |   |               |   -- UserMapper.java\
|   |   |               -- service\
|   |   |                   |-- IBackService.java\
|   |   |                   |-- IBatchService.java\
|   |   |                   |-- ICommitteeService.java\
|   |   |                   |-- IDefenseService.java\
|   |   |                   |-- IGradeService.java\
|   |   |                   |-- IInformationService.java\
|   |   |                   |-- IStudentService.java\
|   |   |                   |-- ITeacherService.java\
|   |   |                   |-- IThesisService.java\
|   |   |                   |-- IUserService.java\
|   |   |                   |-- IgradeThesisService.java\
|   |   |                   -- impl\
|   |   |                       |-- BackServiceImpl.java\
|   |   |                       |-- BatchServiceImpl.java\
|   |   |                       |-- CommitteeServiceImpl.java\
|   |   |                       |-- DefenseServiceImpl.java\
|   |   |                       |-- GradeServiceImpl.java\
|   |   |                       |-- InformationServiceImpl.java\
|   |   |                       |-- StudentServiceImpl.java\
|   |   |                       |-- TeacherServiceImpl.java\
|   |   |                       |-- ThesisServiceImpl.java\
|   |   |                       |-- UserServieImpl.java\
|   |   |                       -- gradedThesisServiceImpl.java\
|   |   -- resources\
|   |       |-- application.properties\
|   |       |-- static\
|   |       -- templates\
|   -- test\
|       -- java\
|           -- com\
|               -- example\
|                   -- bootthesisreview\
|                       |-- BootthesisreviewApplicationTests.java\
|                       -- Test2.java\
|-- target\
|   |-- classes\
|   |   |-- application.properties\
|   |   -- com\
|   |       -- example\
|   |           -- bootthesisreview\
|   |               |-- BootthesisreviewApplication.class\
|   |               |-- Excel\
|   |               |   |-- ThesisExcel.class\
|   |               |   |-- excelRead$1.class\
|   |               |   |-- excelRead.class\
|   |               |   -- excelRescoure.class\
|   |               |-- common\
|   |               |   |-- MybatisPlusConfig.class\
|   |               |   -- Result.class\
|   |               |-- controller\
|   |               |   |-- BackController.class\
|   |               |   |-- BatchController.class\
|   |               |   |-- CommitteeController.class\
|   |               |   |-- CommitteeVoteController.class\
|   |               |   |-- DefenceDecisionController.class\
|   |               |   |-- DefenseController.class\
|   |               |   |-- GradeController.class\
|   |               |   |-- InformationController.class\
|   |               |   |-- RoleAllocationController.class\
|   |               |   |-- StudentController.class\
|   |               |   |-- TeacherController.class\
|   |               |   |-- ThesisController.class\
|   |               |   -- UserController.class\
|   |               |-- entity\
|   |               |   |-- Back.class\
|   |               |   |-- Batch.class\
|   |               |   |-- Committee.class\
|   |               |   |-- CommitteeVoteVO.class\
|   |               |   |-- Committeevote.class\
|   |               |   |-- DefenceDecision.class\
|   |               |   |-- DefenceDecisionVO.class\
|   |               |   |-- Defense.class\
|   |               |   |-- Grade.class\
|   |               |   |-- GradeVO.class\
|   |               |   |-- Information.class\
|   |               |   |-- Role.class\
|   |               |   |-- RoleAllocation.class\
|   |               |   |-- Student.class\
|   |               |   |-- StudentVO.class\
|   |               |   |-- Teacher.class\
|   |               |   |-- Thesis.class\
|   |               |   |-- User.class\
|   |               |   |-- defCommitteeVO.class\
|   |               |   -- gradedThesisVO.class\
|   |               |-- handler\
|   |               |   -- MyMetaObjectHandler.class\
|   |               |-- mapper\
|   |               |   |-- BackMapper.class\
|   |               |   |-- BatchMapper.class\
|   |               |   |-- CommitteeMapper.class\
|   |               |   |-- CommitteeVoteMapper.class\
|   |               |   |-- DefenceDecisionMapper.class\
|   |               |   |-- DefenseMapper.class\
|   |               |   |-- GradeMapper.class\
|   |               |   |-- GradeThesisMapper.class\
|   |               |   |-- InformationMapper.class\
|   |               |   |-- RoleAllocationMapper.class\
|   |               |   |-- StudentMapper.class\
|   |               |   |-- TeacherMapper.class\
|   |               |   |-- ThesisMapper.class\
|   |               |   -- UserMapper.class\
|   |               -- service\
|   |                   |-- IBackService.class\
|   |                   |-- IBatchService.class\
|   |                   |-- ICommitteeService.class\
|   |                   |-- IDefenseService.class\
|   |                   |-- IGradeService.class\
|   |                   |-- IInformationService.class\
|   |                   |-- IStudentService.class\
|   |                   |-- ITeacherService.class\
|   |                   |-- IThesisService.class\
|   |                   |-- IUserService.class\
|   |                   |-- IgradeThesisService.class\
|   |                   -- impl\
|   |                       |-- BackServiceImpl.class\
|   |                       |-- BatchServiceImpl.class\
|   |                       |-- CommitteeServiceImpl.class\
|   |                       |-- DefenseServiceImpl.class\
|   |                       |-- GradeServiceImpl.class\
|   |                       |-- InformationServiceImpl.class\
|   |                       |-- StudentServiceImpl.class\
|   |                       |-- TeacherServiceImpl.class\
|   |                       |-- ThesisServiceImpl.class\
|   |                       |-- UserServieImpl.class\
|   |                       -- gradedThesisServiceImpl.class\
|   |-- generated-sources\
|   |   -- annotations\
|   |-- generated-test-sources\
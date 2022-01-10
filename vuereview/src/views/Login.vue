<template>

  <div class="login" >
    <div class="back" >
      <div class="main" >

        <el-form ref="form" :model="form" size="normal" :rules="rules">
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user-solid" placeholder="请输入用户名/工号/学号" v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" v-model="form.password" show-password></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <el-input prefix-icon="el-icon-key" type="text" v-model="code" placeholder="请输入验证码" style="width: 60%">


            </el-input>
            <template slot="prepend">验证码</template>
            <template slot="append">

            </template>
            <div class="login-code" @click="refreshCode">
              <Identify :identifyCode="identifyCode"></Identify>
            </div>
<!--            <div class="login-code" @click="refreshCode">-->
<!--              <Identify :identifyCode="identifyCode"></Identify>-->
<!--            </div>-->
          </el-form-item>
          <el-form-item>
            <el-button style="width:100%;font-family:'黑体';background:rgba(20,100,238,0.64);font-size: large" type="primary" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

  </div>
</template>

<script>
import request from "../utils/request";
import Identify from "../components/Identify"
import { mapActions } from 'vuex'
import Header from "../components/Header"

export default {
  name: "Login.vue",
  components: {
    Identify,
    Header
  },
  data() {
    return {
      code: "",
      form: {
        username: "",
        password: "",

      },
      identifyCodes: '1234567890abcdefjhijklinopqrsduvwxyz',
      identifyCode: '',
      rules: {
        username: [
          {required: true, message: '请输入用户名',trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码',trigger: 'blur'},
        ],
        // code: [{ required: true, message: "请输入验证码", trigger: "blur" }]
      },

    }
  },
  mounted () {
    // 初始化验证码
    this.identifyCode = ''
    this.makeCode(this.identifyCodes, 4)
  },
  methods: {
    // 引入验证接口
    ...mapActions("d2admin/account", ["login"]),
    // 重置验证码
    refreshCode () {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode (o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
    },
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },

    login() {
      if (this.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
        this.$message.error('请填写正确验证码')
        this.refreshCode()
        return
      }
      this.$refs['form'].validate((valid)=>{
        if(valid) {
          request.post("/user/login", this.form).then(res => {
            console.log(res)
            if (res.code==='0'){ //.code === 0) {

              //this.$router.push("/")
              this.$message({
                type: "success",
                message: "登录成功"
              })

              sessionStorage.setItem("user",JSON.stringify(res.data))
              //sessionStorage.setItem("username",this.form.username);
              this.$router.push("/")  //登录成功后页面跳转
              Header.dialogVisible=true
              Header.methods.load()

            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })

        }
      })
      //this.$router.push("/")
    }
  }
}
</script>


<style rel="stylesheet/scss" lang="scss">
.login {
  //width: 100%;
  //height: 100ch;
  vertical-align: middle;
  overflow-x:hidden;
  overflow-y:hidden;
  display: flex;
  //justify-content: left;
  align-items: center;

  background-image: url("../assets/images/背景.jpg");
  background-size: 1500px 800px;
  background-attachment:fixed;
  background-repeat:no-repeat;
}
.back {
  margin-top: 80px;
  margin-left: auto;
  margin-right: auto;
  //text-align: center;
  width:1200px;
  height: 800px;
  background-image: url("../assets/images/小背景新.png");
  background-size: 1200px 675px;
  background-repeat:no-repeat;
  //background-attachment:fixed;
  //filter:alpha(Opacity=60);-moz-opacity:0.6;opacity: 0.6
}
.main {
  display: block;
  margin-top: 280px;
  margin-left: 60px;
  width:350px;
  //filter:alpha(Opacity=60);-moz-opacity:0.6;opacity: 0.6
}
.title {
  margin: 0px auto 30px auto;
  text-align: left;
  color: #1414ee;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}


.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}

</style>
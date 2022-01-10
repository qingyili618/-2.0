<template>
  <div style="background-color: #d6d6d6;
              height: 100%;
              width: 100%;
              box-sizing: border-box;
              position: absolute;">
<!--    <router-view v-if="isRouterAlive"/>-->
    <!-- 头部 -->
    <Header/>
    <!-- 主体 -->

    <div style="display:flex;margin-left: 100px;margin-right: 100px;background-color: #f0f0f0;height: calc(100% - 115px);-webkit-overflow-scrolling: touch; overflow: scroll;overflow-x:hidden;">
      <!-- 侧边栏 -->
      <Aside />
      <!-- 内容区域 -->
      <router-view style="flex:1" v-if="isRouterAlive"/>
    </div>
  </div>

</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
export default {
  name: "Layout.vue",
  components: {
    Header,
    Aside
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      // let _this=this
      this.isRouterAlive = false
      this.$nextTick(() => (this.isRouterAlive = true))
    }
  }
}
</script>

<style scoped>
::-webkit-scrollbar{width:5px;}
::-webkit-scrollbar-track{background-color:#02468f;}
::-webkit-scrollbar-thumb{background-color:#d6d6d6;}
</style>
import { createRouter, createWebHashHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/home", //"/user",
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import("@/views/Home"),
      },
        {
        path: 'user',
        name: 'User',
        component: () =>import("@/views/User"),
      },
      {
        path: 'thesis',
        name: 'Thesis',
        component: () =>import("@/views/Thesis"),
      },
      {
        path: 'staff',
        name: 'Staff',
        component: () =>import("@/views/Staff"),
      },
      {
        path: 'student',
        name: 'Student',
        component: () =>import("@/views/Student"),
      },
      {
        path: 'staffdefence',
        name: 'StaffDefence"',
        component: () =>import("@/views/StaffDefence"),
      },
      {
        path: 'staffcommittee',
        name: 'StaffCommittee"',
        component: () =>import("@/views/StaffCommittee"),
      },
      {
        path: 'committeevote',
        name: 'CommitteeVote',
        component: () =>import("@/views/CommitteeVote"),
      },
      {
        path: 'defencedecision',
        name: 'DefenceDecision',
        component: () =>import("@/views/DefenceDecision"),
      },
      {
        path: 'defense',
        name: 'defense',
        component: () =>import("@/views/Defense"),
      },
      {
        path: '/committee',
        name: 'Commottee',
        component: ()=>import("@/views/Committee")
      },
      {
        path: '/committee1',
        name: 'Commottee1',
        component: ()=>import("@/views/Committee1")
      },
      {
        path: 'defenceCommittee',
        name: 'DefenceCommittee',
        component: () =>import("@/views/DefenceCommittee"),
      },
      {
        path: 'defenceCommittee1',
        name: 'DefenceCommittee1',
        //component: () =>import("@/views/DefenceCommittee1"),
      },
      {
        path:'batch',
        name:'Batch',
        component: () =>import("@/views/Batch"),
      },
      {
        path:'newbatch',
        name:'Newbatch',
        component: () =>import("@/views/Newbatch"),
      },
      {
        path:'updateStaffInformation',
        name:'updateStaffInformation',
        component: () =>import("@/views/UpdateStaffInformation"),
      },
      {
        path:'allthesis',
        name:'allthesis',
        component: () =>import("@/views/Allthesis"),
      },

    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/Login")
  },
  {
    path: '/Register',
    name: 'Register',
    component: ()=>import("@/views/Register")
  },


]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router

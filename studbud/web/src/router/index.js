import Vue from 'vue'
import VueRouter from 'vue-router'
import Kanban from '@/views/kanban.vue'
import Pomodoro from '@/views/pomodoro.vue'
import Reading from '@/views/reading.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/kanban',
    name: 'kanban',
    component: Kanban
  },
  {
    path: '/pomodoro',
    name: 'pomodoro',
    component: Pomodoro
  },
  {
    path: '/reading',
    name: 'reading',
    component: Reading
  },
  {
    path: '*',
    redirect: '/kanban'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

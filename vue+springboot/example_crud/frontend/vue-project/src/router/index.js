import { createRouter,createWebHistory } from 'vue-router'
import ListMember from '@/views/ListMember.vue'
import CreateMember from '@/views/CreateMember.vue'
import EditMember from '@/views/EditMember.vue'


const routes = [
  { path: '/', name:'home', component: ListMember },
  { path: '/view', name:'view', component: ListMember },
  { path: '/create', name : 'create' , component: CreateMember },
  { path: '/edit/:id', name : 'edit' , component: EditMember },

]

const router = createRouter({
    history:createWebHistory(),
    routes

})

export default router;
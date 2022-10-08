import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import UserView from '../views/UserView.vue'
import AboutView from '../views/AboutView.vue'
import AdminView from '../views/AdminView.vue'
import ModeratorView from '../views/ModeratorView.vue'
import SignUpView from '../views/SignUpView.vue'
import SignUpSuccessView from '../views/SignUpSuccessView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {path: '/login',name: 'login',component: LoginView},
    {path: '/profile',name: 'profile',component: UserView},
    {path: '/about',name: 'about',component: AboutView},
    {path: '/admin',name: 'admin',component: AdminView},
    {path: '/mod',name: 'mod',component: ModeratorView},
    {path: '/signup',name: 'signup',component: SignUpView},
    {path: '/signupsuccess',name: 'signupsuccess',component: SignUpSuccessView}
    
    

  ]

  
})

// router gurads
router.beforeEach((to, from, next) => {
  const jwt = localStorage.getItem('jwtToken')

  if (to.name !== 'login' && to.name !== 'home' 
      && to.name !=='about' &&  to.name !=='signup' 
      && to.name != 'signupsuccess' && !jwt) {
    next({ name: 'login' })
  }else{
    next()
  } 



})


export default router

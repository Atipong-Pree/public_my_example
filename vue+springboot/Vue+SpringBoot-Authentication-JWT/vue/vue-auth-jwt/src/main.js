import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import './assets/main.css'
// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import axios from 'axios'

//set api url base
//axios.defaults.baseURL = 'http://localhost:5000'
axios.defaults.baseURL = 'http://springbootjwtauthentication-env.eba-wrpyhiqe.us-east-1.elasticbeanstalk.com'


const pinia = createPinia()
const app = createApp(App)

app.use(router)
app.use(pinia)
app.mount('#app')


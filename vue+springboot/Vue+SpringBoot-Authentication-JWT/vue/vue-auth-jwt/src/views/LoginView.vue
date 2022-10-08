<template>
  
  <main class="form-signin w-100 m-auto text-center">
  <form @submit.prevent="signIn" >
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingInput" placeholder="name@example.com" v-model="c_username" required >
      <label for="floatingInput">Username</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" v-model="c_password" required>
      <label for="floatingPassword">Password</label>
    </div>
    <div  class="alert alert-danger" role="alert" v-show="isError">
      {{msgError}}
    </div>

    <!-- <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me"> Remember me
      </label>
    </div> -->

    <button class="w-100 btn btn-lg btn-primary" type="submit" >Sign in</button>
 
  </form>
</main>

</template>

<script setup>
import {onMounted, ref} from 'vue'
import axios from 'axios'
import router from '../router';
import { useCounterStore } from '@/stores/counter'

const storeCounter = useCounterStore();

  //reactive state
  const c_username =  ref('')
  const c_password = ref('')
  const c_API_URL = axios.defaults.baseURL;
  const isError = ref(false)
  const msgError = ref('')

  // functions state
  function signIn(){
   
    const userCredential = JSON.stringify({username : c_username.value ,password : c_password.value})

    axios.post(c_API_URL+'/auth',userCredential)
    .then( (response) => {
      //console.log(response)
      console.log("Authoriztion :"+response.headers['authorization'])
      let accessToken = response.headers['authorization']
      
      if(response.status === 200){
        localStorage.jwtToken = accessToken
        router.push({name:'profile'})
      }
    
    })
    .catch( (error) => {
        console.log(error.response)
        const errorResponse = error.response;

        isError.value = true
        msgError.value = 'Username or Password is invalid'//errorResponse.data.body.message

    })
 
    

  }

  // lifecycle hooks
  onMounted(() => {
  console.log('The initial count is')
  //setInterval(()=>{storeCounter.increment()},100)
})

</script>

<style>
  body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
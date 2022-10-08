<script setup>

</script>

<template>



 <main class="form-signin w-100 m-auto"  >
  <form v-on:submit.prevent="handleSignup"  class="needs-validation" >
      <h1 class="h3 mb-3 fw-normal ">Please sign up</h1>
      <div class="mb-3">
        <label   class="form-label">E-Mail</label>
        <input   type="email" class="form-control" placeholder="e-mail"  v-model="user.email" required >
      </div>
      <div class="mb-3">
        <label  class="form-label">Username</label>
        <input type="text" class="form-control"  placeholder="username" required v-model="user.username">
      </div>
      <div class="mb-3">
        <label  class="form-label">Password</label>
        <input type="password" class="form-control"  placeholder="password" required v-model="user.password">
      </div>
      <div class="mb-3">
        <button type="submit" class="w-100 btn-lg btn btn-primary">Sign up</button>
      </div>
      <div  class="alert alert-danger" role="alert" v-show="isError">
      {{errorMsg}}
    </div>
  </form>
  </main> 



</template>

<script>
import axios from 'axios';
import { ref } from 'vue';




export default {

  data(){
    return{
      user:{
        email:'',
        username:'',
        password:'',
        role:''
      },
      isError:false,
      errorMsg:''
    }
  },
  methods:{
    handleSignup(){
      
        const data = JSON.stringify(this.user)
        let API_URL = axios.defaults.baseURL+'/api/signup'
        axios.post(API_URL,data,{headers:{'Content-Type':'application/json'}})
        .then( (response)=>{
          console.log(response);
          if(response.status === 200){
            this.$router.push({name:'signupsuccess'});
          }
        })
        .catch( (error)=>{
          console.log(error);
          const errorResponse = error.response;

          this.isError = true;
          this.errorMsg = ( errorResponse.data.message || errorResponse.data.body.message 
                              || errorResponse.message ||errorResponse.message );
          
        })

    }

  },
  mounted(){
    // do somthing
  }




}

</script>





<template>
    <div class="container-sm text-break w-50" v-if="objUser.id">
        <div class="navbar-brand navbar-dark bg-primary text-center justify-content-center p-1"><h4>Profile</h4></div>
        <div class="row pt-3">
            <div class="col-4"><label>ID :</label></div>
            <div class="col-8"><span>{{objUser.id}}</span></div>
        </div>
        <div class="row">
            <div class="col-4"><label>username  :</label></div>
            <div class="col-8"><span>{{objUser.username}}</span></div>
        </div>
        <div class="row">
            <div class="col-4"><label>E-Mail :</label></div>
            <div class="col-8"><span>{{objUser.email}}</span></div>
        </div>
        <div class="row">
            <div class="col-4"><label>Token :</label></div>
            <div class="col-8"><label>{{objUser.token}}</label></div>
        </div>
        <div class="row">
            <div class="col-4"><label>Roles :</label></div>
            <div class="col-8" ><span v-for="(value,index) in objUser.roles" :key="index" >{{value}}&nbsp;</span></div>
        </div>
   
    </div>
  
</template>

<script setup>
    import { ref,onMounted, reactive, onBeforeMount } from 'vue';
    import axios from 'axios';
    import { useUserStroe } from '@/stores/UserStore'

    const userStore = useUserStroe();

    //reactive state
    const objUser = reactive({
        id:'',
        username:'',
        email:'',
        token:'',
        roles : []
    })
    
    let API_URL = axios.defaults.baseURL;
    
    // function state
    function getUserDetail(){

        let accessToken = localStorage.getItem('jwtToken')

        axios.get(API_URL+'/api/user',{headers:{'Authorization':accessToken}})

        .then( (response) => {

            console.log(response.data)
            const resData = response.data

            objUser.id = resData.id
            objUser.username = resData.username
            objUser.email = resData.email
            objUser.token = resData.accessToken
            objUser.roles = resData.roles   


            localStorage.setItem('user',JSON.stringify(resData))  
            const userData = JSON.parse(localStorage.getItem('user'))
            console.log("userData : "+userData )
          
            userStore.setUser(userData)
 
            //console.log("userData : "+userData.roles.includes('ROLE_ADMIN') )
                 
        })
        .catch( (error) => {
            console.log(error)
        })
    }

    // life cycle hook
    onMounted(() =>{
        console.log("user view")
        getUserDetail();      

    })

</script>

<style>

</style>
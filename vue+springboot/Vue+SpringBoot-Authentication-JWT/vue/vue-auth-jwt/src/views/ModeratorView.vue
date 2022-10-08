<template>
  <h1>This is a moderater page</h1>
  <h1>{{showData}}</h1>
</template>

<script setup>
import axios from 'axios';
import {ref, onMounted } from 'vue';

const showData = ref('');

  let API_URL = axios.defaults.baseURL;
  let accessToken = localStorage.getItem('jwtToken')

  axios.get(API_URL+'/api/mod',{
    headers:{'Authorization':accessToken}
  }).then( (response) => {

    console.log(response.data);
    showData.value = response.data;

  }).catch((error)=>{
    console.log(error);
    showData.value = error.response.data.body.message;
  })

</script>

<style>

</style>
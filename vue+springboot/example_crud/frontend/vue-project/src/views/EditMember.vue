<template>
    <div class="container-fluid">  
        <form @submit.prevent="handlerUpdate">
            <h1>Edit Member</h1>
            <div class="row g-3">
            <div class="col-12 ">
                <label class="form-label">First Name</label>
                <input type="hidden" v-model="member.id" />
                <input type="input" class="form-control" placeholder="Enter First Name" v-model="member.firstName" required />
            </div>
            <div class="col-12">
                <label class="form-label">Last Name</label>
                <input type="input" class="form-control" v-model="member.lastName" placeholder="Enter Last Name" />
            </div>
            <div class="col-12">
                <label class="form-label">E-Mail</label>
                <input type="input" class="form-control " v-model="member.email" placeholder="Enter E-Mail" required/>
            </div>
            <div class="col-12">
                <label class="from-label">Phone Number</label>
                <input type="input" class="form-control" v-model="member.phoneNumber" placeholder="Enter Phone Number" />
            </div>
            <div class="pt-3 d-grid">
                <button type="submit" class="btn btn-success btn-block" >Update</button>
                <router-link to="/" class="btn btn-link" >Back to List</router-link>
            </div>
           
            
            
            <div v-if="isUpdateSuccess" class="alert alert-success alert-dismissible fade show" >
                <strong>{{msgNotify}}</strong>
            </div> 
        </div>
        </form>
        
    </div>

 
</template>


<script>

import axios from 'axios';
import config_api from '@/global/config'


export default {

    data(){
        return{
            isUpdateSuccess : false,
            msgNotify :'',    
            member:{},
        }
    },
    mounted(){

        let apiURL = config_api.HOST_NAME_API+'/api/member';
        axios
        .get(apiURL,{
            params:{id:this.$route.params.id}
        })
        .then((response) => {
            //alert(response.data.firstName)
            console.log(response);
            this.member = response.data;
        })
        .catch((getError)=>{
            console.log(getError);
        })
    },
    methods : {
        handlerUpdate(){
            axios
            .put(config_api.HOST_NAME_API+'/api/update',this.member)
            .then((response)=>{
               
                console.log(response);
                this.msgNotify = 'Update was successful'
                this.isUpdateSuccess = true;
                
            })
            .catch((error)=>{
                console.log(error);
            })
        }
    }
  
};
</script>
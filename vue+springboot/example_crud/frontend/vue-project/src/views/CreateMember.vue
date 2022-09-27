<template>
        <div class="p-2">
        <form  @submit.prevent="handleCreate">
            <h1>Create Member</h1>
            <div class="row g-3">
            <div class="col-12">
                <label   class="form-label">First Name</label>
                <input type="text" class="form-control" placeholder="Enter First Name" v-model="member.firstName" required/>
            </div>  

            <div class="col-12">
                <label class="form-label">Last Name</label>
                <input type="input" class="form-control" v-model="member.lastName" placeholder="Enter Last Name" />
            </div>

            <div class="col-12">
                <label class="form-label">E-Mail</label>
                <input type="input" class="form-control " v-model="member.email" placeholder="Enter E-Mail"  required />
            </div>
            <div class="col-12">
                <label class="form-label">Phone Number</label>
                <input type="input" class="form-control" v-model="member.phoneNumber" placeholder="Enter Phone Number" />
            </div>
            <div class="pt-3 d-grid">
                <button type="submit" class="btn btn-primary btn-block" >Create</button>
                <router-link to="/" class="btn btn-link" >Back to List</router-link>
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
            member:{
                firstName : '',
                lastName : '',
                email : '',
                phoneNumber : ''
            }
        }
    },
    methods : {
        handleCreate(){
            let apiURL = config_api.HOST_NAME_API+'/api/create'
            //alert(this.member)

            axios
            .post(apiURL,this.member)
            .then(()=>{
                this.$router.push('/'); 
                //this.member={};          
            })
            .catch((error)=>{
                
                console.log(error);
            })
        },
        onRedirect(){
            this.$router.push({path : '/',replace:true})
        }
    }
  
};

</script>

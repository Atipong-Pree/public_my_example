<template>
    <div class="row">
        <div class="container d-flex justify-content-center">
            <h1>Member List</h1>
        </div> 
            <div class="d-flex justify-content-end">
                <input type="input" v-model="textSearch"  @keyup="handleSearch"  placeholder="Search" />
                <div class="link-light bg-primary" href="#" aria-label="Search">
                    <svg xmlns="http://www.w3.or bt-infog/2000/svg" width="20" height="20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="mx-3" role="img" viewBox="0 0 24 24"><title>Search</title><circle cx="10.5" cy="10.5" r="7.5"/><path d="M21 21l-5.2-5.2"/></svg>
                </div>
            </div>
    </div>


    <div class="table-responsive pt-3">      
        <table class="table table-striped table-bordered" >
            <thead class="table-primary">
                <tr>
                    <th>No.</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>E-Mail</th>
                    <th>Phone </th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr  v-for="(member,index) in memberList" v-bind:key="member.id">
                    <td>{{(index+1)+(this.pageSize*this.pageNum)}}</td>
                    <td>{{member.firstName}}</td>
                    <td>{{member.lastName}}</td>
                    <td>{{member.email}}</td>
                    <td>{{member.phoneNumber}}</td>
                    <td>
                        <router-link  :to="{name:'edit',params:{id : member.id}}"  class="btn btn-info">Edit</router-link>
                        <span>&nbsp;</span>
                        <button class="btn btn-danger" @click.prevent="handlerDelete(member.id)" >Del</button>
                    </td>
                </tr>
                <tr v-if="memberList == '' "><td colspan="6" class="text-center">No data</td></tr>
            </tbody>
        </table>

    </div>
        
    <div class="row">

        <!-- Pagination -->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-end">
                <li class="page-item" @click="handlePagination(this.pageNum-1)" >
                    <a class="page-link" href="#" tabindex="-1" aria-disabled="true" v-bind:class=" (this.pageNum == 0)? 'disabled':''" >Previous</a>
                </li>
           
                <li v-for="(page) in totalPage" v-bind:key="page"  class="page-item" @click.prevent="handlePagination(page-1)"><a class="page-link" v-bind:class=" (page == (this.pageNum+1))?'active':'' "  href="#" >{{page}}</a></li>

                <li class="page-item" @click="handlePagination(this.pageNum+1)">
                    <a class="page-link" v-bind:class="((this.pageNum+1) >= this.totalPage)? 'disabled':''" href="#">Next</a>
                </li>
            </ul>
        </nav>
        
    </div>


</template>

<script >
import axios from 'axios'
import { ref } from 'vue';
import config_api from '@/global/config'



export default {

   data(){
    return {
      memberList:null,
      textSearch:'',
      pageNum : 0 ,
      pageSize : 5,
      totalPage : 0,
      totalRow : 0
    }
  },
   mounted(){
  
     let apiURL = config_api.HOST_NAME_API+'/api/pagination?offset='+this.pageNum+'&pageSize='+this.pageSize;
    axios
        .get(apiURL)
        .then((response) => {
            //alert(response.data.totalPages);
            this.memberList = response.data.content;
            this.totalPage = response.data.totalPages;
            this.totalRow = response.data.totalElements;
            this.pageNum = response.data.pageable.pageNumber;
            
            
        })
        .catch((error)=>{
            console.log(error);
        })

   },
   methods : {
        handlerDelete(_id){

            if(window.confirm("Would you like to delete this data?")){
                let apiURL = config_api.HOST_NAME_API+'/api/delete';
                axios
                    .get(apiURL,{
                        params:{id:_id}
                    })
                    .then((resSuccess)=>{ 
                        console.log(resSuccess);
        
                         //let apiURL = 'http://localhost:8080/api/members';
                        let apiURL = config_api.HOST_NAME_API+'/api/pagination?offset=0&pageSize='+this.pageSize;
                         axios
                            .get(apiURL)
                            .then((response) => {
                              
                                this.totalPage = response.data.totalPages;
                                this.totalRow = response.data.totalElements;
                                this.pageNum = response.data.pageable.pageNumber;
                                this.memberList = response.data.content;
                            })  
                        
                    })
                    .catch((error)=> {
                        console.log(error);
                    })
            }    

        },
        handleSearch(){

          
            let apiURL = config_api.HOST_NAME_API+'/api/searchpaging?keyword='+this.textSearch+'&offset=0&pageSize='+this.pageSize

            axios
                
                .get(apiURL)
                .then((response)=>{
                    console.log(response);
                    this.memberList = response.data.content
                    this.totalPage = response.data.totalPages;
                    this.totalRow = response.data.totalElements;
                    this.pageNum = response.data.pageable.pageNumber;
                   
                })
                .catch((error)=>{console.log(error);})
            
        },
        handlePagination(_pageNum){

            if(_pageNum == this.totalPage){_pageNum = (this.totalPage-1)
            }if(_pageNum < 0){
                pageNum = 0;
            }

            let pagURL = config_api.HOST_NAME_API+'/api/searchpaging?keyword='+this.textSearch+'&offset='+_pageNum+'&pageSize='+this.pageSize
            axios
                
                .get(pagURL)
                .then((response)=>{

                    this.memberList = response.data.content;
                    this.totalPage = response.data.totalPages;
                    this.totalRow = response.data.totalElements;
                    this.pageCurrent = response.data.pageNumber; 
                    this.pageNum = response.data.pageable.pageNumber;
                 
                }).catch((e)=>{console.log(e.error)})
        }

   }

    
}


</script>

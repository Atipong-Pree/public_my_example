import { defineStore} from "pinia";


export const useUserStroe = defineStore({

    // id
    id:'user',
    // state
    state: () =>({
        user:null,
    }),
    // actions
    actions:{
        setUser(user){
            this.user = user;
        }

    },
    // getters
    getters:{

        getUser(){
            return   this.user!=null?this.user:{};
        },
        getUserName(){
           
            return this.user!=null?this.user.username:'';
        },
        getRoles(){
            return this.user!=null?this.user.roles:[];
        },
        isAdmin(){
            return this.user!=null?this.user.roles.includes('ROLE_ADMIN'):false;
          
        },
        isModerator(){
            return  this.user!=null?this.user.roles.includes('ROLE_MODERATOR'):false;
        },
        isLogIn(){
            return this.user!=null?true:false;
        }
        


    }


})
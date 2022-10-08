import { defineStore } from "pinia";

export const useCounterStore = defineStore({
    // id
    id :'counter', 
    // state
    state: () => ({ 
        count: 0
    }),
    //acction
    actions:{
        increment(){
            this.count++;
        },
    },
    // getters
    
  })
import authHeader from './auth-header';
import axios from 'axios';

const API_URL = 'http://localhost:5000/auth';

function logIn(user){
    
    const userCredential = JSON.stringify({username :user.username ,password :user.password});

    axios.post(API_URL+'/auth',userCredential)
    .then( (response) => {
      //console.log(response)
      console.log("Authoriztion :"+response.headers['authorization']);
      let accessToken = response.headers['authorization'];
      
      if(response.status === 200){
        localStorage.setItem('user');
        router.push({name:'profile'});
        
      }

      return response.data;
    
    })
    .catch( (error) => {
        console.log(error.response);
        const errorResponse = error.response;

        //isError.value = true;
       //msgError.value = errorResponse.data.body.message;

    })
}

function logOut(){
    localStorage.removeItem('user');
}

export const AuthService = { logIn , logOut };
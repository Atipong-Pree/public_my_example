import axios from "axios";

const HELLOWORLD_URL = "http://localhost:8080/helloworld"

class HelloWorldService{

    getHelloWorld(){

        return axios.get(HELLOWORLD_URL);
    }

}

export default new HelloWorldService()



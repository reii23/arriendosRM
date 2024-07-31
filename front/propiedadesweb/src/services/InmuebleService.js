import axios from 'axios';

const API_URL = "http://localhost:8080/inmuebles";

class InmuebleService {
  getAll() {
    return axios.get(API_URL);
  }
}

export default InmuebleService;


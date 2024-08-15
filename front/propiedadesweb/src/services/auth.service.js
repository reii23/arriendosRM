// src/services/auth.service.js
// API para autenticación de usuarios en el backend
// Se encarga de enviar las credenciales al backend y almacenar el token de autenticación en el local storage del navegador
// También se encarga de recuperar el token de autenticación del local storage y verificar si el usuario está autenticado
// Se exporta una instancia de la clase AuthService para ser utilizada en otros componentes

import axios from 'axios';

const AUTH_API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
  login(usuario, password) {
    return axios
      .post(AUTH_API_URL + 'signin', {
        usuario,
        password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(usuario, email, password) {
    return axios.post(AUTH_API_URL + 'signup', {
      usuario,
      email,
      password
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));
  }

  isAuthenticated() {
    const user = this.getCurrentUser();
    return !!user && !!user.accessToken;
  }
}

export default new AuthService();
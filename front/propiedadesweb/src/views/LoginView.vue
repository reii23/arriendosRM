<template>
  <div class="login-container">
    <h2>Iniciar Sesión</h2>
    <form @submit.prevent="loginUser">
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="user.email" required>
      </div>
      <div class="form-group">
        <label for="password">Contraseña</label>
        <input type="password" id="password" v-model="user.password" required>
      </div>
      <button type="submit">Iniciar Sesión</button>
    </form>
    <p v-if="message" :class="messageClass">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { auth } from "@/auth";

export default {
  name: 'Login',
  data() {
    return {
      user: {
        email: "",
        password: ""
      },
      message: "",
      messageClass: ""
    }
  },
  methods: {
    async loginUser(){
      try {
        const respuesta = await axios.post('http://localhost:8080/user/login', this.user);
        const respuesta2 = await axios.get(`http://localhost:8080/user/obtenerUsuarioPorEmail/${this.user.email}`);
        auth.login(respuesta2.data.id, respuesta2.data.rol);
        this.message = "Has iniciado sesión correctamente";
        this.messageClass = "success-message";

        this.$router.push('/');
      } catch (error){
        this.message = "Error al iniciar sesión. Verifica los datos ingresados";
        this.messageClass = "error-message";
        console.error('Error al iniciar sesión:', error);
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  max-width: 350px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input{
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;

}
button{
  width: 100%;
  padding: 10px;
  background-color: rgb(255,146,2);
  color: rgb(236, 236, 236);
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;

}

button:hover{
  background-color: rgb(8, 8, 8);
}
button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
.success-message {
  color: green;
  font-weight: bold;
}
.error-message {
  color: red;
  font-weight: bold;
}

</style>

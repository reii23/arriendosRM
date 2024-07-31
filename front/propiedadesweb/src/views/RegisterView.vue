<template>
    <div class="register-container">
      <h2>Registro de Usuario</h2>
      <form @submit.prevent="registerUser">
        <div class="form-group">
          <label for="nombre">Nombre de usuario</label>
          <input type="text" id="nombre" v-model="user.nombre" required>
        </div>
        <div class="form-group">
          <label for="email">Emai:</label>
          <input type="email" id="email" v-model="user.email" required>
        </div>
        <div class="form-group">
          <label for="password">Contraseña</label>
          <input type="password" id="password" v-model="user.password" required>
        </div>
        <button type="submit">Registrar</button>
      </form>
      <p v-if="message" :class="{ 'success-message': isSuccess, 'error-message': !isSuccess }">
        {{ message }}
      </p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'Register',
    data() {
      return {
        user: {
          nombre: '',
          email: '',
          password: ''
        },
        message: '',
        isSuccess: false
      }
    },
    methods: {
      async registerUser() {
        try {
          const response = await axios.post('http://localhost:8080/user/crearUsuario', this.user);
          console.log('Usuario registrado:', response.data);
          this.message = 'Usuario registrado exitosamente';
          this.isSuccess = true;
          // Aquí puedes redirigir al usuario después de un breve retraso
          setTimeout(() => this.$router.push('/login'), 2000);
        } catch (error) {
          console.error('Error al registrar usuario:', error);
          this.message = 'Error al registrar usuario. Intentalo de nuevo';
          this.isSuccess = false;
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .register-container {
    max-width: 350px;
    margin: 0 auto;
    padding: 20px;
  }
  .form-group {
    margin-bottom: 15px;
  }
  label {
    display: block;
    margin-bottom: 5px;
  }
  input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  button {
    width: 100%;
    padding: 10px;
    background-color: rgb(255,146,2);
    color: rgb(236, 236, 236);
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  button:hover {
    background-color: rgb(8, 8, 8);
  }
  </style>
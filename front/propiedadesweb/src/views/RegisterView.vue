<template>
    <div class="register-container">
      <h2 class="titulo-container">Registro de Usuario</h2>
      <form @submit.prevent="registerUser">
        <div class="form-group">
          <label for="nombre">Nombre de usuario</label>
          <input type="text" id="nombre" v-model="user.nombre" required>
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" v-model="user.email" required>
        </div>
        <div class = "form-group">
          <label for="numeroTelefono">Numero de Telefono</label>
          <input type="text" id="numeroTelefono" v-model="user.numeroTelefono" required>
        </div>
        <div class="form-group">
          <label for="rol">Rol</label>
          <select id="rol" v-model="user.rol">
            <option value="0">Administrador</option>
            <option value="1">Usuario</option>
            <option value="2">Agente Inmobiliario</option>
          </select>
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
          password: '',
          numeroTelefono: '',
          rol: 1 // por defecto el usuario comprador o arrendador (usuario normal)
        },
        message: '',
        isSuccess: false,
        isSubmitting: false
      }
    },
    methods: {
      async registerUser() {
        this.isSubmitting = true;
        try {
          const response = await axios.post('http://localhost:8080/user/crearUsuario', this.user);
          console.log('Usuario registrado:', response.data);
          this.message = 'Usuario registrado exitosamente';
          this.isSuccess = true;
          setTimeout(() => this.$router.push('/login'), 2000);
        } catch (error) {
          console.error('Error al registrar usuario:', error);
          this.message = 'Error al registrar usuario. Intentalo de nuevo';
          this.isSuccess = false;
        } finally {
          this.isSubmitting = false;
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
    border-radius: 8px;
  }

  .titulo-container {
    text-align: center;
    color: rgb(255, 255, 255);
  }
  .form-group {
    margin-bottom: 15px;
    color:rgb(255, 250, 250);
  }
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  input, select {
    width: 100%;
    padding: 8px;
    border: 1px solid #070707;
    border-radius: 4px;
    font-size: 16px;
  }
  button {
    width: 100%;
    padding: 10px;
    background-color: rgb(255,146,2);
    color: rgb(236, 236, 236);
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
  }
  button:hover {
    background-color: rgb(8, 8, 8);
  }

  button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.succes-message {
  color: green;
}

.error-message {
  color: red;
}

  </style>
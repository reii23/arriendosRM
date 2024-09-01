<template>
  <div class="login-container">
    <h2>Iniciar Sesión</h2>
    <form @submit.prevent="loginUser">
      <div class="formulario">
        <label for="email">Correo electrónico</label>
        <input type="email" id="email" v-model="user.email" required>
      </div>
      <div class="formulario">
        <label for="password">Contraseña</label>
        <input type="password" id="password" v-model="user.password" required>
      </div>
      <button type="submit">Iniciar Sesión</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import { auth } from "@/auth";

export default {
  nombre: 'Login',
  data() {
    return {
      user: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    async loginUser() {
      try {
        const response = await axios.post('http://localhost:8080/user/login', this.user);
        const response2 = await axios.get(`http://localhost:8080/user/obtenerUsuarioPorEmail/${this.user.email}`);
        console.log(response2.data.id); // Mostrar el ID del usuario en la consola
        auth.login(response2.data.id, response2.data.rol); // Iniciar sesión con el ID y el rol del usuario
        this.$router.push('/'); // Redirigir al usuario a la página de inicio
      } catch (error) {
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
}

.formulario {
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
  background-color: rgb(255, 146, 2);
  color: rgb(236, 236, 236);
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: rgb(8, 8, 8);
}
</style>
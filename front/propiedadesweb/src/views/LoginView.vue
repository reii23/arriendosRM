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
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Login',
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
        // Suponiendo que la autenticación fue exitosa, se establece la variable booleana en true
        localStorage.setItem('isLoggedIn', 'true');
        console.log('Usuario logueado correctamente:', response.data);
        this.$router.push('/');
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
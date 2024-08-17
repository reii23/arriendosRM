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
      <button type="submit":disabled="isSubmitting">
        {{ isSubmitting ? 'Iniciando sesión...' : 'Iniciar Sesión' }}
      </button>
    </form>
    <p v-if="message":class="{ 'success-message': isSuccess, 'error-message': !isSuccess }">
      {{ message }}
    </p>
  </div>
</template>

<script>
import axios from "axios";
import {auth} from "@/auth";

export default {
  name: "Login",
  data() {
    return {
      user: {
        email: "",
        password: ""
      },
      isSubmitting: false,
      message: "",
      isSuccess: false
    }
  },


  methods: {
    async loginUser() {
      this.isSubmitting = true;
      this.message = "";
      try {
        const loginResponse = await axios.post("http://localhost:8080/user/login", this.user);
        
        if (loginResponse.data && loginResponse.data.success) {
          const userResponse = await axios.get(`http://localhost:8080/user/obtenerUsuarioPorEmail/${this.user.email}`);
          if (userResponse.data) {
            auth.login(userResponse.data.id, userResponse.data.rol);
            this.message = "Has iniciado sesión correctamente";
            this.isSuccess = true;
            setTimeout(() => this.$router.push("/"), 1500);
          } else {
            throw new Error("No se pudo obtener la información del usuario");
          }

        } else{
          throw new Error("Credenciales ingresadas inválidas");
        }
        
      } catch (error){
        console.error("Error al iniciar sesion:", error);
        this.message = error.response?.data?.message || "Error al iniciar sesión. Inténtalo de nuevo";
        this.isSuccess = false;
      } finally{
        this.isSubmitting = false;
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
}
.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
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

button:hover {
  background-color: rgb(8, 8, 8);
}

.success-message{
  color: green;
  font-weight: bold;
}
.error-message{
  color: red;
  font-weight: bold;
}

</style>
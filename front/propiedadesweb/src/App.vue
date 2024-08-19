<template>
  <div id="app">
    <nav>
      <router-link to="/">Inicio</router-link> |
      <router-link to="/about">Subsidios</router-link> |
      <router-link to="/inmuebles">Inmuebles</router-link>
      <span v-if="!auth.isLoggedIn"> | </span>
      <!-- Mostrar Login y Register solo si no está logueado -->
      <router-link v-if="!auth.isLoggedIn" to="/login">Iniciar sesión</router-link>
      <span v-if="!auth.isLoggedIn"> | </span>
      <router-link v-if="!auth.isLoggedIn" to="/register">Crear cuenta</router-link>
      <!-- Mostrar Logout solo si está logueado -->
      <span v-if="auth.isLoggedIn"> | </span>
      <router-link v-if="auth.isLoggedIn" to="/logout">Cerrar sesión</router-link>
	  <span v-if="auth.isLoggedIn"> | </span>
	  <router-link v-if="auth.isLoggedIn" to="/account">Mi Cuenta</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
import { auth } from '@/auth';

export default {
  nombre: "App",
  setup() {
    return {
      auth
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #fffafa;
  background-color: rgb(2,43,96);
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #ffffff;
}

nav a.router-link-exact-active {
  color: rgb(255,146,2);
}
</style>

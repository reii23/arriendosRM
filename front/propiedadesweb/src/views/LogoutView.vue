<template>
  <div>
    <p>Sesión cerrada exitosamente.</p>
  </div>
</template>

<script>
export default {
  name: "LogoutView",

  created() {
    this.logoutUser();
  },

  methods: {
    logoutUser() {
      // Establecer la variable isLoggedIn en false al cerrar sesión
      localStorage.setItem('isLoggedIn', 'false');
      // Redirigir al login
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
</style>

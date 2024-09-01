<template>
  <div>
    <p>Sesión cerrada exitosamente.</p>
  </div>
</template>

<script>
import { auth } from "@/auth";

export default {
  name: "LogoutView",
  created() {
    auth.logout(); // Actualiza el estado reactivo
    this.$router.push('/login');
  }
}
</script>

<style scoped></style>

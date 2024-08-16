<template>
  <div>
    <p>Sesión cerrada exitosamente.</p>
  </div>
</template>

<script>
import AuthService from "@/services/auth.service";
import { useRouter } from "vue-router";

export default {
  name: "LogoutView",
  setup() {
    const router = useRouter();

    // Llamar al método de logout cuando el componente se monte
    AuthService.logout();
    router.push("/login");

    return {};
  },
};
</script>

<style scoped>
</style>

<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/residenciasRM.png" />
    <Componente msg="Bienvenido a residenciasRM" />
    <button @click="publicarInmueble" class="btn-publicar">Publicar Inmueble</button>
  </div>
</template>

<script>
import Componente from "@/components/Componente.vue";
import AuthService from '@/services/auth.service';
import { useRouter } from 'vue-router';

export default {
  name: "HomeView",
  components: {
    Componente,
  },
  setup() {
    const router = useRouter();

    const publicarInmueble = () => {
      if (AuthService.isAuthenticated()) {
        router.push('/seleccionar-inmueble');
      } else {
        router.push('/login');
      }
    };

    return { publicarInmueble };
  }
};
</script>

<style scoped>
.btn-publicar {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
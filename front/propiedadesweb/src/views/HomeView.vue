<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/residenciasRM.png" />
    <Componente msg="Bienvenido a residenciasRM" />
    <!-- Utilizar la función handleCreatePublicationClick en el botón -->
    <button @click="handleCreatePublicationClick" class="btn-publicar">Publicar Inmueble</button>
  </div>
</template>

<script>
import Componente from "@/components/Componente.vue";

export default {
  name: "HomeView",
  components: {
    Componente,
  },
  methods: {
    handleCreatePublicationClick() {
      const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
      if (isLoggedIn) {
        this.$router.push('/seleccionar-inmueble');
      } else {
        this.$router.push('/login');
      }
    }
  }
}
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

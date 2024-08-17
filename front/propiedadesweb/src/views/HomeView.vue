<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/residenciasRM.png" />

    <div v-if="auth.isLoggedIn">
      <p>Usuario: {{ auth.id }}</p>
      <p>Rol: {{ auth.rol }}</p>
    </div>
    <Componente msg="Bienvenido a residenciasRM" />

    <!-- Botón para agregar horarios disponibles (solo para rol 2 y 0) -->
    <button v-if="auth.rol == 2 || auth.rol == 0" @click="handleAddScheduleClick" class="btn-horarios">Agregar Horarios Disponibles</button>

    <!-- Botón para publicar inmueble (para rol 1, 0 y usuarios no logueados) -->
    <button v-if="auth.rol == 1 || auth.rol == 0 || !auth.isLoggedIn" @click="handleCreatePublicationClick" class="btn-publicar">Publicar Inmueble</button>
  </div>
</template>

<script>
import Componente from "@/components/Componente.vue";
import { auth } from "@/auth";

export default {
  name: "HomeView",
  components: {
    Componente,
  },
  data() {
    return {
      auth // Hacemos que el store de autenticación esté disponible en el template
    };
  },
  methods: {
    handleCreatePublicationClick() {
      if (this.auth.isLoggedIn) {
        this.$router.push('/seleccionar-inmueble');
      } else {
        this.$router.push('/login');
      }
    },
    handleAddScheduleClick() {
      this.$router.push('/agente');
    }
  }
}
</script>

<style scoped>
.btn-publicar, .btn-horarios {
  margin-top: 10px;
  padding: 12px 60px;
  font-size: 18px;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.btn-publicar {
  background-color: #4CAF50;
}

.btn-publicar:hover {
  background-color: #45a049;
}

.btn-horarios {
  background-color: #ff6200;
}

.btn-horarios:hover {
  background-color: #FB8C00;
}

.btn-publicar:active, .btn-horarios:active {
  transform: scale(0.98);
}

</style>

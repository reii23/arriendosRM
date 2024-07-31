<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/residenciasRM.png" />
    <Componente msg="Bienvenido a residenciasRM" />
  </div>
</template>

<script>
import Componente from "@/components/Componente.vue";

export default {
  name: "HomeView",
  components: {
    Componente,
  },
};
</script>

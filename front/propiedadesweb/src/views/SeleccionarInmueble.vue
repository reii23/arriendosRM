<template>
    <div class="selector-inmueble">
      <h2>¿Qué tipo de propiedad quieres publicar?</h2>
      <div class="botones">
        <button @click="navigateTo('/seleccionar-datos-c')" class="btn-casa">Casa</button>
        <button @click="navigateTo('/seleccionar-datos-d')" class="btn-departamento">Departamento</button>
        <button @click="navigateTo('/seleccionar-datos-t')" class="btn-terreno">Terreno</button>
      </div>
    </div>
  </template>
  
  <script>
  import { useRouter } from 'vue-router';
  
  export default {
    name: 'SeleccionarInmueble',
    setup() {
      const router = useRouter();
      const navigateTo = (path) => {
        router.push(path);
      };
      return { navigateTo };
    },
  };
  </script>
  
  <style scoped>
  .selector-inmueble {
    background-color: #022b60;
    color: #e0eeff;
    padding: 60px;
    border-radius: 10px;
    max-width: 500px;
    margin: 60px auto;
    text-align: center;
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 40px;
    font-weight: 600;
  }
  
  .botones {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  button {
    font-size: 18px;
    padding: 15px 30px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
  }
  
  .btn-casa {
    background-color: #f7c545;
  }
  
  .btn-departamento {
    background-color: #85c1e9;
  }
  
  .btn-terreno {
    background-color: #58d68d;
  }
  </style>
  
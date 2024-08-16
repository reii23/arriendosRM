<template>
    <div class="selector-inmueble">
      <h2>¿Qué tipo de propiedad quieres publicar?</h2>
      <div class="button-group">
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
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 40px;
    font-weight: 600;
  }
  
  .button-group {
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
  
  button:hover {
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  }
  
  .btn-casa {
    background-color: #f7c545;
    color: #022b60;
  }
  
  .btn-departamento {
    background-color: #85c1e9;
    color: #022b60;
  }
  
  .btn-terreno {
    background-color: #58d68d;
    color: #022b60;
  }
  </style>
  
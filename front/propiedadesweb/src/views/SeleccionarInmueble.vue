<template>
    <div class="selector-inmueble">
      <h2>¿Qué tipo de propiedad desea crear?</h2>
      <button @click="navigateTo('/seleccionar-datos-c')" class="btn-casa">Casa</button>
      <button @click="navigateTo('/seleccionar-datos-d')" class="btn-departamento">Departamento</button>
      <button @click="navigateTo('/seleccionar-datos-t')" class="btn-terreno">Terreno</button>
    </div>
  </template>

<script>
import axios from 'axios';
import {ref} from 'vue';
import {useRouter} from 'vue-router';
export default {
    name: 'SeleccionarInmueble',
    setup() {
        const router = useRouter();
        const navigateTo = (path) => {
            router.push(path);
        }
        return {navigateTo};
    }
}
</script>

<style scoped>
.selector-inmueble {
    background-color: rgb(2,43,96);
    color: #e0eeff;
    padding: 100px;
    border-radius: 8px;
}
.btn-casa, .btn-departamento, .btn-terreno {
    font-size: 15px;
    margin-top: 30px;
    margin-bottom: 60px;
    margin-left: 10px;
    margin-right: 10px;
    padding: 20px 30px;
    background-color: #bfc845;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.btn-departamento {
    background-color: #85c1e9;
}

.btn-terreno {
    background-color: #58d68d;
}
</style>

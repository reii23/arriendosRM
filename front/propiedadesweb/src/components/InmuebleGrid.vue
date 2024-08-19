<template>
    <div class="inmuebles-grid">
      <InmuebleCard v-for="inmueble in inmuebles" :key="inmueble.id" :inmueble="inmueble" />
    </div>
  </template>
  
  <script>
  import InmuebleCard from './InmuebleCard.vue';
  
  export default {
    name: 'InmuebleGrid',
    components: {
      InmuebleCard
    },
    props: {
      inmuebles: {
        type: Array,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  .inmuebles-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
  }
  
  @media (max-width: 768px) {
    .inmuebles-grid {
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    }
  }
  </style>
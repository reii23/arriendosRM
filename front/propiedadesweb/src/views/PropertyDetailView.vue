<template>
  <div v-if="propiedad" class="propiedad-detalle">
    <h1>Detalles de la Propiedad</h1>
    <div class="propiedad-info">
      <p><strong>ID:</strong> {{ propiedad.id }}</p>
      <p><strong>Tipo de Inmueble:</strong> {{ propiedad.tipoInmueble }}</p>
      <p><strong>Dirección:</strong> {{ propiedad.direccion }}</p>
      <p><strong>Precio:</strong> ${{ propiedad.precio.toLocaleString() }}</p>
      <p><strong>Disponible:</strong> {{ propiedad.disponible ? 'Sí' : 'No' }}</p>
      <p><strong>Verificado:</strong> {{ propiedad.verificado ? 'Sí' : 'No' }}</p>
      <p><strong>Metros Cuadrados:</strong> {{ propiedad.metrosCuadrados }} m²</p>
      <p><strong>Comuna:</strong> {{ propiedad.comuna }}</p>
      
      <!-- Campos específicos según el tipo de inmueble -->
      <template v-if="propiedad.tipoInmueble === 'CASA'">
        <p><strong>Número de Pisos:</strong> {{ propiedad.numPisos }}</p>
      </template>
      <template v-else-if="propiedad.tipoInmueble === 'DEPARTAMENTO'">
        <p><strong>Piso:</strong> {{ propiedad.piso }}</p>
        <p><strong>Tiene Ascensor:</strong> {{ propiedad.tieneAscensor ? 'Sí' : 'No' }}</p>
      </template>
      <template v-else-if="propiedad.tipoInmueble === 'TERRENO'">
        <p><strong>Tipo de Suelo:</strong> {{ propiedad.tipoSuelo }}</p>
      </template>
    </div>
  </div>
  <div v-else>
    <p>Cargando detalles de la propiedad...</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PropiedadDetalle',
  data() {
    return {
      propiedad: null
    }
  },
  created() {
    this.obtenerDetallesPropiedad();
  },
  methods: {
    async obtenerDetallesPropiedad() {
      try {
        const id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8080/inmuebles/${id}`);
        this.propiedad = response.data;
      } catch (error) {
        console.error('Error al obtener los detalles de la propiedad:', error);
      }
    }
  }
}
</script>

<style scoped>
.propiedad-detalle {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.propiedad-info p {
  margin-bottom: 10px;
}
</style>
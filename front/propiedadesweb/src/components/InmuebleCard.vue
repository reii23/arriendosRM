<template>
  <div class="inmueble-card" @click="goToDetail">
    <img :src="inmueble.imagen || getDefaultImage(inmueble.tipoInmueble)" :alt="inmueble.direccion"
      class="inmueble-image">
    <div class="inmueble-detalles">
      <h3 class="inmueble-precio">{{ formatPrice(inmueble.precio) }}</h3>
      <h2 class="inmueble-tipo-operacion">{{ establecerAccion(inmueble) }}</h2>
      <p class="inmueble-direccion">{{ inmueble.direccion }}</p>
      <div class="inmueble-atributos">
        <span>
          <i class="pi pi-home"></i>
          <template v-if="inmueble.disponible">Disponible</template>
          <template v-else>No disponible</template>
        </span>
        <span>
          <i class="pi pi-verified"></i>
          <template v-if="inmueble.verificado">Verificado</template>
          <template v-else>No verificado</template>
        </span>
        <span><i class="pi pi-stop"></i> {{ inmueble.metrosCuadrados }} m²</span>
      </div>
    </div>
  </div>
</template>


<script>
import defaultCasaImagen from '@/assets/default-house.jpg';
import defaultDepartamentoImagen from '@/assets/default-departamento.jpg';
import defaultTerrenoImagen from '@/assets/default-terreno.jpg';

export default {
  name: 'InmuebleCard',
  props: {
    inmueble: {
      type: Object,
      required: true
    }
  },
  methods: {
    formatPrice(price) {
      return new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' }).format(price);
    },
    getDefaultImage(tipoInmueble) {
      switch (tipoInmueble) {
        case 'CASA':
          return defaultCasaImagen;
        case 'DEPARTAMENTO':
          return defaultDepartamentoImagen;
        case 'TERRENO':
          return defaultTerrenoImagen;
      }
    },
    goToDetail() {
      this.$router.push({ name: 'PropertyDetail', params: { id: this.inmueble.id } });
    },
    establecerAccion(inmueble) {
      if (inmueble.tipoOperacion === 'vender') {
        return inmueble.tipoInmueble + ' en Venta';
      } else {
        return inmueble.tipoInmueble + ' en Arriendo';
      }
    }
  }
}
</script>

<style scoped>
.inmueble-card {
  border: 1px solid #f5f5f5;
  border-radius: 8px;
  overflow: hidden;
  transition: box-shadow 0.3s ease;
}

.inmueble-card:hover {
  box-shadow: black 0px 0px 30px;
  cursor: pointer;
}

.inmueble-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.inmueble-detalles {
  padding: 15px;
}

.inmueble-precio {
  font-size: 1.4em;
  font-weight: bold;
  color: #02b408;
  margin: 0 0 0px 0;
}

.inmueble-tipo-operacion {
  font-size: 1em;
  color: #f5f5f5;
  margin: 0 0 10px 0;
}

.inmueble-direccion {
  font-size: 1em;
  color: #f5f5f5;
  margin: 0 0 10px 0;
}

.inmueble-atributos {
  display: flex;
  justify-content: space-between;
  font-size: 0.9em;
  color: #faf9f9;
}

.inmueble-atributos span {
  display: flex;
  align-items: center;
}

.inmueble-atributos i {
  margin-right: 5px;
}
</style>
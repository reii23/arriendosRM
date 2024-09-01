<template>
  <!--carrusel para mostrar inmuebles recomendados-->
  <div class="carruselDeInmuebles">
    <h2>Top 10 inmuebles </h2>
    <Carousel :value="inmuebleRec" numVisible="5" numScroll="1" orientation="horizontal">
      <template #item="elementoPropiedad">
        <div class="inmueble" @click="goToDetail(elementoPropiedad.data.id)">
          <!-- imagen del inmueble -->
          <img :src="elementoPropiedad.data.imagen || getDefaultImage(elementoPropiedad.data.tipoInmueble)" />
          <div class="content">
            <p>Me gusta: {{ elementoPropiedad.data.meGustas }}</p>
            <p>Precio: ${{ elementoPropiedad.data.precio }}</p>
            <p>Dirección: {{ elementoPropiedad.data.direccion }}</p>
          </div>
        </div>
      </template>
    </Carousel>
  </div>

  <!-- Botón de filtros -->
  <button @click="toggleFiltro" class="filtro-button">Filtros</button>

  <!-- Formulario de filtros -->
  <div v-if="mostrarFiltro" class="filtro-container">
    <label>
      Precio Mínimo:
      <input type="number" v-model="filtroPrecioMin" placeholder="0" />
    </label>
    <label>
      Precio Máximo:
      <input type="number" v-model="filtroPrecioMax" placeholder="Sin máximo" />
    </label>
    <button @click="aplicarFiltro">Aplicar Filtros</button>
  </div>

  <div class="inmuebles-container">
    <InmueblePaginator :rows="rows" :totalRecords="totalRecords" :first="first" @page-change="onPageChange" />
    <div v-if="InmueblesEnPagina.length === 0" class="no-inmuebles">No hay inmuebles disponibles para mostrar </div>
    <InmuebleGrid v-else :inmuebles="InmueblesEnPagina" />
  </div>
</template>

<script>
import InmueblePaginator from "../components/InmueblePaginator.vue";
import InmuebleGrid from "../components/InmuebleGrid.vue";
import InmuebleService from "../services/InmuebleService";
import Carousel from 'primevue/carousel'
import defaultCasaImagen from '@/assets/default-house.jpg';
import defaultDepartamentoImagen from '@/assets/default-departamento.jpg';
import defaultTerrenoImagen from '@/assets/default-terreno.jpg';

export default {
  components: {
    InmueblePaginator,
    InmuebleGrid,
    Carousel
  },
  data() {
    return {
      inmuebles: [],
      rows: 10,
      totalRecords: 0,
      first: 0,
      inmuebleRec: [],
      mostrarFiltro: false,
      filtroPrecioMin: 0,
      filtroPrecioMax: null,
    };
  },
  computed: {
    InmueblesEnPagina() {
      const inicio = this.first;
      const fin = inicio + this.rows;
      return this.filtrarInmuebles().slice(inicio, fin);
    },
  },
  created() {
    this.inmuebleService = new InmuebleService();
    this.loadInmuebles();
    this.loadTopInmuebles();

  },
  methods: {
    loadInmuebles() {
      this.inmuebleService
        .getAll()
        .then((response) => {
          console.log("Inmuebles recibidos:", response.data);
          this.inmuebles = response.data;
          this.totalRecords = response.data.length;
          this.first = 0;
        })
        .catch((error) => {
          console.error("Error al traer los inmuebles", error);
        });
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
    goToDetail(idInmueble) {
      this.$router.push({ name: 'PropertyDetail', params: { id: idInmueble } });
    },
    // Función que permite cargar el top de inmuebles por likes para el carrusel
    loadTopInmuebles() {
      this.inmuebleService.getTopLikedInmuebles()
        .then((response) => {
          console.log("Top de inmuebles recibidos:", response.data);
          this.inmuebleRec = response.data;
        })
        .catch((error) => {
          console.error("Error al traer los inmuebles", error);
        });
    },

    onPageChange(event) {
      console.log("Page changed:", event);
      this.first = event.first;
      this.rows = event.rows;
    },
    toggleFiltro() {
      this.mostrarFiltro = !this.mostrarFiltro;
    },
    aplicarFiltro() {
      this.first = 0; // Reiniciar paginación al aplicar filtro
      this.totalRecords = this.filtrarInmuebles().length;
    },
    filtrarInmuebles() {
      return this.inmuebles.filter((inmueble) => {
        const precioMin = this.filtroPrecioMin || 0;
        const precioMax = this.filtroPrecioMax || Infinity;
        return inmueble.precio >= precioMin && inmueble.precio <= precioMax;
      });
    },
  },
};
</script>

<style scoped>
.inmuebles-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.no-inmuebles {
  text-align: center;
  font-size: 1.2em;
  color: #f1f1f1;
  margin-top: 20px;
}

.carruselDeInmuebles {
  width: 100%;
  overflow: hidden;
}

.carruselInterior {
  white-space: nowrap;
}

.inmueble {
  width: 40px;
  margin-right: 10px;
  display: inline-flex;
}

button {
  margin-right: 10px;
  margin-top: 10px;
}

.filtro-button {
  margin: 20px 0;
  padding: 10px 20px;
  background-color: #f36c3d;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease, background-color 0.3s ease;
}

.filtro-button:hover {
  background-color: #e55d2a;
  transform: translateY(-2px);
}

.filtro-button:active {
  background-color: #d84f1f;
  transform: translateY(0);
}

.carruselDeInmuebles img {
  /* estilos imagen */
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.carruselDeInmuebles .content {
  padding: 10px;
  background: #000000;
}

.carruselDeInmuebles h3,
.carruselDeInmuebles p {
  margin: 5px 0;
}

.carruselDeInmuebles .pi {
  margin-right: 4px;
}


.carruselDeInmuebles .inmueble {
  width: 300px;
  margin-right: 20px;
  display: flex;
  flex-direction: column;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  cursor: pointer
}

.carruselDeInmuebles .inmueble:hover {
  transform: scale(1.1);
  cursor: pointer;
}
</style>
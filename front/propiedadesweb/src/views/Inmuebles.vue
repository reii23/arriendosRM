<template>
  <div class="inmuebles-container">
    <InmueblePaginator
      :rows="rows"
      :totalRecords="totalRecords"
      :first="first"
      @page-change="onPageChange"
    />
    <div v-if="InmueblesEnPagina.length === 0" class="no-inmuebles">No hay inmuebles disponibles para mostrar </div>
    <InmuebleGrid v-else :inmuebles="InmueblesEnPagina" />
  </div>
</template>

<script>

import InmueblePaginator from "../components/InmueblePaginator.vue";
import InmuebleGrid from "../components/InmuebleGrid.vue";
import InmuebleService from "../services/InmuebleService";

export default {
  components: {
    InmueblePaginator,
    InmuebleGrid
  },
  data() {
    return {
      inmuebles: [ ],
      rows: 10,
      totalRecords: 0,
      first: 0,
    };
  },
  computed: {
    InmueblesEnPagina() {
      const inicio = this.first;
      const fin = inicio + this.rows;
      return this.inmuebles.slice(inicio, fin);
    },
  },
  created() {

    this.inmuebleService = new InmuebleService();
    this.loadInmuebles();
  },
  methods: {
    loadInmuebles() {
      this.inmuebleService.getAll().then((response) => {
        console.log("Inmuebles recibidos:", response.data);
        this.inmuebles = response.data;
        this.totalRecords = response.data.length;
        this.first = 0;
      }).catch(error => {
        console.error("Error fetching inmuebles:", error);
      });
    },
    onPageChange(event) {
      console.log("Page changed:", event);
      this.first = event.first;
      this.rows = event.rows;
    }
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
</style>
<template>
  <div class="propiedad-detalle-container">
    <h1>Detalles de la Propiedad</h1>
    <div v-if="propiedad" class="propiedad-detalle">
      <div class="propiedad-info">
        <div class="imagen-container">
          <img :src="propiedad.imagen ? propiedad.imagen : getDefaultImage(propiedad.tipoInmueble)" alt="Imagen de la propiedad" class="propiedad-imagen">
        </div>
        <div class="caracteristicas-container">
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
    </div>
    <div v-else>
      <p>Cargando detalles de la propiedad...</p>
    </div>

    <div class="horarios-visita">
      <h2>Horarios de Visita</h2>
      <div v-if="horariosVisita" class="horarios-lista">
        <div v-for="horario in horariosVisita" :key="horario.id" class="horario-item">
          <button @click="agendarVisita(horario.id, propiedad.id)"
		  style="width: 150px; height: 50px  ; background-color: #FFF;">
		  <strong>Fecha:</strong> {{horario.fecha}} <br> 
			<div v-if="horario.fecha[9] === 'm'"> 
				<strong>Hora:</strong> 9:00 - 10:30
			</div>
			<div v-if="horario.fecha[9] === 't'">
				<strong>Hora:</strong> 14:00 - 15:30
			</div>
			<div v-if="horario.fecha[9] === 'n'">
				<strong>Hora:</strong> 18:00 - 19:30
			</div>
		</button>

        </div>
      </div>
      <div v-else>
        <p>Cargando horarios de visita...</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import defaultCasaImagen from '@/assets/default-house.jpg';
import defaultDepartamentoImagen from '@/assets/default-departamento.jpg';
import defaultTerrenoImagen from '@/assets/default-terreno.jpg';
import { Calendar, setupCalendar } from 'v-calendar';
import 'v-calendar/style.css';
export default {
  name: 'PropiedadDetalle',
  data() {
    return {
      propiedad: null,
      horariosVisita: null
    }
  },
  created() {
    this.obtenerDetallesPropiedad();
    this.obtenerHorariosVisita();
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
    },
    async obtenerHorariosVisita() {
      try {
        const id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorInmueble/${id}`);
        this.horariosVisita = response.data;
      } catch (error) {
        console.error('Error al obtener los horarios de visita:', error);
      }
    },
	async agendarVisita(idHorario, idInmueble) {
	  try {
		const response = await axios.delete(`http://localhost:8080/horarioVisita/agendarVisita/${idHorario}`);
		const response2 = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorInmueble/${idInmueble}`);
        this.horariosVisita = response2.data;
		alert('Visita agendada correctamente');
	  } catch (error) {
		console.error('Error al agendar la visita:', error);
		alert('Error al agendar la visita');
	  }
	},
    getDefaultImage(tipoInmueble){
      switch (tipoInmueble) {
        case 'CASA':
          return defaultCasaImagen;
        case 'DEPARTAMENTO':
          return defaultDepartamentoImagen;
        case 'TERRENO':
          return defaultTerrenoImagen;
      }
    }
  }
}
</script>

<style scoped>
.propiedad-detalle-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

h1, h2 {
  margin-bottom: 20px;
}

.propiedad-info {
  display: flex;
  margin-bottom: 30px;
}

.imagen-container {
  flex: 1;
  margin-right: 20px;
}

.propiedad-imagen {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.caracteristicas-container {
  flex: 1;
}

.caracteristicas-container p {
  margin-bottom: 10px;
}

.horarios-visita {
  margin-top: 30px;
}

.horarios-lista {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
}

.horario-item {
  padding: 10px;
  border-radius: 8px;
}
</style>
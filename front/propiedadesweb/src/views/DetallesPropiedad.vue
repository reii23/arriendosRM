<template>
  <div class="propiedad-detalle-container">
    <h1>Detalles de la Propiedad</h1>
    <div v-if="propiedad" class="propiedad-detalle">
      <div class="propiedad-info">
        <div class="imagen-container">
          <img :src="propiedad.imagen ? propiedad.imagen : getDefaultImage(propiedad.tipoInmueble)"
            alt="Imagen de la propiedad" class="propiedad-imagen">
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
    <div class="calendario-visita">
      <h2>Seleccione una Fecha para Agendar una Visita</h2>
      <Calendario class="calendario-visita-component" @fechaSeleccionada="fechaSeleccionada = $event"
        @horariosPorFecha="horariosPorFecha = $event" @obtenerHorariosPorFecha="obtenerHorariosPorFecha" />
    </div>
    <div class="horarios-por-fecha">
      <!-- Obtengo la fecha seleccionada en el calendario -->
      <p v-if="fechaSeleccionada">Horarios disponibles para el {{ fechaSeleccionada }}</p>
      <div v-if="horariosPorFecha.length">
        <div v-for="horario in horariosPorFecha" :key="horario.id" class="horario-item">
          <p><strong>Horario:</strong> {{ obtenerHorario(horario.fecha) }}</p>
          <button @click="agendarVisita(horario.id, propiedad.id)">Agendar Visita</button>
        </div>
      </div>
      <div v-else>
        <p>No hay horarios disponibles para esta fecha.</p>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';
import defaultCasaImagen from '@/assets/default-house.jpg';
import defaultDepartamentoImagen from '@/assets/default-departamento.jpg';
import defaultTerrenoImagen from '@/assets/default-terreno.jpg';
import { auth } from "@/auth";
import Calendario from '@/components/Calendario.vue';

export default {
  name: 'PropiedadDetalle',
  components: {
    Calendario
  },
  data() {
    return {
      propiedad: null,
      horariosVisita: null,
      // Fecha seleccionada en el calendario
      fechaSeleccionada: '',
      horariosPorFecha: []
    };
  },
  created() {
    this.obtenerDetallesPropiedad();
    this.obtenerHorariosVisita();
    this.actualizarFechaSeleccionada(this.fechaSeleccionada);
  },
  methods: {
    actualizarFechaSeleccionada(fecha) {
      this.fechaSeleccionada = fecha;
      this.obtenerHorariosPorFecha();
    },
    async obtenerDetallesPropiedad() {
      try {
        const id = this.$route.params.id; // Obtener el ID de la propiedad desde la URL
        const response = await axios.get(`http://localhost:8080/inmuebles/${id}`); // Obtener los detalles de la propiedad por su ID
        this.propiedad = response.data; // Guardar los detalles de la propiedad en la variable 'propiedad'
      } catch (error) {
        console.error('Error al obtener los detalles de la propiedad:', error);
      }
    },
    async obtenerHorariosVisita() {
      try {
        const id = this.$route.params.id; // Obtener el ID de la propiedad desde la URL
        const response = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaDisponiblesPorInmueble/${id}`);
        this.horariosVisita = response.data;
      } catch (error) {
        console.error('Error al obtener los horarios de visita:', error);
      }
    },
    async agendarVisita(idHorario, idInmueble) {
      try {
        const sesionIniciada = auth.isLoggedIn; // Verificar si el usuario ha iniciado sesión
        const idUsuario = localStorage.getItem('userId'); // Obtener el ID del usuario desde el almacenamiento local
        if (!sesionIniciada) { // Si el usuario no ha iniciado sesión, mostrar un mensaje de alerta
          alert('Debe iniciar sesión para agendar una visita');
          return;
        }
        const respuesta = await axios.post(`http://localhost:8080/horarioVisita/agendarVisita/${idHorario}/${idUsuario}`);
        const respuesta2 = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaDisponiblesPorInmueble/${idInmueble}`);
        this.horariosVisita = respuesta2.data;
        alert('Visita agendada correctamente');
      } catch (error) {
        console.error('Error al agendar la visita:', error);
        alert('Error al agendar la visita');
      }
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
    async obtenerHorariosPorFecha() {
      try {
        const id = this.$route.params.id; // ID del inmueble
        // Recibo la fecha en formato yyyy-mm-dd y la convierto a dd/mm/yyyy
        const fecha = this.fechaSeleccionada.split('-').reverse().join('-');
        console.log(fecha);
        const response = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorFecha/${id}/${fecha}`);
        this.horariosPorFecha = response.data;
      } catch (error) {
        console.error('Error al obtener horarios por fecha:', error);
      }
    },
    obtenerHorario(fecha) {
      const horario = fecha.split('/')[3];
      switch (horario) {
        case 'm': return 'Mañana (9:00 - 10:30)';
        case 't': return 'Tarde (14:00 - 15:30)';
        case 'n': return 'Noche (18:00 - 19:30)';
        default: return '';
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

h1,
h2 {
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

.calendario-visita {
  margin-bottom: 20px;
}

.horarios-por-fecha {
  margin-top: 20px;
}

.horario-item {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 10px;
}
</style>
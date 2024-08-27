<template>
  <div class="propiedad-detalle-container">
    <h1>Detalles de la Propiedad</h1>
    <div v-if="propiedad" class="propiedad-detalle">
      <div class="propiedad-info">
        <div class="imagen-container">
          <img :src="propiedad.imagen ? propiedad.imagen : getDefaultImage(propiedad.tipoInmueble)"
            alt="Imagen de la propiedad" class="propiedad-imagen">
          <!--Mostrar valoraciones solo si la propiedad está verificada-->
          <div class="encabezado">
            <button v-show="propiedad && propiedad.verificado == true" class="btn-me-gusta"
              @click="meGusta(propiedad.id)">🤍</button>
            <p v-show="propiedad && propiedad.verificado == true" class="valoraciones">Esta propiedad le encanta a {{
              propiedad.meGustas }} usuarios</p>
          </div>
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
      <div>
        <!-- contactar via chat -->
        <button @click="chatHandle">Contactar</button>
      </div>
    </div>
    <div v-else>
      <p>Cargando detalles de la propiedad...</p>
    </div>
    <div class="calendario-visita">
      <h2>Seleccione una Fecha para Agendar una Visita</h2>
      <Calendario class="calendario-visita-component" @fechaSeleccionada="actualizarFechaSeleccionada"
        @horariosPorFecha="horariosPorFecha = $event" />
    </div>
    <div class="horarios-por-fecha">
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
  },
  methods: {
    async chatHandle() {
      // Obtengo el id del usuario logueado
      const idUsuario = localStorage.getItem('userId');
      // Obtengo el id del usuario dueño de la propiedad
      const idPropietario = this.propiedad.idUsuario;

      // Si el usuario no esta logueado, no se puede contactar
      if (!auth.isLoggedIn) {
        alert('Debe iniciar sesión para contactar al propietario');
        return;
      }
      // Si el usuario logueado es el dueño de la propiedad, no se puede contactar
      if (idUsuario == idPropietario) {
        alert('Esta es tu propiedad, no puedes contactarte contigo mismo');
        return;
      }
      console.log(idUsuario);
      console.log(idPropietario);
      // Obtengo todos los chats del usuario propietario
      const responseProp = await axios.get(`http://localhost:8080/chat/obtenerChatPorIdUsuario1/${idPropietario}`);
      console.log(responseProp.data);
      // Si el usuario propietario no tiene chats, creo un chat
      if (responseProp.data.length == 0) {
        console.log('no tiene chat');
        await axios.post(`http://localhost:8080/chat/crearChat/${idPropietario}/${idUsuario}`);
      } else if (responseProp.data.length > 0) {
        // Si el usuario propietario ya tiene chats, verifico si ya existe un chat con el usuario logueado
        let aux = 0;
        for (let i = 0; i < responseProp.data.length; i++) {
          if (responseProp.data[i].idUsuario2 == idUsuario) { // Si existe un chat con el usuario logueado, redirijo al chat
            console.log('existe chat');
            aux = 1;
            break;
          }
        }

        // Si no existe un chat con el usuario logueado, creo un chat
        if (aux == 0) {
          console.log('no existe chat');
          await axios.post(`http://localhost:8080/chat/crearChat/${idPropietario}/${idUsuario}`);
        } else {
          // Si ya existe un chat con el usuario logueado, redirijo al chat
          // Logica de redireccionamiento
          console.log('existe chat');
        }
      }


    },
    actualizarFechaSeleccionada(fecha) {
      this.fechaSeleccionada = fecha;
      this.obtenerHorariosPorFecha();
    },
    async obtenerDetallesPropiedad() {
      try {
        const id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8080/inmuebles/${id}`); // Obtener detalles de la propiedad
        this.propiedad = response.data;
      } catch (error) {
        console.error('Error al obtener los detalles de la propiedad:', error);
      }
    },
    async obtenerHorariosPorFecha() {
      try {
        const id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorFecha/${id}/${this.fechaSeleccionada}`); // Obtener horarios disponibles
        this.horariosPorFecha = response.data;
      } catch (error) {
        console.error('Error al obtener horarios por fecha:', error);
      }
    },
    obtenerHorario(fecha) { // Fecha formato DDMMYYYYm
      const horario = fecha[fecha.length - 1];
      switch (horario) {
        case 'm': return 'Mañana (9:00 - 10:30)';
        case 't': return 'Tarde (14:00 - 15:30)';
        case 'n': return 'Noche (18:00 - 19:30)';
        default: return '';
      }
    },
    async agendarVisita(idHorario, idInmueble) {
      try {
        const sesionIniciada = auth.isLoggedIn;
        const idUsuario = localStorage.getItem('userId');
        if (!sesionIniciada) {
          alert('Debe iniciar sesión para agendar una visita');
          return;
        }
        await axios.post(`http://localhost:8080/horarioVisita/agendarVisita/${idHorario}/${idUsuario}`); // post de agendar visita
        alert('Visita agendada correctamente');
        this.obtenerHorariosPorFecha(); // Actualizar los horarios disponibles (para que se refleje el horario agendado)
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
    }
  }
}
</script>

<style scoped>
.propiedad-detalle-container {
  text-align: left;
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.encabezado {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
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
  margin-top: 0px;
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
  margin-top: 60px;
  text-align: center;
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

.valoraciones {
  text-align: center;
  align-items: center;
  margin-top: 20px;

}

.btn-me-gusta {
  background-color: #f86a9a;
  color: #eaeaea;
  border: none;
  border-radius: 8px;
  padding: 10px 10px;
  cursor: pointer;
  margin-top: 0px;
  margin-right: 0px;
}

.comentarios-propiedad {
  margin-top: 90px;
  text-align: left;
}
</style>
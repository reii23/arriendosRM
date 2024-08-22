<template>
  <div>
    <h1>Agente Inmobiliario - Horarios Disponibles</h1>
    <form @submit.prevent="agregarHorario">
      <div class="form-group">
        <label for="fecha">Fecha:</label>
        <input type="date" v-model="nuevoHorario.fecha" required />
      </div>

      <div class="form-group">
        <label for="periodo">Periodo:</label>
        <select id="periodo" v-model="nuevoHorario.periodo" required>
          <option value="m">Mañana</option>
          <option value="t">Tarde</option>
          <option value="n">Noche</option>
        </select>
      </div>

      <div class="form-group">
        <label for="idInmueble">ID del Inmueble:</label>
        <input type="text" id="idInmueble" v-model="nuevoHorario.idInmueble" required />
      </div>

      <!-- Botón de Agregar Horario al final del formulario -->
      <button type="submit">Agregar Horario</button>
    </form>
    <p v-if="mensaje" :class="{'mensaje-exito': esCorrecto, 'mensaje-error': !esCorrecto}">
      {{ mensaje }}
    </p>

    <!-- Se eliminó el texto de "Horarios Disponibles" -->
    <!-- <h2>Horarios Disponibles</h2>
    <ul>
      <li v-for="horario in horarios" :key="horario.id">
        {{ horario.fecha }} a las {{ horario.periodo }}
      </li>
    </ul> -->
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AgenteView',
  data() {
    return {
      nuevoHorario: {
        fecha: '',
        periodo: '',
        idInmueble: ''
      },
      horarios: [],
      userId: localStorage.getItem('userId'),
      mensaje: '',
      esCorrecto: false
    };
  },
  methods: {
    async agregarHorario() {
      if (!this.userId) {
        console.error('No se puede obtener el ID del usuario.');
        return;
      }

      const horario = {
        fecha: this.formatearFecha(this.nuevoHorario.fecha, this.nuevoHorario.periodo),
        idInmueble: this.nuevoHorario.idInmueble,
        idUsuario: this.userId,
        idVisitante: -1 // Aún no tenemos un ID de visitante, por lo que será nulo
      };

      const periodoEstablecido = (valor) => {
        if(valor == 'm') {
          return 'Mañana';
        } else if(valor == 't') {
          return 'Tarde';
        } else {
          return 'Noche';
        }
      };

      console.log(this.userId);
      console.log(horario.idInmueble);
      console.log(horario.idVisitante);
      console.log(horario.fecha);
      console.log(horario.idUsuario);

      try {
        const response = await axios.post('http://localhost:8080/horarioVisita/crearHorarioVisita', horario);
        console.log('Horario registrado:', response.data);
        this.mensaje = 'Se ha registrado una visita en el inmueble ' + horario.idInmueble + ' para el periodo ' + 
                        horario.fecha[0] + horario.fecha[1] + '/' + horario.fecha[2] + horario.fecha[3] + '/' + 
                        horario.fecha[4] + horario.fecha[5] + horario.fecha[6] + horario.fecha[7] + ' ' + 
                        periodoEstablecido(horario.fecha[8]);
        this.esCorrecto = true;
        this.horarios.push(response.data);
        this.nuevoHorario.fecha = '';
        this.nuevoHorario.periodo = '';
        this.nuevoHorario.idInmueble = '';
      } catch (error) {
        console.error('Error al registrar el horario:', error);
        this.mensaje = 'Error al registrar el horario. Inténtalo de nuevo';
        this.esCorrecto = false;
      }
    },
    formatearFecha(fecha, periodo) {
      const fechaObj = new Date(fecha);
      const dia = fechaObj.getDate().toString().padStart(2, '0');
      const mes = (fechaObj.getMonth() + 1).toString().padStart(2, '0');
      const anio = fechaObj.getFullYear(); // Obtener año
      return `${dia}${mes}${anio}${periodo}`;
    }
  }
};
</script>

<style scoped>
.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input,
select {
  width: 8.5%;
  padding: 8px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px;
  background-color: #ff2600;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #ff6200;
}

.mensaje-exito {
  color: #28a745;
  margin-top: 20px;
}

.mensaje-error {
  color: red;
  margin-top: 20px;
}

</style>

<template>
  <div>
    <h1>Agente Inmobiliario - Horarios Disponibles</h1>
    <form @submit.prevent="agregarHorario">
      <div class="form-group">
        <label for="fecha">Fecha:</label>
        <input type="date" id="dia" v-model="nuevoHorario.fecha" required @input="agregarDias()" />
      </div>
	  <div>
		<label for="dias">Dias Seleccionados: {{ this.dias.length }}</label>
        <p v-for="dia in dias">{{ dia }}</p>
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
        <label for="idInmueble">Dirección del Inmueble:</label>
        <select v-model="nuevoHorario.idInmueble" required>
          <option v-for="inmueble in inmuebles" :key="inmueble.id" :value="inmueble.id">
            {{ inmueble.direccion }}
          </option>
        </select>
      </div>

      <!-- Botón de Agregar Horario al final del formulario -->
      <button type="submit">Agregar Horario</button>
    </form>
    <p v-if="mensaje" :class="{'mensaje-exito': esCorrecto, 'mensaje-error': !esCorrecto}">
      {{ mensaje }}
    </p>
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
      inmuebles: [], // Aquí se almacenarán los inmuebles cargados
      horarios: [],
	  dias:[],
      userId: localStorage.getItem('userId'),
      mensaje: '',
      esCorrecto: false
    };
  },
  created() {
    this.cargarInmuebles(); // permite cargar los inmuebles al iniciar la vista
  },
  methods: {

    // Método que permite cargar los inmuebles que se ubican en la base de datos, consulta: 'http://localhost:8080/inmuebles'
    async cargarInmuebles() {
      try {
        const response = await axios.get('http://localhost:8080/inmuebles');
        this.inmuebles = response.data;
      } catch (error) {
        console.error('Error al cargar los inmuebles:', error);
      }
    },
	async agregarDias(){
	  const inputNombre = document.querySelector('#dia');

	  this.dias.push(inputNombre.value);

	},
    async agregarHorario() {
      if (!this.userId) {
        console.error('No se puede obtener el ID del usuario.');
        return;
      }
	  for(let dia of this.dias){
        const horario = {
          fecha: this.formatearFecha(dia, this.nuevoHorario.periodo),
          idInmueble: this.nuevoHorario.idInmueble,
          idUsuario: this.userId,
          idVisitante: -1 // Aún no tenemos un ID de visitante, por lo que será nulo
        };
        try {
          const response = await axios.post('http://localhost:8080/horarioVisita/crearHorarioVisita', horario);
          console.log('Horario registrado:', response.data);
          this.mensaje = 'Horario registrado exitosamente';
          this.esCorrecto = true;
          this.horarios.push(response.data);
          this.nuevoHorario.fecha = '';
        } catch (error) {
          console.error('Error al registrar el horario:', error);
          this.mensaje = 'Error al registrar el horario. Inténtalo de nuevo';
          this.esCorrecto = false;
        }}
		this.nuevoHorario.periodo = '';
		this.nuevoHorario.idInmueble = '';
		this.dias = [];
    },
    formatearFecha(fecha, periodo) {
      const [anio, mes, dia] = fecha.split('-'); // Descomponer la fecha en sus componentes
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
  width: 11.5%;
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
</style>

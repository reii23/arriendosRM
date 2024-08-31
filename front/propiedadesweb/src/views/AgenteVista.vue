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
		<div v-for=" (dia, indice) in dias" :key="indice">
          {{ formatearFecha2(dia) }} <button @click="eliminarDia(indice)" class="boton-eliminar">X</button>
		</div>
	  </div>
	  <br>
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
	<div v-for="mensaje in mensajes">
      <p :class="{'mensaje-exito': mensaje[0] === 'H', 'mensaje-error': mensaje[0] === 'Y'}">
        {{ mensaje }}
      </p>
	</div>
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
	  misHorarios: null,
      userId: localStorage.getItem('userId'),
      mensajes: [],
      esCorrecto: false
    };
  },
  created() {
    this.cargarInmuebles(); // permite cargar los inmuebles al iniciar la vista
	this.cargarMisHorarios();
  },
  methods: {
	async cargarMisHorarios(){
		try{
			const idUsuario = localStorage.getItem('userId');
			const respuesta = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorUsuarioGuia/${idUsuario}`);
			this.misHorarios = respuesta.data;
			console.log('Horarios del usuario:', this.misHorarios);
		}catch(error){
			console.error('Error al obtener los horarios del usuario:', error);
		}
	},
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
		if(this.comprobarHorario(horario)){
          try {
            const response = await axios.post('http://localhost:8080/horarioVisita/crearHorarioVisita', horario);
            console.log('Horario registrado:', response.data);
            this.mensajes.push('Horario registrado exitosamente ' + this.formatearFecha3(horario.fecha));
            this.esCorrecto = true;
            this.horarios.push(response.data);
            this.nuevoHorario.fecha = '';
			this.cargarMisHorarios();
          } catch (error) {
            console.error('Error al registrar el horario:', error);
            this.mensajes = 'Error al registrar el horario. Inténtalo de nuevo';
            this.esCorrecto = false;
          }
		}else{
			this.mensajes.push('Ya existe un horario para esa fecha ' + this.formatearFecha3(horario.fecha)) ;
			this.esCorrecto = false;
		}
	}
	this.nuevoHorario.periodo = '';
	this.nuevoHorario.idInmueble = '';
	this.dias = [];
    },
	eliminarDia(indice){
		this.dias.splice(indice, 1);
	},
    formatearFecha(fecha, periodo) {
      const [anio, mes, dia] = fecha.split('-'); // Descomponer la fecha en sus componentes
      return `${dia}${mes}${anio}${periodo}`;
    },
	formatearFecha2(fecha){
		const [anio, mes, dia] = fecha.split('-');
		return `${dia}-${mes}-${anio}`;
	},
	formatearFecha3(fecha){
			const dia = fecha[0] + fecha[1];
			const mes = fecha[2] + fecha[3];
			const anio = fecha[4] + fecha[5] + fecha[6] + fecha[7];
			return `${dia}/${mes}/${anio}`;
		},
	comprobarHorario(horarioNuevo){
		for(let horario of this.misHorarios){
			if(horario.fecha === horarioNuevo.fecha){
				return false;
			}
		}
		return true;
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

.boton-eliminar {
  padding: 10px;
  background-color: #ff2600;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}
</style>

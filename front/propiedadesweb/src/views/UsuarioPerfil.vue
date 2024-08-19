<template>
  <div class="account-page">
	<h1>Mi Cuenta</h1>
	<div v-if="usuario" class="cuenta-info">
	  <h2>Información de la Cuenta</h2>
	  <p>Nombre: {{ usuario.nombre }}</p>
		<p>Email: {{ usuario.email }}</p>
		<p>Número de telefono: {{ usuario.numeroTelefono}} </p>
		<p v-if="usuario.rol === 0"> Rol: Administrador</p>
		<p v-if="usuario.rol === 1"> Rol: Cliente</p>
		<p v-if="usuario.rol === 2"> Rol: Agente Inmobiliario</p>
	</div>
	<div v-if="usuario && (usuario.rol === 1 || usuario.rol === 0)"class="mis-propiedades">
	  <h2>Mis Propiedades</h2>
	  <div v-if="propiedades" class="propiedades-lista">
		<div v-for="propiedad in propiedades" :key="propiedad.id" class="propiedad-item">
		  <router-link :to="'/inmuebles/' + propiedad.id">
			<strong>Dirección:</strong> {{ propiedad.direccion }} <br></router-link>
			<strong>Precio:</strong> ${{ propiedad.precio.toLocaleString() }} <br>
			<strong>Disponible:</strong> {{ propiedad.disponible ? 'Sí' : 'No' }} <br>
			<strong>Verificado:</strong> {{ propiedad.verificado ? 'Sí' : 'No' }} <br>
			<strong>Metros Cuadrados:</strong> {{ propiedad.metrosCuadrados }} m² <br>
			<strong>Comuna:</strong> {{ propiedad.comuna }} <br>
		</div>	
		<button @click="irACrearPublicacion()"
					 style= "font: 1em sans-serif; font-size: x-large; color: black;background-color: beige;
					 padding: 10px;border-radius: 8px; height: 180px; width: 200px; text-align: center">Crear Publicación</button>
	  </div>
	</div>
	<div v-if="usuario && (usuario.rol === 1 || usuario.rol === 0)"class="visitas-agendadas">
	  <h2>Visitas Agendadas</h2>
	  <div v-if="horariosVisitas" class="visitas-lista">
		<div v-for="horario in horariosVisitas" :key="horario.id" class="horario-item">
		<router-link :to="'/inmuebles/' + horario.idInmueble">
		<strong>Propiedad:</strong> {{ horario.idInmueble}}</router-link> <br>
		<strong>Fecha:</strong> {{ horario.fecha }} <br>
		<div v-if="horario.fecha[9] === 'm'"> 
		  <strong>Hora:</strong> 9:00 - 10:30
		</div>
		<div v-if="horario.fecha[9] === 't'">
		  <strong>Hora:</strong> 14:00 - 15:30
		</div>
		<div v-if="horario.fecha[9] === 'n'">
		  <strong>Hora:</strong> 18:00 - 19:30
		</div> 
		<br>
		<button @click="desagendarVisita(horario.id)"
		 		 style="width: 120px; height: 30px  ; background-color: #FFF"> Desagendar</button>
		</div>
	  </div>
	</div>	 
  </div> 
</template>

<script>
  import axios from 'axios';
  import { auth } from '@/auth';

  export default{
	name: 'AcountInfo',
	data(){
        return {
		  usuario: null,
		  propiedades: null,
		  horariosVisitas: null
		}
	},
	created(){
		this.obtenerUsuario();
		this.obtenerPropiedades();
		this.obtenerHorariosVisitas();
	},
	methods:{
		async obtenerUsuario(){
			try{
				const id = localStorage.getItem('userId');
				const respuesta = await axios.get(`http://localhost:8080/user/obtenerUsuarioPorId/${id}`);
				this.usuario = respuesta.data;
			}catch(error){
				console.error('Error al obtener los detalles del usuario:', error);
			}
		},
		async obtenerPropiedades(){
			try{
				const idUsuario = localStorage.getItem('userId');
				const respuesta = await axios.get(`http://localhost:8080/inmuebles/obtenerInmueblesPorUsuario/${idUsuario}`);
				this.propiedades = respuesta.data;
			}catch(error){
				console.error('Error al obtener las propiedades del usuario:', error);
			}
		},
		async obtenerHorariosVisitas(){
			try{
				const id = localStorage.getItem('userId');
				const respuesta = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorUsuario/${id}`);
				this.horariosVisitas = respuesta.data;
			}catch(error){
				console.error('Error al obtener las visitas del usuario:', error);
			}
		},
		async desagendarVisita(horarioId){
			try{
				const respuesta = await axios.post(`http://localhost:8080/horarioVisita/desagendarVisita/${horarioId}`);
				this.obtenerHorariosVisitas();
				alert('Visita desagendada correctamente');
			}catch(error){
				console.error('Error al desagendar la visita:', error);
				alert('Error al desagendar la visita');
			}
		},
		irACrearPublicacion(){
			this.$router.push('/seleccionar-inmueble');
		}
	}
  }

</script>

<style scoped>
.cuenta-info {
  max-width: 350px;
  margin: 0 auto;
  padding: 20px;
}

h1, h2 {
  margin-bottom: 20px;
}
.visitas-agendadas {
  margin-top: 30px;
}
.visitas-lista {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
  margin-left: 150px;
  margin-right: 150px;
}

.horario-item {
	font: 1em sans-serif;
    color: black;
    background-color: beige;
	padding: 10px;
	border-radius: 8px;
}

.propiedades-lista {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
  margin-left: 150px;
  margin-right: 150px;
}

.propiedad-item {
  font: 1em sans-serif;
  color: black;
  background-color: beige;
  padding: 10px;
  border-radius: 8px;
  height: 180px;
  width: 200px;
  text-align: center;
  
}
</style>
<template>
  <div>
    <h1>Agente Inmobiliario - Horarios Disponibles</h1>
    <form @submit.prevent="agregarHorario">
      <label for="fecha">Fecha:</label>
      <input type="date" v-model="nuevoHorario.fecha" required />

      <label for="hora">Hora:</label>
      <input type="time" v-model="nuevoHorario.hora" required />

      <button type="submit">Agregar Horario</button>
    </form>

    <h2>Horarios Disponibles</h2>
    <ul>
      <li v-for="horario in horarios" :key="horario.id">
        {{ horario.fecha }} a las {{ horario.hora }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'AgenteView',
  data() {
    return {
      nuevoHorario: {
        fecha: '',
        hora: ''
      },
      horarios: []
    };
  },
  methods: {
    agregarHorario() {
      this.horarios.push({ ...this.nuevoHorario });
      this.nuevoHorario.fecha = '';
      this.nuevoHorario.hora = '';
    }
  }
};
</script>

<style scoped>



</style>

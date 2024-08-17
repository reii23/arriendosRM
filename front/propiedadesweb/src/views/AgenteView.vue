<template>
  <div>
    <h1>Agente Inmobiliario - Horarios Disponibles</h1>
    <form @submit.prevent="agregarHorario">
      <label for="fecha">Fecha:</label>
      <input type="date" v-model="nuevoHorario.fecha" required />

      <label for="periodo">Periodo:</label>
      <select v-model="nuevoHorario.periodo" required>
        <option value="Mañana">Mañana</option>
        <option value="Tarde">Tarde</option>
        <option value="Noche">Noche</option>
      </select>

      <button type="submit">Agregar Horario</button>
    </form>

    <h2>Horarios Disponibles</h2>
    <ul>
      <li v-for="(horario, index) in horarios" :key="index">
        {{ horario.fecha }} - {{ horario.periodo }}
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
        periodo: ''
      },
      horarios: []
    };
  },
  methods: {
    agregarHorario() {
      this.horarios.push({ ...this.nuevoHorario });
      this.nuevoHorario.fecha = '';
      this.nuevoHorario.periodo = '';
    }
  }
};
</script>


<style scoped>

label {
  margin-top: 10px;
}

button {
  margin-top: 20px;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  background-color: #f2f2f2;
  margin: 5px 0;
  padding: 10px;
  border-radius: 4px;
}

</style>

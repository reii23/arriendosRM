<template>
  <div class="subsidio-info">
    <h1>Subsidios para la Adquisición de Inmuebles</h1>
    <div class="subsidio-tarjeta" v-for="subsidio in subsidios" :key="subsidio.id">
      <h2>{{ subsidio.nombre }}</h2>
      <p><strong>Descripción:</strong> {{ subsidio.descripcion }}</p>
      <p><strong>Requisitos:</strong></p>
      <ul>
        <li v-for="requerimiento in subsidio.requerimientos" :key="requerimiento">{{ requerimiento }}</li>
      </ul>
      <p><strong>Monto:</strong> {{ subsidio.monto }}</p>
      <button @click="contactoInfo(subsidio)">Más información</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'SubsidyInfo',
  setup() {
    const subsidios = ref([
      {
        id: 1,
        nombre: 'Subsidio Habitacional',
        descripcion: 'Ayuda económica para la compra de tu primera vivienda.',
        requerimientos: [
          'Ser mayor de 18 años',
          'No poseer otra propiedad',
          'Ingresos familiares mensuales no superiores a $1,000,000',
        ],
        monto: '$50,000 - $100,000',
      },
      {
        id: 2,
        nombre: 'Subsidio Renovación Urbana',
        descripcion: 'Subsidio para adquirir inmuebles en zonas de renovación urbana.',
        requerimientos: [
          'Inmueble ubicado en zona de renovación urbana',
          'Ingreso familiar dentro de los límites establecidos',
        ],
        monto: '$30,000 - $70,000',
      },
      // Más subsidios pueden ser agregados aquí
    ]);

    const contactoInfo = (subsidio) => {
      alert(`Para más información sobre el ${subsidio.nombre}, contacta a nuestro equipo de soporte.`);
    };

    return {
      subsidios,
      contactoInfo: contactoInfo,
    };
  }
};
</script>

<style scoped>
.subsidio-info {
  background-color: #f0f8ff;
  color: #333;
  padding: 20px;
  border-radius: 8px;
  max-width: 800px;
  margin: 40px auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}

.subsidio-tarjeta {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.subsidio-tarjeta h2 {
  margin-bottom: 10px;
}

.subsidio-tarjeta ul {
  padding-left: 20px;
}

button {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  background-color: #042fc1;
  color: #fff;
  font-size: 14px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #5881a7;
}
</style>

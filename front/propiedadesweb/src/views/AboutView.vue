<template>
  <div class="subsidy-info">
    <h1>Subsidios para la Adquisición de Inmuebles</h1>
    <div class="subsidy-card" v-for="subsidy in subsidies" :key="subsidy.id">
      <h2>{{ subsidy.name }}</h2>
      <p><strong>Descripción:</strong> {{ subsidy.description }}</p>
      <p><strong>Requisitos:</strong></p>
      <ul>
        <li v-for="requirement in subsidy.requirements" :key="requirement">{{ requirement }}</li>
      </ul>
      <p><strong>Monto:</strong> {{ subsidy.amount }}</p>
      <button @click="contactInfo(subsidy)">Más información</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'SubsidyInfo',
  setup() {
    const subsidies = ref([
      {
        id: 1,
        name: 'Subsidio Habitacional',
        description: 'Ayuda económica para la compra de tu primera vivienda.',
        requirements: [
          'Ser mayor de 18 años',
          'No poseer otra propiedad',
          'Ingresos familiares mensuales no superiores a $1,000,000',
        ],
        amount: '$50,000 - $100,000',
      },
      {
        id: 2,
        name: 'Subsidio Renovación Urbana',
        description: 'Subsidio para adquirir inmuebles en zonas de renovación urbana.',
        requirements: [
          'Inmueble ubicado en zona de renovación urbana',
          'Ingreso familiar dentro de los límites establecidos',
        ],
        amount: '$30,000 - $70,000',
      },
      // Más subsidios pueden ser agregados aquí
    ]);

    const contactInfo = (subsidy) => {
      alert(`Para más información sobre el ${subsidy.name}, contacta a nuestro equipo de soporte.`);
    };

    return {
      subsidies,
      contactInfo,
    };
  }
};
</script>

<style scoped>
.subsidy-info {
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

.subsidy-card {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.subsidy-card h2 {
  margin-bottom: 10px;
}

.subsidy-card ul {
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

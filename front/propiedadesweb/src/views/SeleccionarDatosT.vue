<template>
  <div class="property-form">
    <h2>¿Qué deseas hacer con tu propiedad?</h2>
    <div class="options">
      <label>
        <input type="radio" v-model="action" value="vender" />
        Vender
      </label>
      <label>
        <input type="radio" v-model="action" value="arrendar" />
        Arrendar
      </label>
    </div>
    <div class="address-inputs">
      <h3>Ingresa los datos de tu propiedad</h3>
      <input type="text" v-model="address.line1" placeholder="Dirección de la propiedad" />
      <input type="text" v-model="address.line2" placeholder="Precio" />
      <input type="text" v-model="address.line3" placeholder="Metros cuadrados" />
      <input type="text" v-model="address.line4" placeholder="Tipo de suelo" />
      <input type="text" v-model="address.line5" placeholder="¿Tiene servicios básicos?" />
    </div>
    <button @click="publishProperty">Publicar Terreno</button>
    <p v-if="message" :class="{'success-message': isSuccess, 'error-message': !isSuccess}">
      {{ message }}
    </p>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'PropertyForm',
  setup() {
    const action = ref('');
    const address = ref({
      line1: '',
      line2: '',
      line3: '',
      line4: '',
      line5: ''
    });
    const message = ref('');
    const isSuccess = ref(false);

    const publishProperty = () => {
      if (action.value && address.value.line1 && address.value.line2) {
        isSuccess.value = true;
        message.value = "Su publicación está a la espera de ser verificada";
      } else {
        isSuccess.value = false;
        message.value = "Los datos de la publicación no son válidos";
      }
    };

    return {
      action,
      address,
      message,
      isSuccess,
      publishProperty
    };
  }
}
</script>

<style scoped>
.property-form {
  background-color: #022b60;
  color: #e4f0ff;
  padding: 40px;
  border-radius: 10px;
  max-width: 600px;
  margin: 40px auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 24px;
}

.options {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin-bottom: 30px;
}

.radio-option {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
}

.address-inputs {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.address-inputs h3 {
  font-size: 20px;
  margin-bottom: 20px;
  text-align: center;
}

input[type="text"] {
  padding: 12px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  background-color: #e4f0ff;
  color: #022b60;
}

input[type="text"]::placeholder {
  color: #7a8ca7;
}

button {
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  background-color: #ff6b35;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  display: block;
  margin: 20px auto;
}

button:hover {
  background-color: #ff5630;
}

.success-message {
  color: #28a745;
  text-align: center;
  margin-top: 20px;
}

.error-message {
  color: #dc3545;
  text-align: center;
  margin-top: 20px;
}
</style>

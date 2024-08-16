<template>
    <div class="seleccionar-terreno">
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
        <input type="text" v-model="address.line1" placeholder="Dirección" />
        <select v-model="address.comuna">
        <option value="">Selecciona una comuna</option>
        <option v-for="comuna in comunas" :key="comuna" :value="comuna">
          {{ comuna.replace(/_/g, ' ') }}
        </option>
      </select>
        <input type="text" v-model="address.line2" placeholder="Valor (pesos)" />
        <input type="text" v-model="address.line3" placeholder="m2 del terreno" />
        <input type="text" v-model="address.line4" placeholder="Tipo de suelo" />
        <input type="text" v-model="address.line5" placeholder="¿Cuenta con servicios básicos? (Si/No)" />
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
        comuna: '',
        line2: '',
        line3: '',
        line4: '',
        line5: ''
      });
      const message = ref('');
      const isSuccess = ref(false);
      const comunas = [
      'COLINA', 'LAMPA', 'TIL_TIL', 'PIRQUE', 'PUENTE_ALTO', 'SAN_JOSE_DE_MAIPO',
      'BUIN', 'CALERA_DE_TANGO', 'PAINE', 'SAN_BERNARDO', 'ALHUE', 'CURACAVI',
      'MARIA_PINTO', 'MELIPILLA', 'SAN_PEDRO', 'CERRILLOS', 'CERRO_NAVIA',
      'CONCHALI', 'EL_BOSQUE', 'ESTACION_CENTRAL', 'HUECHURABA', 'INDEPENDENCIA',
      'LA_CISTERNA', 'LA_GRANJA', 'LA_FLORIDA', 'LA_PINTANA', 'LA_REINA',
      'LAS_CONDES', 'LO_BARNECHEA', 'LO_ESPEJO', 'LO_PRADO', 'MACUL', 'MAIPU',
      'NUNOA', 'PEDRO_AGUIRRE_CERDA', 'PENALOLEN', 'PROVIDENCIA', 'PUDAHUEL',
      'QUILICURA', 'QUINTA_NORMAL', 'RECOLETA', 'RENCA', 'SAN_MIGUEL',
      'SAN_JOAQUIN', 'SAN_RAMON', 'SANTIAGO', 'VITACURA', 'EL_MONTE',
      'ISLA_DE_MAIPO', 'PADRE_HURTADO', 'PENAFLOR', 'TALAGANTE'
    ];
  
      const publishProperty = async () => {
        if (action.value && address.value.line1 && address.value.line2 && 
        address.value.line3 && address.value.line4 && address.value.line5) {
        try {
          const response = await fetch('http://localhost:8080/inmuebles/terreno', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              action: action.value,
              address: {
                line1: address.value.line1,
                comuna: address.value.comuna,
                line2: address.value.line2,
                line3: address.value.line3,
                line4: address.value.line4,
                line5: address.value.line5
              }
            }),
          });

        if (response) {
            isSuccess.value = true;
            message.value = "Su publicación está a la espera de ser verificada";
          } else {
            throw new Error('Error en la solicitud');
          }
        } catch (error) {
          isSuccess.value = false;
          message.value = "Ocurrió un error al intentar publicar su terreno";
        }
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
        publishProperty,
        comunas
      };
    }
  }
  </script>
  
  <style scoped>
  .seleccionar-terreno {
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

  select {
  padding: 12px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  background-color: #e4f0ff;
  color: #7a8ca7;
  width: 100%;
  margin-bottom: 2px;
}

select:focus {
  outline: none;
  box-shadow: 0 0 0 2px #ff6b35;
}
  </style>
  
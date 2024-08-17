<template>
  <div class="seleccionar-terreno">
    <h2>¿Qué deseas hacer con tu propiedad?</h2>
    <div class="options">
      <label class="radio-option">
        <input type="radio" v-model="action" value="vender" />
        Vender
      </label>
      <label class="radio-option">
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
      <div class="patio-option">
        <span>¿Cuenta con servicios básicos?</span>
        <div class="button-group">
          <button @click="setServicios(true)" :class="{ active: address.tieneServicios === true }">Sí</button>
          <button @click="setServicios(false)" :class="{ active: address.tieneServicios === false }">No</button>
        </div>
      </div>
    </div>
    <button @click="publicarPropiedad" class="publicar-button">Publicar Terreno</button>
  <p v-if="message" :class="{'success-message': isSuccess, 'error-message': !isSuccess}">
    {{ message }}
  </p>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  name: 'SeleccionarDatosT',
  setup() {
    const router = useRouter();
    const navigateTo = (path) => {
      router.push(path);
    };
    
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

    const publicarPropiedad = async () => {
      if (action.value && address.value.line1 && address.value.comuna && address.value.line2 &&
          address.value.line3 && address.value.line4 && address.value.tieneServicios != null) { // Validar campos requeridos
        try {
          // Crear objeto con los datos del terreno
          const DatosTerreno = { 
            tipoOperacion: action.value, // Obtener tipo de operación
            direccion: address.value.line1, // Obtener dirección
            comuna: address.value.comuna, // Obtener comuna
            precio: parseInt(address.value.line2), // Obtener valor
            metrosCuadrados: parseInt(address.value.line3), // Obtener m2 del interior
            tipoSuelo: address.value.line4, // Obtener el tipo de suelo
            tieneServiciosBasicos: address.value.tieneServicios// Obtener si tiene servicios básicos
          };
        const response = await axios.post('http://localhost:8080/inmuebles/terreno', DatosTerreno); // Enviar datos al servidor
        if (response.status === 200 || response.status === 201) { // Verificar si la respuesta es exitosa
            isSuccess.value = true;
            message.value = "Su publicación ha sido guardada exitosamente";
          } else {
            throw new Error('Error al guardar la publicación');
          }
        } catch (error) {
          console.error('Error al publicar la propiedad:', error);
          isSuccess.value = false;
          message.value = "Hubo un error al guardar la publicación. Por favor, intente nuevamente.";
        }
      } else {
        isSuccess.value = false;
        message.value = "Por favor, complete todos los campos requeridos";
      }
    };

    const action = ref('');
    const address = ref({
      line1: '',
      comuna: '',
      line2: '',
      line3: '',
      line4: '',
      tieneServicios: null
    });

    const setServicios = (value) => {
      address.value.tieneServicios = value;
    };

    return {
      navigateTo,
      action,
      address,
      message,
      isSuccess,
      publicarPropiedad,
      comunas,
      setServicios
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

  input[type="text"], select {
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

  .patio-option {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.button-group {
  display: flex;
  gap: 10px;
}

.button-group button {
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: #e4f0ff;
  color: #022b60;
  cursor: pointer;
}

.button-group button.active {
  background-color: #ff6b35;
  color: #fff;
}

.publicar-button {
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  background-color: #f7c545;
  color: #022b60;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  display: block;
  margin: 20px auto;
  transition: background-color 0.3s;
}

  .publicar-button:hover {
    background-color: #f9d676;
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

  select:focus {
  outline: none;
  box-shadow: 0 0 0 2px #ff6b35;
  }
</style>

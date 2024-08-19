<template>
    <div class="seleccionar-depa">
      <h2>¿Qué deseas hacer con tu propiedad?</h2>
      <div class="opciones">
        <label class="radio-option">
          <input type="radio" v-model="accion" value="vender" />
          Vender
        </label>
        <label class="radio-option">
          <input type="radio" v-model="accion" value="arrendar" />
          Arrendar
        </label>
      </div>
      <div class="entrada-datos-depa">
      <h3>Ingresa los datos de tu propiedad</h3>
      <input type="text" v-model="datosDepa.line1" placeholder="Dirección" />
      <select v-model="datosDepa.comuna">
        <option value="">Selecciona una comuna</option>
        <option v-for="comuna in comunas" :key="comuna" :value="comuna">
          {{ comuna.replace(/_/g, ' ') }}
        </option>
      </select>
      <input type="text" v-model="datosDepa.line2" placeholder="Valor (pesos)" />
      <input type="text" v-model="datosDepa.line3" placeholder="m2 del departamento" />
      <input type="text" v-model="datosDepa.line4" placeholder="Número de piso del departamento" />
      <div class="ascensor-option">
        <span>¿El edificio cuenta con ascensor?</span>
        <div class="button-group">
          <button @click="establecerAscensor(true)" :class="{ active: datosDepa.tieneAscensor === true }">Sí</button>
          <button @click="establecerAscensor(false)" :class="{ active: datosDepa.tieneAscensor === false }">No</button>
        </div>
      </div>
    </div>
    <button @click="publicarPropiedad" class="publicar-button">Publicar Departamento</button>
    <p v-if="message" :class="{'success-message': isSuccess, 'error-message': !isSuccess}">
      {{ message }}
    </p>
  </div>
</template>
  
<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  name: 'SeleccionarDatosD',
  setup() {
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

    const validarCampoNumerico = (dato, nombreDato) => {
      if (isNaN(datosDepa.value[dato]) || datosDepa.value[dato] === '') {
        isSuccess.value = false;
        message.value = 'Por favor, ingrese un valor numérico en el campo de ' + nombreDato;
        datosDepa.value[dato] = ''; // Limpiar el campo
        return false;
      }
      return true;
    };

    const publicarPropiedad = async () => {
      // Verificar si los campos están completos
      if (accion.value && datosDepa.value.line1 && datosDepa.value.comuna && datosDepa.value.line2 &&
          datosDepa.value.line3 && datosDepa.value.line4 && datosDepa.tieneAscensor !== null){
        const datoNumerico = [
            { dato: 'line2', nombreDato: 'Valor (pesos)' },
            { dato: 'line3', nombreDato: 'm2 del interior' },
            { dato: 'line4', nombreDato: 'Número de piso del departamento' }
          ];

          for (const { dato, nombreDato } of datoNumerico) {
            if (!validarCampoNumerico(dato, nombreDato)) {
              return;
            }
          }
          try {
            // Crear objeto con los datos del departamento
            const DatosDepa = { 
              tipoOperacion: accion.value, // Obtener tipo de operación
              direccion: datosDepa.value.line1, // Obtener dirección
              comuna: datosDepa.value.comuna, // Obtener comuna
              precio: parseInt(datosDepa.value.line2), // Obtener valor
              metrosCuadrados: parseInt(datosDepa.value.line3), // Obtener m2 del interior
              tieneAscensor: datosDepa.value.tieneAscensor, // Obtener si tiene ascensor
              piso: parseInt(datosDepa.value.line4), // Obtener número de piso del departamento  // Obtener si tiene patio
              idUsuario: localStorage.getItem('userId')
            };
            const response = await axios.post('http://localhost:8080/inmuebles/departamento', DatosDepa); // Enviar datos al servidor
            if (response.status === 200 || response.status === 201) { // Verificar si la respuesta es exitosa
                isSuccess.value = true;
                message.value = "Su publicación ha sido guardada exitosamente";
              } else {
                throw new Error('Error al guardar la publicación');
              }
          } 
          catch (error) {
              console.error('Error al publicar la propiedad:', error);
              isSuccess.value = false;
              message.value = "Hubo un error al guardar la publicación. Por favor, intente nuevamente.";
            }
        // Si no están completos, mostrar mensaje de error
      } else {
          isSuccess.value = false;
          message.value = "Por favor, complete todos los campos requeridos";
        }
    };

    const accion = ref('');
    const datosDepa = ref({
      line1: '',
      comuna: '',
      line2: '',
      line3: '',
      line4: '',
      tieneAscensor: null
    });

    const establecerAscensor = (value) => {
      datosDepa.value.tieneAscensor = value;
    };

    return {
      accion,
      datosDepa,
      message,
      isSuccess,
      publicarPropiedad,
      comunas,
      establecerAscensor
    };
  }
}
</script>
  
  <style scoped>
  .seleccionar-depa {
    background-color: #022b60;
    color: #e4f0ff;
    padding: 40px;
    border-radius: 10px;
    max-width: 600px;
    margin: 40px auto;
  }
  
  h2 {
    text-align: center;
    margin-bottom: 30px;
    font-size: 24px;
  }
  
  .opciones {
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
  
  .entrada-datos-depa {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .entrada-datos-depa h3 {
    font-size: 20px;
    margin-bottom: 20px;
    text-align: center;
  }
  
  input[type="text"] ,select {
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

  .ascensor-option {
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
  background-color: #009908;
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
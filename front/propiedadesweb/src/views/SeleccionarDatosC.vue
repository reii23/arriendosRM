<template>
  <div class="seleccionar-casa">

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
      <input type="text" v-model="datosCasa.line1" placeholder="Dirección" />
      <select v-model="datosCasa.comuna">
        <option value="">Selecciona una comuna</option>
        <option v-for="comuna in comunas" :key="comuna" :value="comuna">
            {{ comuna.replace(/_/g, ' ') }}
        </option>
      </select>
      <input type="text" v-model="datosCasa.line2" placeholder="Valor (pesos)" />
      <input type="text" v-model="datosCasa.line3" placeholder="m² del interior" />
      <input type="text" v-model="datosCasa.line4" placeholder="Cantidad de pisos" />
      <div class="patio-option">
        <span>¿Tiene patio?</span>
        <div class="button-group">
          <button @click="establecerPatio(true)" :class="{ active: datosCasa.tienePatio === true }">Sí</button>
          <button @click="establecerPatio(false)" :class="{ active: datosCasa.tienePatio === false }">No</button>
        </div>
      </div>
    </div>

    <button @click="publicarPropiedad" class="publicar-button">Publicar Casa</button>
    <p v-if="message" :class="{'success-message': isSuccess, 'error-message': !isSuccess}">
      {{ message }}
    </p>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  nombre: 'SeleccionarDatosC', 
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
      if (isNaN(datosCasa.value[dato]) || datosCasa.value[dato] === '') {
        isSuccess.value = false;
        message.value = 'Por favor, ingrese un valor numérico en el campo de ' + nombreDato;
        datosCasa.value[dato] = ''; // Limpiar el campo
        return false;
      }
      return true;
    };

    const publicarPropiedad = async () => {
      // Si los campos están completos
      if (accion.value && datosCasa.value.line1 && datosCasa.value.comuna && datosCasa.value.line2 &&
          datosCasa.value.line3 && datosCasa.value.line4 && datosCasa.value.tienePatio !== null) {
          
          const datoNumerico = [
            { dato: 'line2', nombreDato: 'Valor (pesos)' },
            { dato: 'line3', nombreDato: 'm² del interior' },
            { dato: 'line4', nombreDato: 'Cantidad de pisos' }
          ];

          for (const { dato, nombreDato } of datoNumerico) {
            if (!validarCampoNumerico(dato, nombreDato)) {
              return;
            }
          }
          try {
            // Crear objeto con los datos de la casa
            const DatosCasa = {
              tipoOperacion: accion.value, // Obtener tipo de operación (vender o arrendar)
              direccion: datosCasa.value.line1, // Obtener dirección
              comuna: datosCasa.value.comuna, // Obtener comuna
              precio: parseInt(datosCasa.value.line2), // Obtener precio
              metrosCuadrados: parseInt(datosCasa.value.line3), // Obtener m2 del interior
              numPisos: parseInt(datosCasa.value.line4), // Obtener cantidad de pisos
              tienePatio: datosCasa.value.tienePatio, // Obtener si tiene patio
              idUsuario: localStorage.getItem('userId') // Obtener id del usuario
            };
            const response = await axios.post('http://localhost:8080/inmuebles/casa', DatosCasa); // Enviar datos al servidor
            if (response.status === 200 || response.status === 201) { // Verificar si la respuesta es exitosa
              isSuccess.value = true;
              message.value = "Su publicación ha sido guardada exitosamente";
            } else {
              throw new Error('Error al guardar la publicación');
            }
          } 
          catch (error) { // Manejar errores
            console.error('Error al publicar la propiedad:', error);
            isSuccess.value = false;
            message.value = "Hubo un error al guardar la publicación. Por favor, intente nuevamente.";
          }
        // Si los campos no están completos, mostrar mensaje de error
      } else { 
        isSuccess.value = false;
        message.value = "Por favor, complete todos los campos requeridos";
        }
    };
    
    const accion = ref(''); 
    const datosCasa = ref({
      line1: '',
      comuna: '',
      line2: '',
      line3: '',
      line4: '',
      tienePatio: null
    });

    const establecerPatio = (value) => {
      datosCasa.value.tienePatio = value;
    };

    return {
      accion,
      datosCasa: datosCasa,
      message,
      isSuccess,
      publicarPropiedad,
      comunas,
      establecerPatio: establecerPatio
    };
  }
}
</script>

<style scoped>
.seleccionar-casa {
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
  gap: 80px;
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
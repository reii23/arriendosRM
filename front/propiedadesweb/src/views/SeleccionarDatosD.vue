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
      <!-- Agregar formulario para ingresar datos de la propiedad -->
      </div>
      <div class="entrada-datos-depa">
      <h3>Ingresa los datos de tu propiedad</h3>
      <input type="text" v-model="datosDepa.linea1" placeholder="Dirección" />
      <select v-model="datosDepa.comuna">
        <option value="">Selecciona una comuna</option>
        <option v-for="comuna in comunas" :key="comuna" :value="comuna">
          {{ comuna.replace(/_/g, ' ') }}
        </option>
      </select>
      <input type="text" v-model="datosDepa.linea2" placeholder="Valor (pesos)" />
      <input type="text" v-model="datosDepa.linea3" placeholder="m2 del departamento" />
      <input type="text" v-model="datosDepa.linea4" placeholder="Número de piso del departamento" />
      <div class="ascensor-option">
        <span>¿El edificio cuenta con ascensor?</span>
        <div class="botones">
          <button @click="establecerAscensor(true)" :class="{ active: datosDepa.tieneAscensor === true }">Sí</button>
          <button @click="establecerAscensor(false)" :class="{ active: datosDepa.tieneAscensor === false }">No</button>
        </div>
      </div>
    </div>
    <button @click="publicarPropiedad" class="publicar-button">Publicar Departamento</button>
    <p v-if="mensaje" :class="{'mensaje-exito': esCorrecto, 'mensaje-error': !esCorrecto}">
      {{ mensaje }}
    </p>
  </div>
</template>
  
<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  name: 'SeleccionarDatosD',
  setup() {
    const mensaje = ref(''); // Crear referencia reactiva para el mensaje
    const esCorrecto = ref(false); // Crear referencia reactiva para el estado de éxito

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

    // Función para validar campos numéricos
    const validarCampoNumerico = (dato, nombreDato) => {
      if (isNaN(datosDepa.value[dato]) || datosDepa.value[dato] === '') { // Verificar si el campo no es numérico o está vacío
        esCorrecto.value = false; // Establecer estado de éxito en falso
        mensaje.value = 'Por favor, ingrese un valor numérico en el campo de ' + nombreDato; // Mostrar mensaje de error indicando el campo
        datosDepa.value[dato] = ''; // Limpiar el campo
        return false;
      }
      return true;
    };

    // Función para verificar si la dirección ya existe
    const verificarDireccion = async () => {
    try {
      // Enviar petición al servidor a través de path param
      const response = await axios.get(`http://localhost:8080/inmuebles/verificar-direccion?direccion=${datosDepa.value.linea1}`);
      return response.data; // Retornar respuesta del servidor
    } catch (error) {
      console.error('Error al verificar la dirección:', error); // Manejar errores en consola
      return false;
    }
  };

    const publicarPropiedad = async () => {
      // Verificar si los campos están completos
      if (accion.value && datosDepa.value.linea1 && datosDepa.value.comuna && datosDepa.value.linea2 &&
          datosDepa.value.linea3 && datosDepa.value.linea4 && datosDepa.tieneAscensor !== null){
        
            // Verificar si la dirección ya existe
        const direccionExiste = await verificarDireccion();
          if (direccionExiste) {
            esCorrecto.value = false;
            mensaje.value = "La dirección ingresada ya se encuentra registrada. Por favor, ingrese una dirección diferente.";
            return;
          }

          const datoNumerico = [
            { dato: 'linea2', nombreDato: 'Valor (pesos)' },
            { dato: 'linea3', nombreDato: 'm2 del interior' },
            { dato: 'linea4', nombreDato: 'Número de piso del departamento' }
          ];

          for (const { dato, nombreDato } of datoNumerico) { // Iterar sobre los datos numéricos
            if (!validarCampoNumerico(dato, nombreDato)) { // Verificar si el campo es numérico
              return;
            }
          }
          try {
            // Crear objeto con los datos del departamento
            const DatosDepa = { 
              tipoOperacion: accion.value, // Obtener tipo de operación
              direccion: datosDepa.value.linea1, // Obtener dirección
              comuna: datosDepa.value.comuna, // Obtener comuna
              precio: parseInt(datosDepa.value.linea2), // Obtener valor
              metrosCuadrados: parseInt(datosDepa.value.linea3), // Obtener m2 del interior
              tieneAscensor: datosDepa.value.tieneAscensor, // Obtener si tiene ascensor
              piso: parseInt(datosDepa.value.linea4), // Obtener número de piso del departamento  // Obtener si tiene patio
              idUsuario: localStorage.getItem('userId')
            };
            const response = await axios.post('http://localhost:8080/inmuebles/departamento', DatosDepa); // Enviar datos al servidor
            if (response.status === 200 || response.status === 201) { // Verificar si la respuesta es exitosa
                esCorrecto.value = true;
                mensaje.value = "Su publicación ha sido guardada exitosamente";
              } else {
                throw new Error('Error al guardar la publicación');
              }
          } 
          catch (error) {
              console.error('Error al publicar la propiedad:', error);
              esCorrecto.value = false;
              mensaje.value = "Hubo un error al guardar la publicación. Por favor, intente nuevamente.";
            }
        // Si no están completos, mostrar mensaje de error
      } else {
          esCorrecto.value = false;
          mensaje.value = "Por favor, complete todos los campos requeridos";
        }
    };

    const accion = ref(''); // Crear referencia reactiva para la acción (vender o arrendar)
    const datosDepa = ref({ // Crear referencia reactiva para los datos del departamento
      linea1: '',
      comuna: '',
      linea2: '',
      linea3: '',
      linea4: '',
      tieneAscensor: null
    });

    const establecerAscensor = (value) => {
      datosDepa.value.tieneAscensor = value; // Establecer si el departamento tiene ascensor
    };

    return {
      accion,
      datosDepa,
      mensaje,
      esCorrecto,
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

.botones {
  display: flex;
  gap: 10px;
}


.botones button {
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: #e4f0ff;
  color: #022b60;
  cursor: pointer;
}

.botones button.active {
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

.mensaje-exito {
  color: #28a745;
  text-align: center;
  margin-top: 20px;
}

.mensaje-error {
  color: #dc3545;
  text-align: center;
  margin-top: 20px;
}

select:focus {
  outline: none;
  box-shadow: 0 0 0 2px #ff6b35;
}
</style>
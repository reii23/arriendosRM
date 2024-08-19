<template>
  <div class="seleccionar-terreno">
    <h2>¿Qué deseas hacer con tu propiedad?</h2>
    <div class="opciones">
      <label class="radio-option">
        <input type="radio" v-model="action" value="vender" />
        Vender
      </label>
      <label class="radio-option">
        <input type="radio" v-model="action" value="arrendar" />
        Arrendar
      </label>
    </div>
    <div class="entrada-datos-depa">
      <h3>Ingresa los datos de tu propiedad</h3>
      <input type="text" v-model="datosTerreno.linea1" placeholder="Dirección" />
      <select v-model="datosTerreno.comuna">
      <option value="">Selecciona una comuna</option>
      <option v-for="comuna in comunas" :key="comuna" :value="comuna">
        {{ comuna.replace(/_/g, ' ') }}
      </option>
    </select>
      <input type="text" v-model="datosTerreno.linea2" placeholder="Valor (pesos)" />
      <input type="text" v-model="datosTerreno.linea3" placeholder="m2 del terreno" />
      <input type="text" v-model="datosTerreno.linea4" placeholder="Tipo de suelo" />
      <div class="patio-option">
        <span>¿Cuenta con servicios básicos?</span>
        <div class="botones">
          <button @click="establecerServicios(true)" :class="{ active: datosTerreno.tieneServicios === true }">Sí</button>
          <button @click="establecerServicios(false)" :class="{ active: datosTerreno.tieneServicios === false }">No</button>
        </div>
      </div>
    </div>
    <button @click="publicarPropiedad" class="publicar-button">Publicar Terreno</button>
  <p v-if="mensaje" :class="{'mensaje-exito': esCorrecto, 'mensaje-error': !esCorrecto}">
    {{ mensaje }}
  </p>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  name: 'SeleccionarDatosT',
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
      if (isNaN(datosTerreno.value[dato]) || datosTerreno.value[dato] === '') {  // Verificar si el valor no es un número o está vacío
        esCorrecto.value = false; // Establecer el estado de éxito como falso
        mensaje.value = 'Por favor, ingrese un valor numérico en el campo de ' + nombreDato; // Mostrar mensaje de error indicando el campo
        datosTerreno.value[dato] = ''; // Limpiar el campo
        return false;
      }
      return true;
    };

    const publicarPropiedad = async () => {
      // Verificar si los campos requeridos están completos
      if (accion.value && datosTerreno.value.linea1 && datosTerreno.value.comuna && datosTerreno.value.linea2 &&
          datosTerreno.value.linea3 && datosTerreno.value.linea4 && datosTerreno.value.tieneServicios != null) { // Validar campos requeridos
        const datoNumerico = [
          { dato: 'linea2', nombreDato: 'Valor (pesos)' },
          { dato: 'linea3', nombreDato: 'm2 del interior' },
        ];

        for (const { dato, nombreDato } of datoNumerico) { // Iterar sobre los campos numéricos
          if (!validarCampoNumerico(dato, nombreDato)) { // Verificar si el campo es numérico
            return;
          }
        }
        try {
          // Crear objeto con los datos del terreno
          const DatosTerreno = { 
            tipoOperacion: accion.value, // Obtener tipo de operación
            direccion: datosTerreno.value.linea1, // Obtener dirección
            comuna: datosTerreno.value.comuna, // Obtener comuna
            precio: parseInt(datosTerreno.value.linea2), // Obtener valor
            metrosCuadrados: parseInt(datosTerreno.value.linea3), // Obtener m2 del interior
            tipoSuelo: datosTerreno.value.linea4, // Obtener el tipo de suelo
            tieneServiciosBasicos: datosTerreno.value.tieneServicios, // Obtener si tiene servicios básicos
            idUsuario: localStorage.getItem('userId')
          };
        const response = await axios.post('http://localhost:8080/inmuebles/terreno', DatosTerreno); // Enviar datos al servidor
        if (response.status === 200 || response.status === 201) { // Verificar si la respuesta es exitosa
            esCorrecto.value = true;
            mensaje.value = "Su publicación ha sido guardada exitosamente";
          } else {
            throw new Error('Error al guardar la publicación');
          }
        } catch (error) {
              console.error('Error al publicar la propiedad:', error);
              esCorrecto.value = false;
              mensaje.value = "Hubo un error al guardar la publicación. Por favor, intente nuevamente.";
            }
            
      } else {
            esCorrecto.value = false;
            mensaje.value = "Por favor, complete todos los campos requeridos";
          }
    };
      
    const accion = ref(''); // Crear referencia reactiva para la acción
    const datosTerreno = ref({ // Crear referencia reactiva para los datos del terreno
      linea1: '',
      comuna: '',
      linea2: '',
      linea3: '',
      linea4: '',
      tieneServicios: null
    });

    const establecerServicios = (value) => {
      datosTerreno.value.tieneServicios = value; // Establecer si tiene servicios básicos
    };

    return {
      action: accion,
      datosTerreno,
      mensaje: mensaje,
      esCorrecto: esCorrecto,
      publicarPropiedad,
      comunas,
      establecerServicios
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

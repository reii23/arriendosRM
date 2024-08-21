<template>
    <div class="anio">{{ anioActual }}</div>
    <div class="calendario-container">
        <button @click="irSemanaAnterior" class="navegacion">&lt;</button>
        <div class="dias-container">
            <div v-for="(dia, index) in diasVisibles" :key="index"
                :class="['dia', { 'seleccionado': dia.seleccionado, 'sin-horas': !dia.hayHoras, 'con-horas': dia.hayHoras }]"
                @click="seleccionarDia(dia, index)">
                <p class="dia-nombre">{{ dia.nombre }}</p>
                <p class="fecha">{{ dia.fecha }}</p>
            </div>
        </div>
        <button @click="irSemanaSiguiente" class="navegacion">&gt;</button>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'Calendario',
    data() {
        return {
            fechaActual: new Date(),
            diasVisibles: [],
            totalDiasVisibles: 7, // Esto define cuántos días se muestran a la vez
            fechaSeleccionada: null,
            hayHoras: null
        };
    },
    computed: {
        anioActual() {
            // Obtener el año del primer día visible
            return this.diasVisibles.length > 0 ? this.diasVisibles[0].fechaCompleta.getFullYear() : new Date().getFullYear();
        }
    },
    created() {
        this.generarDias();
        this.generarHayHoras();
    },
    methods: {
        async generarHayHoras() {
            // Obtengo el id del inmueble por URL
            const idInmueble = this.$route.params.id;
            this.diasVisibles.forEach(async dia => {
                // Obtengo las fechas 
                const fecha = dia.fechaFormateada;
                const response = await axios.get(`http://localhost:8080/horarioVisita/obtenerHorariosVisitaPorFecha/${idInmueble}/${fecha}`);
                dia.hayHoras = response.data.length > 0;
            });
        },
        generarDias() {
            const dias = [];
            const primerDia = this.fechaActual.getDate() - this.fechaActual.getDay() + 1; // Empezar en lunes
            const lunes = new Date(this.fechaActual.setDate(primerDia));

            for (let i = 0; i < this.totalDiasVisibles; i++) {
                const fecha = new Date(lunes);
                fecha.setDate(lunes.getDate() + i);
                dias.push({
                    fecha: this.formatoFecha(fecha),
                    fechaCompleta: fecha, // Guardar la fecha completa para uso posterior
                    nombre: this.formatoDia(fecha),
                    seleccionado: i === 0, // El primer día es el seleccionado por defecto
                    hayHoras: null,
                    fechaFormateada: this.formatoFechaCompleta(fecha)
                });
            }
            this.diasVisibles = dias;
            this.seleccionarDia(dias[0], 0);
        },
        formatoFecha(fecha) {
            const opciones = { month: 'numeric', day: 'numeric' };
            return fecha.toLocaleDateString('es-ES', opciones);
        },
        formatoDia(fecha) {
            const opciones = { weekday: 'long' };
            return fecha.toLocaleDateString('es-ES', opciones).charAt(0).toUpperCase() + fecha.toLocaleDateString('es-ES', opciones).slice(1);
        },
        seleccionarDia(dia, indice) {
            this.diasVisibles.forEach((d, i) => d.seleccionado = i === indice);
            this.fechaSeleccionada = dia.fechaFormateada;
            this.$emit('fechaSeleccionada', dia.fechaFormateada); // Emitir el evento para la fecha seleccionada
        },
        formatoFechaCompleta(fecha) {
            const dia = String(fecha.getDate()).padStart(2, '0');
            const mes = String(fecha.getMonth() + 1).padStart(2, '0'); // Los meses comienzan en 0
            const anio = fecha.getFullYear();
            return `${dia}-${mes}-${anio}`;
        },
        irSemanaAnterior() {
            this.fechaActual.setDate(this.fechaActual.getDate() - this.totalDiasVisibles);
            this.generarDias();
            this.generarHayHoras();
        },
        irSemanaSiguiente() {
            this.fechaActual.setDate(this.fechaActual.getDate() + this.totalDiasVisibles);
            this.generarDias();
            this.generarHayHoras();
        }
    },
    watch: {
        fechaActual() {
            // Cada vez que cambie la fecha actual, se genera nuevamente la semana
            this.generarDias();
        }
    }
}
</script>

<style scoped>
.calendario-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
}

.anio {
    font-size: 24px;
    margin-bottom: 10px;
}

.dias-container {
    display: flex;
    flex-grow: 1;
    justify-content: space-around;
}

.dia {
    text-align: center;
    padding: 10px;
    border-radius: 20px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.dia.seleccionado {
    background-color: #e69b39;
    color: rgb(255, 208, 138);
}

.dia p {
    margin: 0;
    color: #ffffff;
    font-weight: 700;
    font-size: 18px;
}

.navegacion {
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
    color: #e69b39;
}

.dia:hover {
    background-color: #f2f2f2;
}

.dia.sin-horas {
    opacity: 0.8;
}

.dia.con-horas {
    border: 2px solid #4CAF50;
    /* Borde verde */
    color: #00695c;
    /* Texto oscuro */
}
</style>
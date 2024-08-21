<template>
    <div class="anio">{{ anioActual }}</div>
    <div class="calendario-container">
        <button @click="irSemanaAnterior" class="navegacion">&lt;</button>
        <div class="dias-container">
            <div v-for="(dia, index) in diasVisibles" :key="index"
                :class="['dia', { 'seleccionado': dia.seleccionado, 'sin-horas': !dia.hayHoras }]"
                @click="seleccionarDia(dia, index)">
                <p class="dia-nombre">{{ dia.nombre }}</p>
                <p class="fecha">{{ dia.fecha }}</p>
            </div>
        </div>
        <button @click="irSemanaSiguiente" class="navegacion">&gt;</button>
    </div>
</template>

<script>
export default {
    name: 'Calendario',
    data() {
        return {
            fechaActual: new Date(),
            diasVisibles: [],
            totalDiasVisibles: 7, // Esto define cuántos días se muestran a la vez
            fechaSeleccionada: null
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
    },
    methods: {
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
            this.diasVisibles.forEach((dia, i) => dia.seleccionado = i === indice);
            const fechaFormateada = this.formatoFechaCompleta(dia.fechaCompleta);
            console.log(fechaFormateada);
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
        },
        irSemanaSiguiente() {
            this.fechaActual.setDate(this.fechaActual.getDate() + this.totalDiasVisibles);
            this.generarDias();
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
    background-color: #9b51e0;
    color: white;
}

.dia p {
    margin: 0;
    font-size: 16px;
}

.navegacion {
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
}

.dia:hover {
    background-color: #f2f2f2;
}

.dia.sin-horas {
    opacity: 0.5;
}
</style>
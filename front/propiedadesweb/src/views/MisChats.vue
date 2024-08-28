<template>
    <div class="chat-page">
        <div class="chat-list-container">
            <h1>Mis Chats</h1>
            <div class="view-buttons">
                <button :class="{ 'active': vistaActual === 'cliente' }" @click="cambiarVista('cliente')">
                    MisChatsCliente
                </button>
                <button :class="{ 'active': vistaActual === 'propietario' }" @click="cambiarVista('propietario')">
                    MisChatsPropietario
                </button>
            </div>
            <div class="chat-list" v-if="vistaActual === 'cliente'">
                <div v-for="chat in chats" :key="chat.id" class="chat-item" @click="abrirChat(chat)">
                    <div class="chat-info">
                        <p class="chat-title-2">Direccion: {{ chat.nombreInmueble }}</p>
                        <p class="chat-title">Nombre Propietario: {{ chat.nombreUsuario }}</p>
                    </div>
                    <p class="chat-time">13:45</p>
                </div>
            </div>
            <div class="chat-list" v-if="vistaActual === 'propietario'">
                <div v-for="chat in chatsPropietario" :key="chat.id" class="chat-item"
                    @click="abrirChatPropietario(chat)">
                    <div class="chat-info">
                        <p class="chat-title-2"> Mi vivienda: {{ chat.nombreInmueble }}</p>
                        <p class="chat-title">Nombre Consultante: {{ chat.nombreUsuario }}</p>
                    </div>
                </div>

            </div>
        </div>
        <div class="chat-window">
            <!-- Aquí se muestra la conversación seleccionada -->
            <div v-if="chatSeleccionado">
                <div class="chat-header">
                    <h2>Conversación con {{ chatSeleccionado.nombreUsuario }}</h2>
                    <h2>En el inmueble: {{ chatSeleccionado.nombreInmueble }}</h2>
                </div>
                <div ref="messagesContainer" class="messages-container">
                    <div v-for="mensaje in mensajes" :key="mensaje.id"
                        :class="['message-item', mensaje.remitente === 'Tú' ? 'message-sent' : 'message-received']">
                        <p><strong>{{ mensaje.remitente }}:</strong> {{ mensaje.mensaje }}</p>
                    </div>
                </div>
                <div class="message-input-container">
                    <input v-model="nuevoMensaje" type="text" placeholder="Escribe un mensaje..."
                        @keyup.enter="enviarMensaje" />
                    <button @click="enviarMensaje">Enviar</button>
                </div>
            </div>
            <p v-else>Seleccione un chat para comenzar la conversación</p>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'MisChats',
    data() {
        return {
            chats: [],
            chatsPropietario: [],
            chatSeleccionado: null,
            mensajes: [],
            nuevoMensaje: '', // Nuevo mensaje a enviar
            vistaActual: '' // Vista actual
        }
    },
    async created() {
        await this.cargarChats();
        await this.cargarChatsPropietario();
    },
    methods: {
        cambiarVista(vista) {
            this.vistaActual = vista;
            this.cargarChats();
        },
        async cargarChatsPropietario() {
            const idUsuario = localStorage.getItem('userId');
            const response = await axios.get(`http://localhost:8080/chat/obtenerChatPorIdUsuario1/${idUsuario}`);
            this.chatsPropietario = await Promise.all(response.data.map(async chat => {
                const usuarioResponse = await axios.get(`http://localhost:8080/user/obtenerUsuarioPorId/${chat.idUsuario2}`);
                const inmuebleResponse = await axios.get(`http://localhost:8080/inmuebles/${chat.idInmueble}`);
                chat.nombreUsuario = usuarioResponse.data.nombre;
                chat.nombreInmueble = inmuebleResponse.data.direccion;
                return chat;
            }));
        },
        async cargarChats() {
            const idUsuario = localStorage.getItem('userId');
            const response = await axios.get(`http://localhost:8080/chat/obtenerChatPorIdUsuario2/${idUsuario}`);
            this.chats = await Promise.all(response.data.map(async chat => {
                const usuarioResponse = await axios.get(`http://localhost:8080/user/obtenerUsuarioPorId/${chat.idUsuario1}`);
                const inmuebleResponse = await axios.get(`http://localhost:8080/inmuebles/${chat.idInmueble}`);
                chat.nombreUsuario = usuarioResponse.data.nombre;
                chat.nombreInmueble = inmuebleResponse.data.direccion;
                return chat;
            }));
        },
        async abrirChat(chat) {
            this.chatSeleccionado = chat; // Guardar el chat seleccionado

            // Obtener la conversación
            const response = await axios.get(`http://localhost:8080/mensaje/obtenerMensajesPorChat/${chat.id}`);
            this.mensajes = response.data.map(mensaje => {
                // Determina el remitente con base en el idUsuario1 o idUsuario2
                mensaje.remitente = mensaje.idUsuario === chat.idUsuario1 ? chat.nombreUsuario : "Tú";
                return mensaje;
            });

            this.$nextTick(() => {
                const container = this.$refs.messagesContainer;
                container.scrollTop = container.scrollHeight; // Desplaza automáticamente al final
            });
        },
        async abrirChatPropietario(chat) {
            this.chatSeleccionado = chat; // Guardar el chat seleccionado

            // Obtener la conversación
            const response = await axios.get(`http://localhost:8080/mensaje/obtenerMensajesPorChat/${chat.id}`);
            this.mensajes = response.data.map(mensaje => {
                // Determina el remitente con base en el idUsuario1 o idUsuario2
                mensaje.remitente = mensaje.idUsuario === chat.idUsuario2 ? chat.nombreUsuario : "Tú";
                return mensaje;
            });

            this.$nextTick(() => {
                const container = this.$refs.messagesContainer;
                container.scrollTop = container.scrollHeight; // Desplaza automáticamente al final
            });
        },
        async enviarMensaje() {
            if (this.nuevoMensaje.trim() === '') return; // Verifica que el mensaje no esté vacío

            const mensaje = {
                idChat: this.chatSeleccionado.id,
                idUsuario: localStorage.getItem('userId'),
                mensaje: this.nuevoMensaje,
                fechaEnvio: new Date().toISOString() // Puedes ajustar el formato de fecha si es necesario
            };

            try {
                // Enviar el mensaje al servidor
                await axios.post('http://localhost:8080/mensaje/enviarMensaje', mensaje);

                // Agregar el mensaje a la lista de mensajes localmente
                this.mensajes.push({
                    ...mensaje,
                    remitente: 'Tú' // Marca este mensaje como enviado por el usuario
                });

                // Limpiar el campo de entrada
                this.nuevoMensaje = '';

                this.$nextTick(() => {
                    const container = this.$refs.messagesContainer;
                    container.scrollTop = container.scrollHeight; // Desplaza automáticamente al final
                });

            } catch (error) {
                // Digo al usuario que su mensaje no se pudo enviar por exceder el límite de caracteres
                alert('No se pudo enviar el mensaje. Es posible que su mensaje exceda el límite de caracteres permitido.');
            }
        }
    }
}
</script>

<style>
.chat-page {
    display: flex;
    /* Hago que el chat se ajuste a la pantalla */
    height: 90vh;
    background-color: #3d5a80;
    color: #EAEAEA;
}

.chat-list-container {
    width: 25%;
    background-color: #3d5a80;
    padding: 10px;
    border-right: 3px solid #293241;
}

.chat-list {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    /* Ajuste para la altura disponible */
}

.chat-item {
    display: flex;
    align-items: center;
    padding: 10px;
    margin-bottom: 5px;
    background-color: #323232;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.chat-item:hover {
    background-color: #ee6c4d;
}

.chat-header {
    padding: 20px;
    background-color: #3d5a80;
}

.chat-info {
    flex-grow: 1;
}

.chat-title-2 {
    font-weight: bold;
}

.chat-title {
    font-weight: 100;
    margin: 0;
}

.chat-last-message {
    color: #ffffff;
    margin: 0;
}

.chat-time {
    font-size: 0.9em;
    color: #ffffff;
}

.chat-window {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    background-color: #98c1d9;
    margin: 0;
}

.messages-container {
    flex-grow: 1;
    overflow-y: scroll;
    /* Permite el desplazamiento vertical */
    padding-right: 10px;
    padding-left: 10px;
    display: flex;
    flex-direction: column;
    max-height: calc(80vh - 100px);
}

.message-item {
    background-color: #e0fbfc;
    padding: 10px;
    margin: 5px 0;
    border-radius: 5px;
    max-width: 80%;
    /* Asegura que los mensajes no se extiendan demasiado */
}

.message-sent {
    /* Dejo los mensajes enviados a la derecha*/
    align-self: flex-end;
    background-color: #ee6c4d;

}

.message-received {
    /* Dejo los mensajes recibidos a la izquierda */
    align-self: flex-start;
    background-color: #454c58;
}

.message-input-container {
    display: flex;
    padding: 10px;
    background-color: #3d5a80;
    align-items: flex-end;
    position: relative;
}

.message-input-container input[type="text"] {
    flex-grow: 1;
    padding: 10px;
    border-radius: 5px;
    border: none;
    margin-right: 10px;
}

.message-input-container button {
    padding: 10px;
    background-color: #ee6c4d;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    color: #fff;
    font-weight: bold;
}

.view-buttons {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
}

.view-buttons button {
    padding: 10px;
    background-color: #ee6c4d;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    color: #fff;
    font-weight: bold;
    margin-right: 10px;
    transition: background-color 0.3s;
}

.view-buttons button.active {
    background-color: #ff1500; /* Color para el botón seleccionado */
}

</style>

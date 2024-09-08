## Requisitos Previos

- [Node.js](https://nodejs.org/)
- npm (Node Package Manager, viene con Node.js)
- [Vue CLI](https://cli.vuejs.org/) (instalar con `npm install -g @vue/cli`)

## Instalación

1. Clonar repositorio:
   ```bash
   git clone <https://github.com/reii23/fingeso>
   ```

2. Instalación de dependencias:
   ```bash
   npm install
   ```

3. Configuración de las variables de entorno en IntelliJ (se deben reemplazar con los valores)

   ```bash
   DB_USERNAME = nombredeusuario;DB_PASSWORD=contraseñadeusuario;DB_NAME=nombrebasededatos
   ```

## Ejecución

### Backend

El backend se ejecuta normalmente en `http://localhost:8080`.

### Poblar base de Datos

Se copian y pegan las inserciones contenidas en el archivo Querys.txt en la consola de pgAdmin4 (preferiblemente ejecutar por bloques). Se ejecuta el código para poblar las tablas.

### Frontend

Luego de ejecutar el backend, para ejecutar el frontend del proyecto:

```bash
npm run dev
```

```bash
npm run serve
```

Cualquiera de los dos comandos anteriores sirve, el frontend se ejecuta normalmente en `http://localhost:8081`.


## Puertos utilizados normalmente

- Backend: `localhost:8080`
- Frontend: `localhost:8081`

[Consultas Postman](https://documenter.getpostman.com/view/28812543/2sA3kd9HRZ)


## Operaciones Incorporadas en la Aplicación Web
- Registrar y logear usuario un usuario con roles (cliente, agente inmobiliario, administrador)
- Agregar una propiedad a favoritos
- Contactar al dueño de una propiedad a través de un chat
- Intercambiar mensajes entre arrendador y arrendatario a través del chat
- Top 10 inmuebles con mayor cantidad de me gusta
- Publicar un inmueble (casa, departamento o terreno)
- Agregar horarios de visita a un inmueble como agente inmobiliario
- Agendar o cancelar una visita a un inmueble como cliente
- Ver la información de cuenta con las visitas agendadas, inmuebles favoritos y propiedades de un cliente
- Filtrar inmuebles por un precio mínimo y máximo establecido

## Operaciones Sin Incorporar en la Aplicación Web
- Mostrar la ubicación de cada inmueble a través de un mapa que muestre su dirección
- Publicar inmuebles en masa a través de un archivo .csv

## Equipo de desarrollo:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/thxmohamed">
        <img src="https://avatars.githubusercontent.com/thxmohamed" width="100px;" alt="Mohamed Al-Marzuk"/>
        <br />
        <sub><b>Mohamed Al-Marzuk</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/VicenteArce">
        <img src="https://avatars.githubusercontent.com/VicenteArce" width="100px;" alt="Vicente Arce"/>
        <br />
        <sub><b>Vicente Arce</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Linna-Lpz">
        <img src="https://avatars.githubusercontent.com/Linna-Lpz" width="100px;" alt="Catalina López"/>
        <br />
        <sub><b>Catalina López</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/reii23">
        <img src="https://avatars.githubusercontent.com/reii23" width="100px;" alt="Reinaldo Pacheco"/>
        <br />
        <sub><b>Reinaldo Pacheco</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/BenjaZuniga">
        <img src="https://avatars.githubusercontent.com/BenjaZuniga" width="100px;" alt="Benjamín Zuñiga"/>
        <br />
        <sub><b>Benjamín Zuñiga</b></sub>
      </a>
    </td>
  </tr>
</table>


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

3. Configuración de las variables de entorno (se deben reemplazar con los valores)

```bash
   DB_USERNAME = nombredeusuario;DB_PASSWORD=contraseñadeusuario;DB_NAME=nombrebasededatos
   ```

## Ejecución

### Backend

El backend se ejecuta normalmente en `http://localhost:8080`.

### Frontend

luego de ejecutar el backend, para ejecutar el frontend del proyecto:

```bash
npm run dev
```

```bash
npm run serve
```

El frontend se ejecuta normalmente en `http://localhost:8081`.


## Puertos utilizados normalmente

- Backend: `localhost:8080`
- Frontend: `localhost:8081`

[Consultas Postman](https://documenter.getpostman.com/view/28812543/2sA3kd9HRZ)
[Inputs de SQL de prueba](https://gist.github.com/reii23/30e904464835e89719933720a28d0c9d)

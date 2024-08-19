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


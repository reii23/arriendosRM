import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/PaginaDeInicio.vue";
import About from "../views/SubsidiosInfo.vue";
import Inmuebles from "../views/Inmuebles.vue";
import Register from "../views/UsuarioRegistro.vue";
import Login from "../views/UsuarioInicioSesion.vue";
import PropertyDetail from "../views/DetallesPropiedad.vue";
import SeleccionarInmueble from "@/views/SeleccionarInmueble.vue";
import SeleccionarDatosC from "@/views/SeleccionarDatosC.vue";
import SeleccionarDatosD from "@/views/SeleccionarDatosD.vue";
import SeleccionarDatosT from "@/views/SeleccionarDatosT.vue";
import PublicacionCreada from "@/views/PublicacionCreada.vue";
import LogoutView from "@/views/UsuarioCerrarSesion.vue";
import AccountView from "@/views/UsuarioPerfil.vue";
import AgenteView from "@/views/AgenteVista.vue";
import MisChats from "@/views/MisChats.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: "/inmuebles",
    name: "Inmuebles",
    component: Inmuebles,
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/inmuebles/:id",
    name: "PropertyDetail",
    component: PropertyDetail,
    props: true
  },
  {
    path: "/seleccionar-inmueble",
    name: "SeleccionarInmueble",
    component: SeleccionarInmueble,
  },
  {
    path: "/seleccionar-datos-c",
    name: "SeleccionarDatosC",
    component: SeleccionarDatosC,
  },
  {
    path: "/seleccionar-datos-d",
    name: "SeleccionarDatosD",
    component: SeleccionarDatosD,
  },
  {
    path: "/seleccionar-datos-t",
    name: "SeleccionarDatosT",
    component: SeleccionarDatosT,
  },
  {
    path: "/publicacion-creada",
    name: "PublicacionCreada",
    component: PublicacionCreada,
  },
  {
    path: "/logout",
    name: "Logout",
    component: LogoutView,
  },
  {
	path: "/account",
	name: "account",
	component: AccountView,
  },
  { path: '/agente',
    name: "AgenteView",
    component: AgenteView,
  },
  {
    path: "/mis-chats",
    name: "MisChats",
    component: MisChats,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

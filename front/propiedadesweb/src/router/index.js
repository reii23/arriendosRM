import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/HomeView.vue";
import About from "../views/AboutView.vue";
import Inmuebles from "../views/InmueblesView.vue";
import Register from "../views/RegisterView.vue";
import Login from "../views/LoginView.vue";
import PropertyDetail from "../views/PropertyDetailView.vue";
import SeleccionarInmueble from "@/views/SeleccionarInmueble.vue";
import SeleccionarDatosC from "@/views/SeleccionarDatosC.vue";
import SeleccionarDatosD from "@/views/SeleccionarDatosD.vue";
import SeleccionarDatosT from "@/views/SeleccionarDatosT.vue";
import PublicacionCreada from "@/views/PublicacionCreada.vue";

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
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

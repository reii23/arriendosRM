import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import About from '../views/About.vue';
import Inmuebles from '../views/Inmuebles.vue';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/about', name: 'About', component: About },
  { path: '/inmuebles', name: 'Inmuebles', component: Inmuebles }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;

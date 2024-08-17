import { createApp } from "vue";
import PrimeVue from "primevue/config";
import App from "./App.vue";
import router from "./router";

import ("./assets/global.css"); 


import("primevue/resources/themes/lara-light-blue/theme.css");
import("primevue/resources/primevue.min.css");
import("primeicons/primeicons.css");




const app = createApp(App);
app.use(PrimeVue);
app.use(router);
app.mount("#app");
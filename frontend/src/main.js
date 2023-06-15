import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
// router
import Home from "@/components/Home.vue";
import { createRouter, createWebHashHistory } from "vue-router";
import { createPinia } from 'pinia'
//primevue
import 'primevue/resources/themes/saga-blue/theme.css'  //theme
import 'primevue/resources/primevue.min.css'            //core css
import 'primeicons/primeicons.css'                      //icons
import PrimeVue from 'primevue/config'
// CSS // bootstrap 5
import "@/scss/styles.scss";
import * as bootstrap from "bootstrap";
const GestionPrestamos = () => import('@/components/GestionPrestamos.vue')
const GestionUsuarios = () => import('@/components/GestionUsuarios.vue')
const GestionDocumental = () => import('@/components/GestionDocumental.vue')
const Estadistica = () => import('@/components/Estadistica.vue')
const Login = () => import('@/components/Login.vue')
const NotFound = () => import('@/components/NotFound.vue')

// router
const routes = [
  { path: "/", component: Home, name: 'home' },
  { path: "/gestionPrestamos", component: GestionPrestamos, name:'gestionPrestamos' },
  { path: "/gestionUsuarios", component: GestionUsuarios, name:'gestionUsuarios' },
  { path: "/gestionDocumental", component: GestionDocumental, name:'gestionDocumental' },
  { path: "/estadistica", component: Estadistica, name:'estadistica' },
  { path: "/Login", component: Login, name:'Login' },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound },
];
const pinia = createPinia()
const router = createRouter({ 
  history: createWebHashHistory(),
  routes, 
});

import { authStore } from '@/stores/auth'
router.beforeEach(async (to, from) => {
  const auth = authStore()
  if (!auth.esAdmin && to.name !== 'home' && to.name !== 'Login') {
    return { name: 'home' }
  }
})

const app = createApp(App);
app.use(pinia)
app.use(router);
app.use(PrimeVue)


app.mount("#app");


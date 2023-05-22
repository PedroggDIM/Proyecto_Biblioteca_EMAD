<script setup>
import { storeToRefs } from 'pinia'
import { authStore } from '@/stores/auth'

const auth = authStore()
const { tipoUsuario, esAdmin } = storeToRefs(auth)
const { cambiarNivel } = auth

</script>

<template>
<div>
  <!-- Navbar de la aplicación -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
  <div class="container-fluid"> 
   <router-link :to="{ name: 'home' }"  class="navbar-brand"
                :class="$route.name === 'home' ? 'text-warning' : $route.name === 'selectedRoute' ? 'text-white bg-warning' : ''">            
    Biblioteca EMAD
   </router-link>
  <!-- Botón para el menu movil -->
      <button class="navbar-toggler"
        type="button" data-bs-toggle="collapse" data-bs-target="#menu"
        aria-controls="menu" aria-expanded="false" aria-label="Mostrar / Ocultar Menu">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="menu">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
   <router-link v-if="esAdmin" :to="{ name: 'gestionPrestamos' }"  class="navbar-brand"
                :class="$route.name === 'gestionPrestamos' ? 'text-warning' : $route.name === 'selectedRoute' ? 'text-white bg-warning' : ''">            
   Gestión préstamos
   </router-link>
   <router-link v-if="esAdmin" :to="{ name: 'gestionUsuarios' }"  class="navbar-brand"
                :class="$route.name === 'gestionUsuarios' ? 'text-warning' : $route.name === 'selectedRoute' ? 'text-white bg-warning' : ''">            
   Gestión usuarios
   </router-link>   
   <router-link v-if="esAdmin" :to="{ name: 'gestionDocumental' }"  class="navbar-brand"
                :class="$route.name === 'gestionDocumental' ? 'text-warning' : $route.name === 'selectedRoute' ? 'text-white bg-warning' : ''">            
   Gestión documental
   </router-link>   
   <router-link v-if="esAdmin" :to="{ name: 'estadistica' }"  class="navbar-brand"
                :class="$route.name === 'estadistica' ? 'text-warning' : $route.name === 'selectedRoute' ? 'text-white bg-warning' : ''">            
   Estadística
   </router-link>        
   <router-link v-if="esAdmin" :to="{ name: 'acercade' }"  class="navbar-brand"
                :class="$route.name === 'acercade' ? 'text-warning' : $route.name === 'selectedRoute' ? 'text-white bg-warning' : ''">            
   Acerca de
   </router-link>        
        </ul>
        <router-link :to="{ name:'Login' }" class="btn btn-primary">LOGIN</router-link>       
      </div>
    </div>
</nav>
  <!-- Footer de la aplicación -->
<footer class="footer fixed-bottom bg-secondary text-light">
  <div class="container-fluid px-5">    
    <div class="float-end">
      <a href="https://www.defensa.gob.es/fuerzasarmadas/emad/" class="text-white text-decoration-none" target="_blank">
        <span class="fs-6">Estado Mayor de la Defensa</span>        			
      </a>
    </div>
  </div>
</footer>
</div> 
<router-view></router-view>
</template>

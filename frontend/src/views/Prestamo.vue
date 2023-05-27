<script>
import { mapActions, mapState } from "pinia";
import axios from "axios";
import { prestamosStore } from "@/stores/prestamos.js";
import moment from 'moment';

export default {
  props: ["prestamo"],
  emits: ["editarPrestamo"],
  data() {
    return {
      busquedaTitulo: "",
    };
  },
 computed: {
  ...mapState(prestamosStore, ["prestamos"]),
  filtroDeBusquedaTitulo() {
   if (this.busquedaTitulo === "") {
    return this.prestamos.slice(0, 5);
  } else {
    return this.prestamos.filter((prestamo) => {
      if (prestamo.documento && prestamo.documento.titulo) {
        return prestamo.documento.titulo.toLowerCase().includes(this.busquedaTitulo.toLowerCase());
      }
      return false;
    }).slice(0, 5);
    }
  },
},
  methods: {
    confirmarBorrado(prestamo) {
      debugger;
      if (confirm("¿Estás seguro de que deseas borrar este préstamo?")) {
        this.$emit("borrarPrestamo", prestamo);
      }
    },

    editarPrestamo(prestamo){
      debugger;
      this.$emit('editarPrestamo', prestamo)
    },
    formatDate(date) {
      return moment(date).format('YYYY/MM/DD');
    },
  },
};
</script>

<template>

 <!-- <div v-for="prestamo of prestamos">Campos que llegan de préstamo:   {{ prestamo }}  </div>  -->
    <h5 style="color: blue;">Buscar por Título</h5>
    <input v-model="busquedaTitulo" type="text" placeholder="Buscar por Título (introduzca el Título)" class="form-control mb-3"/>

    <div v-for="prestamo in filtroDeBusquedaTitulo" :key="prestamo.id" class="card mb-3">
      <div class="card-body bg-light">
        <div class="d-flex justify-content-end">
          <button type="button" class="btn btn-warning" @click="editarPrestamo(prestamo)">Editar</button>
        </div> 
        <p class="mb-0"><strong>Número de préstamo (id): </strong>{{ prestamo.id }}</p>        
        <p class="mb-0"><strong>Id Usuario: </strong>{{ prestamo.idUsuario }}</p>  
             <!-- <p class="mb-0"><strong>Titulo: </strong>{{ prestamo.documento ? prestamo.documento.titulo : '' }}</p> -->
        <p class="mb-0"><strong>Título: </strong>{{ prestamo.documento.titulo}}</p>
        <p class="mb-0"><strong>Fecha Inicio Préstamo: </strong>{{ formatDate(prestamo.fechaInicio)}}</p> 
        <p class="mb-0"><strong>Fecha Fin Préstamo: </strong>{{ formatDate(prestamo.fechaFin) }}</p>   
      </div>
    </div> 

</template>

<style>
  
</style>

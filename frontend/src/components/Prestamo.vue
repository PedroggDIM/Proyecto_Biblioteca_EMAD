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
    return this.busquedaTitulo === ""
      ? this.prestamos.slice(0, 5)
      : this.prestamos.filter((prestamo) => {
        return prestamo.documento.titulo.toLowerCase().includes(this.busquedaTitulo.toLowerCase());
      });
  },
},
  methods: {
    confirmarBorrado(prestamo) {

      if (confirm("¿Estás seguro de que deseas borrar este préstamo?")) {
        this.$emit("borrarPrestamo", prestamo);
      }
    },

    editarPrestamo(prestamo){
      this.$emit('editarPrestamo', prestamo)
    },
    formatDate(date) {
      return moment(date).format('YYYY/MM/DD');
    },

    loadPrestamos(){

      this.prestamos = prestamosStore().getPrestamos();
       
    }
  },
};
</script>

<template>

 <div v-for="prestamo of prestamos">
 </div> 
    <h5 style="color: blue;">Buscar por Título</h5>
    <input v-model="busquedaTitulo" type="text" placeholder="Buscar por Título (introduzca el Título)" class="form-control mb-3"/>

    <div v-for="prestamo in filtroDeBusquedaTitulo" :key="prestamo.id" class="card mb-3">
      <div class="card-body bg-light">
        <div class="d-flex justify-content-end">
          <button type="button" class="btn btn-warning" @click="editarPrestamo(prestamo)">Editar</button>
        </div>    
        <p>
          <span>Usuario: </span>{{ prestamo.idUsuario }}
        </p>
        <p>
          <span>Titulo: </span>{{ prestamo.documento.titulo }}
        </p>
        <p>
          <span>Fecha Inicio Préstamo: </span>{{ formatDate(prestamo.fechaInicio) }}
        </p>
        <p>
          <span>Fecha Fin Préstamo: </span>{{ formatDate(prestamo.fechaFin) }}
        </p>      
      </div>
    </div> 

</template>

<style>
  
</style>

<script>
import { mapActions, mapState } from "pinia";
import { documentosStore } from "@/stores/documentos.js";
import { prestamosStore } from '@/stores/prestamos.js'
import axios from "axios";

export default {
  emits: ['editarPrestamo'],
  props: ['documento', 'docId'],
  data() {
    return {
      busquedaTitulo: "",
    };
  },
  computed: {
    ...mapState(documentosStore, ['documentos']),
    ...mapState(prestamosStore, ['prestamos']),

    filtroDeBusqueda() {
      const filtroDeDocumentos = this.documentos.filter((documento) =>
        documento.titulo
          .toLowerCase()
          .includes(this.busquedaTitulo.toLowerCase())
      );
      //return filtroDeDocumentos.slice(0, 3);
      return filtroDeDocumentos;
    },

    loadDoc(){
      debugger;
      console.log("Carga documnentos");
      this.documentos = documentosStore().getDocumentos();
    },
    
  },
  mounted() {
    debugger;
    console.log("Componente montado ");
    console.log(this.docId);
  },
  updated() {
    debugger;
    console.log("Componente actualizado ");
    console.log(this.docId);

  },
  methods: {
    editarPrestamo(documento) {
      debugger;
      this.$emit('editarPrestamo', documento);
    },
    

  },
};
</script>

<template>
  <!-- <div v-for="documento of documentos">Campos de documento{{ documento }}</div>  -->

  <h5>Buscar por Título</h5>

  <input v-model="busquedaTitulo" type="text" placeholder="Buscar por Título (introduzca el Título)"
    class="form-control mb-3" />

  <div v-for="documento in filtroDeBusqueda" :key="documento.id" class="card m-0 p-0">
    <div v-if="documento.disponible == true">
      <div class="card-header text-primary">
        <strong>Título: </strong>{{ documento.titulo }}
      </div>
      <div class="card-body">
        <div class="contenedor d-flex justify-content-end">
          <!-- botón editar-->
          <button type="button" class="btn btn-warning" @click="editarPrestamo(documento)">Enviar al formulario</button>
        </div>
        <p class="mb-0"><strong>IdDocumento: </strong>{{ documento.id }}</p>
        <p class="mb-0"><strong>Autor: </strong>{{ documento.autor }}</p>
        <p class="mb-0"><strong>Tipo de documento: </strong>{{ documento.categoria }}</p>
        <p class="mb-0"><strong>Disponible: </strong>{{ documento.disponible ? 'Disponible' : 'No disponible' }}</p>
        <p class="mb-0"><strong>Copias disponibles: </strong>{{ documento.numCopias }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
<script>
import { mapActions, mapState } from "pinia";
import axios from "axios";
import { documentosStore } from "@/stores/documentos.js";

export default {
  props: ["documento"],
  data() {
    return {
      busquedaTitulo: "",
      busquedaAutor: "",
      busquedaSinopsis: ""
    };
  },
  computed: {
    ...mapState(documentosStore, ["documentos"]),
    filtroDeBusqueda() {
      return this.documentos.filter((documento) =>
        documento.titulo.toLowerCase().includes(this.busquedaTitulo.toLowerCase()) &&
        documento.autor.toLowerCase().includes(this.busquedaAutor.toLowerCase()) &&
        documento.sinopsis.toLowerCase().includes(this.busquedaSinopsis.toLowerCase())
      ).slice(0, 4);
     // return filtroDeDocumentos;
    },
  },
};
</script>

<template>
   <div>    
  <div class="row">
  <div class="row">      
  <div class="col-md-4 mb-3">
  <h5>Buscar por Título</h5>
    <input
      v-model="busquedaTitulo"
      type="text"
      placeholder="Buscar por Título (introduzca el Título)"
      class="form-control mb-3"
    />
  </div>
  <div class="col-md-4 mb-3">
   <h5>Buscar por Autor</h5>
    <input
      v-model="busquedaAutor"
      type="text"
      placeholder="Buscar por Autor (introduzca el Autor)"
      class="form-control mb-3"
    />
  </div>
  <div class="col-md-4 mb-3">
   <h5>Buscar por Sinópsis</h5>
    <input
      v-model="busquedaSinopsis"
      type="text"
      placeholder="Buscar por Sinopsis (introduzca palabras clave de la Sinopsis)"
      class="form-control mb-3"
    />
  </div>
</div>
   
  </div>

    <div v-for="documento in filtroDeBusqueda" :key="documento.id" class="card m-0 p-0">
      <div class="card-header text-primary">
        <h6><strong>Título: {{ documento.titulo }} </strong></h6>
      </div>
      <div class="card-body">
        <div class="contenedor d-flex justify-content-end">          
        </div>      
        <!-- <p class="mb-0"><strong>Idenfificador: </strong>{{ documento.id }}</p> -->
        <p class="mb-0"><strong>Autor: </strong>{{ documento.autor }}</p>
        <p class="mb-0"><strong>Sinopsis: </strong>{{ documento.sinopsis }}</p>
        <p class="mb-0"><strong>Estantería: </strong>{{ documento.estanteria }}</p>
        <p class="mb-0"><strong>Fecha alta: </strong>{{ documento.fechaAlta }}</p>
        <p class="mb-0"><strong>Disponible: </strong>{{ documento.disponible ? 'Disponible' : 'No disponible' }}</p>
        <p class="mb-0"><strong>Copias disponibles: </strong>{{ documento.numCopias }}</p>
        <p class="mb-0"><strong>Categoría: </strong>{{ documento.categoria }}</p>     
        <div v-if="documento.categoria === 'escrito'">
          <p class="mb-0"><strong>ISBN: </strong>{{ documento.isbn }}</p>
          <p class="mb-0"><strong>Número de páginas: </strong>{{ documento.numPaginas }}</p>
          <p class="mb-0"><strong>Tamaño: </strong>{{ documento.tamano }}</p>
        </div>  
        <div v-if="documento.categoria === 'audiovisual'">
          <p class="mb-0"><strong>ISAN: </strong>{{ documento.isan }}</p>
          <p class="mb-0"><strong>Duración en minutos: </strong>{{ documento.duracion }}</p>
          <p class="mb-0"><strong>Tipo: </strong>{{ documento.tipo }}</p>
        </div> 
      </div>
    </div>
  </div>
</template>

<style>
.contenedor button {
  margin-top: 0;
  margin-bottom: 0;
  padding-top: 0;
  padding-bottom: 0;
  margin-right: 10px;
}
</style>

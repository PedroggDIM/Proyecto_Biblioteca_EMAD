<script>
import { mapState } from "pinia";
import axios from "axios";
import { documentosStore } from "@/stores/documentos.js";
import moment from "moment";

export default {
  props: ["documento"],
  data() {
    return {
      busquedaTitulo: "",
      busquedaAutor: "",
      busquedaSinopsis: "",
    };
  },
  computed: {
    ...mapState(documentosStore, ["documentos"]),
    filtroDeBusqueda() {
      return this.documentos
        .filter(
          (documento) =>
            documento.titulo
              .toLowerCase()
              .includes(this.busquedaTitulo.toLowerCase()) &&
            documento.autor
              .toLowerCase()
              .includes(this.busquedaAutor.toLowerCase()) &&
            documento.sinopsis
              .toLowerCase()
              .includes(this.busquedaSinopsis.toLowerCase())
        )
        .slice(0, 4);
    },
  },
  methods: {
    formatDate(date) {
      return moment(date).format("YYYY/MM/DD");
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
    <div
      v-for="documento in filtroDeBusqueda"
      :key="documento.id"
      class="card m-0 p-0"
    >
      <div class="card-header text-primary">
        <h6>
          <span>Título: {{ documento.titulo }} </span>
        </h6>
      </div>
      <div class="card-body">
        <div class="contenedor d-flex justify-content-end"></div>
        <p><span>Autor: </span>{{ documento.autor }}</p>
        <p><span>Sinopsis: </span>{{ documento.sinopsis }}</p>
        <p><span>Estantería: </span>{{ documento.estanteria }}</p>
        <p><span>Fecha alta: </span>{{ formatDate(documento.fechaAlta) }}</p>
        <p>
          <span>Disponible: </span
          >{{ documento.disponible ? "Disponible" : "No disponible" }}
        </p>
        <p><span>Copias disponibles: </span>{{ documento.numCopias }}</p>
        <p><span>Categoría: </span>{{ documento.categoria }}</p>
        <div v-if="documento.categoria === 'escrito'">
          <p><span>ISBN: </span>{{ documento.isbn }}</p>
          <p><span>Número de páginas: </span>{{ documento.numPaginas }}</p>
          <p><span>Tamaño: </span>{{ documento.tamano }}</p>
        </div>
        <div v-if="documento.categoria === 'audiovisual'">
          <p><span>ISAN: </span>{{ documento.isan }}</p>
          <p><span>Duración en minutos: </span>{{ documento.duracion }}</p>
          <p><span>Tipo: </span>{{ documento.tipo }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>

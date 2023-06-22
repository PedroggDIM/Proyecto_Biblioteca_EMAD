<script>
import { mapState } from "pinia";
import { documentosStore } from "@/stores/documentos.js";
import { prestamosStore } from "@/stores/prestamos.js";

export default {
  props: ["documento"],
  emits: ["editarPrestamo"],
  data() {
    return {
      busquedaTitulo: "",
    };
  },
  computed: {
    ...mapState(documentosStore, ["documentos", "decrementarCopias"]),
    ...mapState(prestamosStore, ["prestamos"]),

    filtroDeBusqueda() {
      const filtroDeDocumentos = this.documentos.filter((documento) =>
        documento.titulo
          .toLowerCase()
          .includes(this.busquedaTitulo.toLowerCase())
      );
      return filtroDeDocumentos;
    },
  },
  methods: {
    editarPrestamo(documento) {
      this.$emit("editarPrestamo", documento);
    },
    gestionarCopias(prestamo, nuevoPrestamo) {
      const documentoId = prestamo.documento.id;

      let pos = -1;
      for (let i = 0; i < this.documentos.length && pos === -1; i++) {
        const documentoAuxiliar = this.documentos[i];
        if (documentoAuxiliar.id === documentoId) {
          pos = i;
        }
      }

      if (pos !== -1) {
        const documentoModificado = this.documentos[pos];

        if (nuevoPrestamo) {
          documentoModificado.numCopias--;
        } else {
          // Si modifico el prestamo
          if (prestamo.devuelto) {
            documentoModificado.numCopias++;
          }
        }

        if (documentoModificado.numCopias === 0) {
          documentoModificado.disponible = false;
        } else {
          documentoModificado.disponible = true;
        }
      }
    },
  },
};
</script>

<template>
  <div>
    <h5>Buscar por Título</h5>
    <input
      v-model="busquedaTitulo"
      type="text"
      placeholder="Buscar por Título (introduzca el Título)"
      class="form-control mb-3"
    />
    <div
      v-for="documento in filtroDeBusqueda"
      :key="documento.id"
      class="card m-0 p-0"
    >
      <div v-if="documento.disponible == true">
        <div class="card-header text-primary">
          <span>Título: </span>{{ documento.titulo }}
        </div>
        <div class="card-body">
          <div class="contenedor d-flex justify-content-end">
            <button
              type="button"
              class="btn btn-warning"
              @click="editarPrestamo(documento)"
            >
              Enviar al formulario
            </button>
          </div>
          <p><span>Autor: </span>{{ documento.autor }}</p>
          <p><span>Tipo de documento: </span>{{ documento.categoria }}</p>
          <p>
            <span>Disponible: </span
            >{{ documento.disponible ? "Disponible" : "No disponible" }}
          </p>
          <p><span>Copias disponibles: </span>{{ documento.numCopias }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>

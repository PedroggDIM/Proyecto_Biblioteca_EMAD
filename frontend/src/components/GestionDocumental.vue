<script>
import Calendar from "primevue/calendar";
import inputDocumental from "@/components/inputDocumental.vue";
import { documentosStore } from "@/stores/documentos.js";
import { mapActions } from "pinia";

export default {
  components: {
    Calendar,
    inputDocumental,
  },
  data() {
    return {
      documento: {
        id: "",
        titulo: "",
        autor: "",
        sinopsis: "",
        estanteria: 0,
        fechaAlta: "",
        disponible: [],
        categoria: [],
      },
    };
  },
  methods: {
    ...mapActions(documentosStore, [
      "guardarDocumento",
      "getDocumentos",
      "incluirNuevoDocumento",
    ]),
    procesarFormulario() {
      if (this.documento.titulo.trim() === "") {
        return;
      }
      this.documento.id = shortid.generate();
      this.documento = {
        id: "",
        titulo: "",
        autor: "",
        sinopsis: "",
        estanteria: 0,
        fechaAlta: "",
        disponible: [],
        categoria: [],
      };
    },
    limpiarDocumento() {
      this.documento.id = "";
      this.documento.titulo = "";
      this.documento.autor = "";
      this.documento.sinopsis = "";
      this.documento.estanteria = 0;
      this.documento.numCopias = 0;
      this.documento.fechaAlta = "";
      this.documento.duracion = "";
      this.documento.numPaginas = "";
      this.documento.tamano = "";
      this.documento.isan = "";
      this.documento.isbn = "";
      this.documento.tipo = "";
      this.documento.disponible = [];
      this.documento.categoria = [];
      this.documento._links = null;
    },
    formatearFecha(documento) {
      let arr = documento.fechaAlta.split("T")[0];
      let dataf = arr.split("-");
      documento.fechaAlta = new Date(
        parseInt(dataf[0]),
        parseInt(dataf[1]) - 1,
        parseInt(dataf[2])
      );
    },
    fGuardarDocumento(documento) {
      this.guardarDocumento(documento).then((r) => {
        this.formatearFecha(r.data);
        this.incluirNuevoDocumento(r.data);
        this.limpiarDocumento();
        this.$refs.inputDocumentalRef.limpiarDocumento();
      });
    },
  },
};
</script>

<template>
  <div>
    <h4>
      Gestión de los documentos de la biblioteca (grabación, edición y borrado
      del catálogo)
    </h4>
    <form class="mt-3" @submit.prevent="procesarFormulario">
      <inputDocumental
        ref="inputDocumentalRef"
        :documento="documento"
        @guardarDocumento="fGuardarDocumento"
      >
      </inputDocumental>
      <hr />
    </form>
  </div>
</template>

<style>
p {
  margin-top: 5px;
  margin-bottom: 0px;
}
</style>
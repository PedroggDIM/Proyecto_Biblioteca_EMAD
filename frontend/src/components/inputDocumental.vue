<script>
import Calendar from "primevue/calendar";
import { mapState, mapActions } from "pinia";
import Documento from "@/components/Documento.vue";
import { documentosStore } from "@/stores/documentos.js";
import { borrarDocumento } from "@/stores/api-service.js";

export default {
  props: {},
  components: {
    Calendar,
    Documento,
  },
  emits: ["guardarDocumento"],
  data() {
    return {
      mensajeError: [],
      documento: {
        id: '',
        titulo: '',
        autor: '',
        sinopsis: '',
        estanteria: '',
        fechaAlta: '',
        numCopias: '',
        disponible: '',
        categoria: '',
        isbn: '',
        numPaginas: '',
        tamano: '',
        isan: '',
        duracion: '',
        tipo: '',
        _links: '',
      },
    };
  },
  computed: {
    ...mapState(documentosStore, ["documentos"]),
  },
  methods: {
    ...mapActions(documentosStore, ["getDocumentos", "eliminarDocumento"]),
    borrarDocumento(documento) {
      this.eliminarDocumento(documento).then((r) => {
        if (r.data) {
          let eliminado = false;
          for (let i = 0; i < this.documentos.length && !eliminado; i++) {
            let documentoAux = this.documentos[i];
            if (documentoAux.id === documento.id) {
              eliminado = true;
              this.documentos.splice(i, 1);
            }
          }
        }
      });
    },
    limpiarDocumento() {
      this.documento.id = ''
      this.documento.titulo = '';
      this.documento.autor = '';
      this.documento.sinopsis = '';
      this.documento.estanteria = '';
      this.documento.fechaAlta = '';
      this.documento.numCopias = '';
      this.documento.disponible = '';
      this.documento.categoria = '',
      this.documento.isbn = '',
      this.documento.numPaginas = '';
      this.documento.tamano = '';
      this.documento.isan = '';
      this.documento.duracion = '';
      this.documento.tipo = '';
      this.documento._links = null;
      this.documento.categoria = '';
      this.mensajeError = [];
    },
    editarDocumento(documento) {
      this.documento.id = documento.id,
      this.documento.titulo = documento.titulo;
      this.documento.autor = documento.autor;
      this.documento.sinopsis = documento.sinopsis;
      this.documento.estanteria = documento.estanteria;
      this.documento.fechaAlta = documento.fechaAlta;
      this.documento.numCopias = documento.numCopias;
      this.documento.disponible = documento.disponible;
      this.documento.categoria = documento.categoria,
      this.documento.isbn = documento.isbn,
      this.documento.numPaginas = documento.numPaginas;
      this.documento.tamano = documento.tamano;
      this.documento.isan = documento.isan;
      this.documento.duracion = documento.duracion;
      this.documento.tipo = documento.tipo;
      this.documento._links = documento._links;
      if (documento.categoria != null && documento.categoria !== "") {
        this.documento.categoria = documento.categoria.toLowerCase();
      }
    },
    validarFormulario() {
      this.mensajeError = [];
      let valid = true;
      if (this.documento.titulo == null || this.documento.titulo.trim() === '') {
        valid = false;
        this.mensajeError.push('Inserte el titulo del documento.');
      }
      if (this.documento.autor == null || this.documento.autor.trim() === '') {
        valid = false;
        this.mensajeError.push('Inserte el autor del documento.');
      }
      if (this.documento.sinopsis == null || this.documento.sinopsis.trim() === '') {
        valid = false;
        this.mensajeError.push('Inserte la sinopsis del documento.');
      }
      if (this.documento.estanteria == null || this.documento.estanteria === '' || this.documento.estanteria < 0) {
        valid = false;
        this.mensajeError.push('Indique la estantería donde se localiza el documento.');
      }
      if (this.documento.numCopias == null || this.documento.numCopias === '' || this.documento.numCopias < 0) {
        valid = false;
        this.mensajeError.push('Indique el número de copias del documento. (Mayor que 0).');
      }
      if (this.documento.fechaAlta == null || this.documento.fechaAlta === '') {
        valid = false;
        this.mensajeError.push('Indique la fecha de alta del documento.');
      }
      if (this.documento.disponible == null || this.documento.disponible === '') {
        valid = false;
        this.mensajeError.push('Indique si el documento está disponible.');
      }
      if (this.documento.categoria == null || this.documento.categoria === '') {
        valid = false;
        this.mensajeError.push('Indique la categoria del documento.');
      } else {
        if (this.documento.categoria === 'escrito') {
          if (this.documento.isbn == null || this.documento.isbn === '') {
            valid = false;
            this.mensajeError.push('Indique el isbn del documento.');
          }
          if (this.documento.numPaginas == null || this.documento.numPaginas === '' || this.documento.numPaginas <= 0) {
            valid = false;
            this.mensajeError.push('Indique el número de páginas. (Mayor que 0).');
          }
          if (this.documento.tamano == null || this.documento.tamano === '' || this.documento.tamano <= 0) {
            valid = false;
            this.mensajeError.push('Indique el tamaño del documento. (Mayor que 0).');
          }
        } else {
          if (this.documento.isan == null || this.documento.isan === '') {
            valid = false;
            this.mensajeError.push('Indique el isan del documento.');
          }
          if (this.documento.duracion == null || this.documento.duracion === '' || this.documento.duracion <= 0) {
            valid = false;
            this.mensajeError.push('Indique la duración. (Mayor que 0).');
          }
          if (this.documento.tipo == null || this.documento.tipo === '') {
            valid = false;
            this.mensajeError.push('Indique el tipo del documento.');
          }
        }
      }
      return valid;
    },
    guardarDocumento(documento) {      
      if (this.validarFormulario()) {    
        this.$emit("guardarDocumento", documento);
      }
    },
  },
  created() {
    this.getDocumentos();
  },
};
</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-12 col-sm-5 fondoFormularioGraba">
        <h5 class="colorAzul">Formulario de grabación de documentos</h5>
        <p class="margeninput">Título</p>
        <input type="text" placeholder="Introduzca el título" class="form-control" v-model.trim="documento.titulo" />
        <p class="margeninput">Autor</p>
        <input type="text" placeholder="Introduzca el autor" class="form-control" v-model.trim="documento.autor" />
        <p class="margeninput">Sinopsis</p>
        <textarea placeholder="Introduzca la sinposis" class="form-control" v-model.trim="documento.sinopsis"></textarea>
        <p class="margeninput">Estantería</p>
        <input type="number" min="0" placeholder="Introduzca número de estantería" class="form-control"
          v-model.number="documento.estanteria" />
        <p class="margeninput">Fecha de alta</p>
        <Calendar v-model.trim="documento.fechaAlta" dateFormat="dd/MM/yy"></Calendar>
        <div class="my-2">
          <p class="margeninput">Indique si se encuentra disponible</p>
          <div class="form-radio form-radio-inline">
            <input readonly="readonly" type="radio" class="form-check-input" id="radio-1" v-model="documento.disponible"
              value="true" />
            <label for="check-1" class="form-check-label">Disponible Sí</label>
          </div>
          <div class="form-radio form-radio-inline">
            <input readonly="readonly" type="radio" class="form-check-input" id="radio-2" v-model="documento.disponible"
              value="false" />
            <label for="check-2" class="form-check-label">Disponible No</label>
          </div>
        </div>
        <div class="form-radio form-radio-inline">
          <label for="check-1" class="form-check-label">Copias disponibles</label>
          <input type="number" min="0" placeholder="Introduzca copias disponibles" class="form-control"
            v-model.number="documento.numCopias" />
        </div>
        <div class="my-2">
          <p class="margeninput">Seleccione el tipo de documento</p>
          <div class="form-radio form-radio-inline">
            <input type="radio" class="form-check-input" id="radio-1" v-model="documento.categoria" value="escrito" />
            <label for="check-1" class="form-check-label">Documento escrito</label>
          </div>
          <div class="form-radio form-radio-inline">
            <input type="radio" class="form-check-input" id="radio-2" v-model="documento.categoria" value="audiovisual" />
            <label for="check-2" class="form-check-label">Documento audiovisual</label>
          </div>
        </div>
        <div v-if="documento.categoria === 'escrito'">
          <p class="margeninput">ISBN</p>
          <input type="number" min="0" placeholder="Introduzca el ISBN" class="form-control"
            v-model.trim="documento.isbn" />
          <p class="margeninput">Número de páginas</p>
          <input type="number" min="0" placeholder="Introduzca el número de páginas" class="form-control"
            v-model.trim="documento.numPaginas" />
          <p class="margeninput">Tamaño</p>
          <input type="number" min="0" placeholder="Introduzca el tamaño" class="form-control"
            v-model.trim="documento.tamano" />
        </div>
        <div v-if="documento.categoria === 'audiovisual'" :disabled="documento.categoria === 'escrito'">
          <p>ISAN</p>
          <input type="number" min="0" placeholder="Introduzca el ISAN" class="form-control"
            v-model.trim="documento.isan" />
          <p>Duración</p>
          <input type="number" min="0" placeholder="Introduzca la duración" class="form-control"
            v-model.trim="documento.duracion" />
          <p>Tipo</p>
          <input type="text" placeholder="Introduzca el tipo" class="form-control" v-model.trim="documento.tipo" />
        </div>
        <button type="button" class="btn btn-success" @click="guardarDocumento(documento)">
          Guardar
        </button>
        <div>
          <p v-if="mensajeError.length != 0">Revise los siguientes errores:</p>
          <p  class="error" v-for="error in mensajeError">{{ error }}</p>
        </div>
      </div>
      <div class="col-12 col-sm-7 fondoEditElim">
        <h5 class="colorAzul">Edición/Borrado de documentos</h5>
        <br />
        <Documento @borrarDocumento="borrarDocumento" @editarDocumento="editarDocumento">
        </Documento>
      </div>
    </div>
  </div>
</template>

<style>
.fondoFormularioGraba {
  background-color: #ccc;
  border: 1px;
  border-radius: 3px;
}
.fondoEditElim {
  background-color: #d6eaf8;
}
.margeninput {
  margin-top: 10px;
  margin-bottom: 0px;
}
.error {
  color: red;
}
</style>
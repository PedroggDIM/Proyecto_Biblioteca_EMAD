<script>
import { mapState } from "pinia";
import { documentosStore } from "@/stores/documentos.js";
import moment from 'moment';

export default {
  props: ["documento"],
  emits: ["borrarDocumento"],
  data() {
    return {
      busquedaTitulo: "",
    };
  },
  computed: {
    ...mapState(documentosStore, ["documentos"]),
    filtroDeBusqueda() {   
      const filtroDeDocumentos = this.documentos.filter((documento) =>
        documento.titulo
          .toLowerCase()
          .includes(this.busquedaTitulo.toLowerCase())
      );
        return filtroDeDocumentos.slice(0, 3);        
    },
  },
  methods: {
    confirmarBorrado(documento) {  
      if (confirm("¿Estás seguro de que deseas borrar este documento?")) {
        this.$emit("borrarDocumento", documento);
      }
    },
    formatDate(date) {
      return moment(date).format('YYYY/MM/DD');
    },
    editarDocumento(documento){ 
      this.$emit('editarDocumento', documento)
    }
  },
};
</script>

<template>
  <h5>Buscar por Título.</h5>  
  <input v-model="busquedaTitulo" type="text" placeholder="Buscar por Título (introduzca el Título)" class="form-control mb-3"/>
  <div v-for="documento in filtroDeBusqueda" :key="documento.id" class="card m-0 p-0">
    <div class="card-header text-primary">    
      <h5>Título: {{ documento.titulo }}</h5>
    </div>
    <div class="card-body">
      <div class="contenedor d-flex justify-content-end">
        <button type="button" class="btn btn-danger" @click="confirmarBorrado(documento)">Borrar</button>
        <button type="button" class="btn btn-warning" @click="editarDocumento(documento)">Editar</button>
      </div>       
      <p>
        <span>Autor: </span> {{ documento.autor }}
      </p>
      <p>
        <span>Estantería: </span> {{ documento.estanteria }}
      </p>
      <p>
        <span>Disponible: </span> {{ documento.disponible ? 'Disponible' : 'No disponible' }}
      </p>
      <p>
        <span>Copias disponibles: </span> {{ documento.numCopias }}
      </p>
      <p>
        <span>Categoría: </span> {{ documento.categoria }}
      </p>     
      <div v-if="documento.categoria === 'escrito'">
        <p>
          <span>ISBN: </span>{{ documento.isbn }}
        </p>
        <p>
          <span>Número de páginas: </span>{{ documento.numPaginas }}
        </p>
        <p>
          <span>Tamaño: </span>{{ documento.tamano }}
        </p>
      </div>  
      <div v-if="documento.categoria === 'audiovisual'">
        <p>
          <span>ISAN: </span>{{ documento.isan }}
        </p>
        <p>
          <span>Duración en minutos: </span>{{ documento.duracion }}
        </p>
        <p>
          <span>Tipo: </span>{{ documento.tipo }}
        </p>
      </div> 
    </div>
  </div>
</template>

<style scoped>

</style>

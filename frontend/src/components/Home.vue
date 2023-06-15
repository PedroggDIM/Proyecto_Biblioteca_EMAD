<script>
import { mapState, mapActions } from 'pinia'
import Documento from '@/components/Documento.vue'
import DocumentoHome from '@/components/DocumentoHome.vue'
import { documentosStore } from '@/stores/documentos.js'

export default { 
  components: {
    Documento,  
    DocumentoHome },
   data() {
     return {
        busquedaTitulo: '',
        busquedaPorAutor: '',
        buscaSinopsis: '',    
      };
  },
 computed: {
    ...mapState(documentosStore, ['documentos']),    
    filtroDeBusqueda() {  

      const filtroDeDocumentos = this.documentos.filter((documento) => {
      return documento.titulo.toLowerCase().includes(this.busquedaTitulo.toLowerCase()) &&
      documento.autor.toLowerCase().includes(this.busquedaPorAutor.toLowerCase()) &&
      documento.sinopsis.toLowerCase().includes(this.buscaSinopsis.toLowerCase()) 
  });
  return filtroDeDocumentos;
    },
  },
  methods: { 
    ...mapActions(documentosStore, [ 'getDocumentos' ]),   
  },    
  created() {
    this.getDocumentos()
  }
};
</script>

<template>
      
  <img src="../assets/logo_app_lite.png" class="img-fluid" alt="icono EMAD"/>
    <h5 class="d-none d-md-block">
      Biblioteca del EMAD. Consulta del catálogo.
    </h5><br>

          <DocumentoHome/>  
 
</template>

<style>

</style>





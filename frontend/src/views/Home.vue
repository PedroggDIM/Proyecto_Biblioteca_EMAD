<script>
import { mapState, mapActions } from 'pinia'
import Documento from '@/views/Documento.vue'
import DocumentoHome from '@/views/DocumentoHome.vue'
import { documentosStore } from '@/stores/documentos.js'

export default { Documento,
   components: {  
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
      debugger;
    
  const filtroDeDocumentos = this.documentos.filter((documento) => {
   return documento.titulo.toLowerCase().includes(this.busquedaTitulo.toLowerCase()) &&
    documento.autor.toLowerCase().includes(this.busquedaPorAutor.toLowerCase()) &&
    documento.sinopsis.toLowerCase().includes(this.buscaSinopsis.toLowerCase()) 
  });
  return filtroDeDocumentos.slice(0, 10);
},
  },
  methods: {
    //para  el get
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
                                         
      <!-- <Documento v-for="documento of documentos" :documento="documento"> </Documento>  -->
</template>

<style scoped>
.container {
  width: 1800px;
  
}
</style>





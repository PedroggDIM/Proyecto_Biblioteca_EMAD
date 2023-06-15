<script>
import Calendar from 'primevue/calendar';
import { getDocumentosMasPrestados } from '@/stores/api-service_prestamos.js';

export default {
  components: {
    Calendar
  },
  data() {
    return {
      fechaInicio: null,
      fechaFin: null,
      lstDocumentosMasPrestados:[]
    };
  },
  methods: {
    obtenerDocumentosUtilizados() {   
      getDocumentosMasPrestados(this.fechaInicio, this.fechaFin, 10).then(documentosMasPrestadosResponse => {
        if (documentosMasPrestadosResponse.mensaje !== '') {
          this.lstDocumentosMasPrestados = documentosMasPrestadosResponse.data.lstDocumentosMasPrestados;
        }

      });
      
    }
  }
};
</script>


<template>
  <div class="container">
    <h3>Documentos más utilizados entre dos fechas</h3>
    <div class="row">
      <div class="col-md-6">
        <div class="p-field">
          <label class="texto" for="fechaInicio">Fecha de inicio: </label><br>
          <Calendar v-model="fechaInicio" id="fechaInicio" :showIcon="true"></Calendar>
        </div>
      </div>
      <div class="col-md-6">
        <div class="p-field">
          <label class="texto" for="fechaFin">Fecha de fin:</label><br>
          <Calendar v-model="fechaFin" id="fechaFin" :showIcon="true"></Calendar>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-6 mt-3">
        <div class="p-field">
          <button @click="obtenerDocumentosUtilizados" class="p-button p-component">Obtener documentos</button>
        </div>
      </div>
    </div>
  </div>

  <div v-for="documentoMasPrestado in lstDocumentosMasPrestados" :key="documentoMasPrestado.documentoModel.id" class="card m-0 p-0">
      <div class="card-header text-primary">
        <span>Título: </span>{{ documentoMasPrestado.documentoModel.titulo }}
      </div>
      <div class="card-body">  
        <p>
          <span>Autor: </span>{{ documentoMasPrestado.documentoModel.autor }}
        </p>
        <p>
          <span>Tipo de documento: </span>{{ documentoMasPrestado.documentoModel.categoria }}
        </p>
        <p>
          <span>Disponible: </span>{{ documentoMasPrestado.documentoModel.disponible ? 'Disponible' : 'No disponible' }}
        </p>
        <p>
          <span>Copias disponibles: </span>{{ documentoMasPrestado.documentoModel.numCopias }}
        </p>
        <p>
          <span>Veces prestado: </span>{{ documentoMasPrestado.suma }}
        </p>
      </div>
  </div>
</template>

<style scoped>
.texto {
  color: blue;
  font-size: 20px
}
</style>
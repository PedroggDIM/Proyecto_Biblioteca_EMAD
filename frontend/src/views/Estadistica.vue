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
      // Llamar al método personalizado en el backend con las fechas seleccionadas
      // Puedes hacer una solicitud HTTP aquí utilizando axios o la librería de tu elección
      // y pasar las fechas como parámetros en la solicitud

      console.log('Obtener documentos más utilizados entre:', this.fechaInicio, this.fechaFin);
      // Lógica adicional para manejar la respuesta del backend

      debugger;
      getDocumentosMasPrestados(this.fechaInicio, this.fechaFin, 5).then(documentosMasPrestadosResponse => {
        debugger;
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
        <strong>Título: </strong>{{ documentoMasPrestado.documentoModel.titulo }}
      </div>
      <div class="card-body">
        <p class="mb-0"><strong>IdDocumento: </strong>{{ documentoMasPrestado.documentoModel.id }}</p>
        <p class="mb-0"><strong>Autor: </strong>{{ documentoMasPrestado.documentoModel.autor }}</p>
        <p class="mb-0"><strong>Tipo de documento: </strong>{{ documentoMasPrestado.documentoModel.categoria }}</p>
        <p class="mb-0"><strong>Disponible: </strong>{{ documentoMasPrestado.documentoModel.disponible ? 'Disponible' : 'No disponible' }}</p>
        <p class="mb-0"><strong>Copias disponibles: </strong>{{ documentoMasPrestado.documentoModel.numCopias }}</p>
        <p class="mb-0"><strong>Veces prestado: </strong>{{ documentoMasPrestado.count }}</p>
      </div>
  </div>
</template>

<style>
.texto {
  color: blue;
  font-size: 20px
}
</style>
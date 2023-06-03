<script>
import Calendar from 'primevue/calendar'
export default {
  components: {
   
    Calendar
  },
  data() {
    return {
      fechaInicio: null,
      fechaFin: null
    };
  },
  methods: {
    obtenerDocumentosUtilizados() {
      // Llamar al método personalizado en el backend con las fechas seleccionadas
      // Puedes hacer una solicitud HTTP aquí utilizando axios o la librería de tu elección
      // y pasar las fechas como parámetros en la solicitud
      console.log('Obtener documentos más utilizados entre:', this.fechaInicio, this.fechaFin);
      // Lógica adicional para manejar la respuesta del backend
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
   <!-- <h6>Página de estadística en construcción</h6>
   <img src="../assets/sitio-construccion.jpg" class="img-fluid" alt="En desarrollo"/> -->

</template>
<style>
.texto {
  color: blue;
  font-size: 20px
}

</style>
<script>
import Calendar from 'primevue/calendar'
import Prestamo from '@/views/Prestamo.vue'
import { mapState, mapActions } from 'pinia'
import { prestamosStore } from '@/stores/prestamos.js'
import DocumentoPrestamo from '@/views/DocumentoPrestamo.vue'
import TabView from 'primevue/tabview'
import TabPanel from 'primevue/tabpanel'


export default {
    emits: ["guardarPrestamo"],
  components: {
    Prestamo, Calendar,
    DocumentoPrestamo,
    TabView, TabPanel,
  },

  data() {
    return {
      // El nombre, apellido y email los rellena el bibliotecario.
      // El resto de campos del formulario son generados por el sistema
      prestamo: {
        idDocumento: '',
        titulo: '',
        autor: '',
        email: '',
        nombre: '',
        apellidos: '',
        fechaInicio: '',
        fechaFin: '',
        categoria: ''
      }
    }
  },
  computed: {
    ...mapState(prestamosStore, ['prestamos'])

  },
  methods: {
    ...mapActions(prestamosStore, ['guardarPrestamo','getPrestamos']),

     editarPrestamo(documento) {
      debugger;

      if (documento.categoria === 'escrito') {
        this.prestamo.idDoc = documento.isbn;
        this.prestamo.categoria = 'escrito';
      } else {
        this.prestamo.idDoc = documento.isan;
        this.prestamo.categoria = 'audiovisual';
      }
      this.prestamo.idDocumento = documento._links.self.href;

      this.prestamo.titulo = documento.titulo;
      this.prestamo.autor = documento.autor;

      this.prestamo.fechaInicio = '';
      this.prestamo.fechaFin = '';
    },


    editarP(prestamo) {
      debugger;

      this.prestamo.id = prestamo.id;

      if (prestamo.documento.categoria === 'escrito') {
        this.prestamo.idDoc = prestamo.documento.isbn;
        this.prestamo.categoria = 'escrito';
      } else {
        this.prestamo.idDoc = prestamo.documento.isan;
        this.prestamo.categoria = 'audiovisual';
      }

      this.prestamo.idDocumento = prestamo._links.documento.href;

      this.prestamo.titulo = prestamo.documento.titulo;
      this.prestamo.autor = prestamo.documento.autor;

      this.prestamo.email = prestamo.email;
      this.prestamo.nombre = prestamo.nombre;
      this.prestamo.apellidos = prestamo.apellidos;

      this.prestamo.fechaInicio = prestamo.fechaInicio;
      this.prestamo.fechaFin = prestamo.fechaFin;
    },

    cambiarFechaAlta(event) {
     debugger
      if (this.prestamo.fechaInicio != null && this.prestamo.fechaInicio !== '') {
        let date;

        const miliFechaInicio = this.prestamo.fechaInicio.getTime();
        if (this.prestamo.categoria === 'escrito') {
          date = new Date(miliFechaInicio + 86400000 * 7);
          console.log('escrito ' + date);

        } else {
          date = new Date(miliFechaInicio + 86400000 * 3);
          console.log('audiovisual ' + date);
        }
        const dateF = date.toLocaleDateString('en-us', { weekday: "long", year: "numeric", month: "short", day: "numeric" })
        // "Friday, Jul 2, 2021"
        const arrayAux = dateF.split(','); // [Friday, Jul 2, 2021]
        const arrayAuxDay = arrayAux[1].trim().split(' ');
        const day = arrayAuxDay[1];
        const month = arrayAuxDay[0]
        const year = arrayAux[2].trim();

        const map = {'Jan': 0, 'Feb': 1, 'Mar':2, 'Apr':3, 'May': 4, 'Jun':5, 'Jul':6, 'Aug':7, 'Sep':8, 'Oct':9, 'Nov':10, 'Dec':11 };

        //this.prestamo.fechaFin = day + '/' + month + '/' + year;
        debugger;
        this.prestamo.fechaFin = new Date( parseInt(year), map[month], parseInt(day));
      }
    },

    fGuardarPrestamo(prestamo) {
      console.log(prestamo);
      debugger;
    
      this.guardarPrestamo(prestamo).then(r => {
        debugger;
        if (r.status == 200) {
          this.getPrestamos();
        }
      });
    },
  },
  
  created() {
    this.getPrestamos()
  }
};
</script>

<template>

<div class="card">
    <TabView>
      <TabPanel header="Formulario de Grabación de Préstamos">
    <form>
    <div class="container-fluid">
      <div class="row">
        <div class="col-12 col-sm-5 fondoFormularioGraba">
          <h5 class="margeninput" style="color: blue;">Formulario de grabación de Préstamos</h5>
       
          <input type="hidden" placeholder="id" cass="form-control" v-model.trim="prestamo.id" readonly>
          <p class="margeninput">ISBN / ISAN</p>
          <input type="number" placeholder="Introduzca el Id del documento" class="form-control disabled" 
            v-model.trim="prestamo.idDoc" >
          <p class="margeninput">Título</p>
          <input type="text" placeholder="Introduzca el título del documento" class="form-control disabled" 
            v-model.trim="prestamo.titulo">
          <p class="margeninput">Autor</p>
          <input type="text" placeholder="Introduzca el título del documento" class="form-control disabled" 
            v-model.trim="prestamo.autor">

          <p class="margeninput">E-mail</p>
          <input type="email" placeholder="Introduzca el email" class="form-control" v-model.trim="prestamo.email" required>

          <p class="margeninput">Nombre usuario</p>
          <input type="text" placeholder="Nombre usuario" class="form-control" v-model.trim="prestamo.nombre" required>

          <p class="margeninput">Apellidos</p>
          <input type="text" placeholder="Apellidos del usuario" class="form-control" v-model.trim="prestamo.apellidos" required>

          <p class="margeninput">Fecha de alta (Fecha actual)</p>
          <Calendar v-model.trim="prestamo.fechaInicio" dateFormat="dd/MM/yy" @focusout="cambiarFechaAlta($event)"></Calendar>
          
          
          
          <!--  <Calendar v-model.trim="prestamo.fechaInicio" dateFormat="dd/MM/yy">
          </Calendar>-->

          <p class="margeninput">Fecha finalización préstamo (Escrito +7 días | Audiovisual +3 días)</p>
          <Calendar class="disabled" v-model.trim="prestamo.fechaFin" dateFormat="dd/MM/yy" readonly></Calendar>
          <!--<Calendar v-model.trim="prestamo.fechaFin" dateFormat="dd/MM/yy"></Calendar>-->
          <!-- Botón GUARDAR -->
          <div>
            <br>
            <button type="button" class="btn btn-success" @click="fGuardarPrestamo(prestamo)">Confirmar reserva</button>
          </div>
        </div>

        <div class="col-12 col-sm-7 fondoEditElim">
          <h5 class="margeninput" style="color: blue;">Asistente para añadir Documentos disponibles al formulario</h5>
          <p class="margeninput" style="color: blue;">Localice el Documento a añadir por su título y seleccione reservar para añadir campos</p>
          <DocumentoPrestamo @editarPrestamo="editarPrestamo"></DocumentoPrestamo>
        </div>
      </div>
    </div>
    </form>
  <!-- Muestro lo recogido en el formulario -->
    <p>
      {{ prestamo }}
    </p>
    </TabPanel>
    <TabPanel header="Listado de Préstamos">
<div class="col-12 col-sm-7">
  <!-- Listado de préstamos del componente Prestamo.vue -->
 
    <h2 style="color: blue;">Listado de Préstamos</h2>
   <Prestamo :prestamo="prestamo" @editarPrestamo="editarP"> </Prestamo>
</div> 

    </TabPanel>            
    </TabView>
</div>

</template>

<style>
.disabled {
  opacity: 0.75;
}
</style>


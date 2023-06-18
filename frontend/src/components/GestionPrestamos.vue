<script>
import Calendar from 'primevue/calendar'
import Prestamo from '@/components/Prestamo.vue'
import { mapState, mapActions } from 'pinia'
import { prestamosStore } from '@/stores/prestamos.js'
import DocumentoPrestamo from '@/components/DocumentoPrestamo.vue'
import TabView from 'primevue/tabview'
import TabPanel from 'primevue/tabpanel'

const documntoPrestamoRef = null;

export default {
  components: {
    Prestamo, Calendar,
    DocumentoPrestamo,
    TabView, TabPanel,
  },
  emits: ["guardarPrestamo"],
  data() {
    return {
      noPermitirDevolver: true,
      prestamo: {
        id: '',
        isbn_isan: '',
        documento: '',
        email: '',
        nombre: '',
        apellidos: '',
        fechaInicio: '',
        fechaFin: '',
        devuelto: ''
      }
    }
  },
  computed: {
    ...mapState(prestamosStore, ['prestamos'])

  },
  methods: {
    ...mapActions(prestamosStore, ['guardarPrestamo', 'getPrestamos', 'incluirPrestamo']),

    editarPrestamo(documento) {
      this.prestamo.documento = documento;
      if (documento.categoria === 'escrito') {
        this.prestamo.isbn_isan = documento.isbn;
      } else {
        this.prestamo.isbn_isan = documento.isan;
      }
      this.prestamo.fechaInicio = '';
      this.prestamo.fechaFin = '';
      this.prestamo.devuelto = false;

       // Deshabilita el check para que no se pueda devolver el prestamo porque este es uno nuevo
      this.noPermitirDevolver = true;
    },

    editarP(prestamo) {
      this.prestamo.id = prestamo.id;
      this.prestamo.documento = prestamo.documento;
      if (prestamo.documento.categoria === 'escrito') {
        this.prestamo.isbn_isan = prestamo.documento.isbn;
      } else {
        this.prestamo.isbn_isan = prestamo.documento.isan;
      }
      this.prestamo.titulo = prestamo.documento.titulo;
      this.prestamo.autor = prestamo.documento.autor;
      this.prestamo.fechaInicio = prestamo.fechaInicio;
      this.prestamo.fechaFin = prestamo.fechaFin;
      this.prestamo.devuelto = prestamo.devuelto;
      this.prestamo._links = prestamo._links;

      if (!prestamo.devuelto) {
        // Habilita el check para poder devolver el prestamo
        this.noPermitirDevolver = false;
      }else{
        this.noPermitirDevolver = true;
      }
    },

    cambiarFechaAlta(event) {
      if (this.prestamo.fechaInicio != null && this.prestamo.fechaInicio !== '') {
        let date;

        const miliFechaInicio = this.prestamo.fechaInicio.getTime();
        if (this.prestamo.categoria === 'escrito') {
          date = new Date(miliFechaInicio + 86400000 * 7);

        } else {
          date = new Date(miliFechaInicio + 86400000 * 3);
        }
        const fechaFormateada = date.toLocaleDateString('en-us', { weekday: "long", year: "numeric", month: "short", day: "numeric" })
        const arrayAux = fechaFormateada.split(',');
        const arrayAuxDia = arrayAux[1].trim().split(' ');
        const dia = arrayAuxDia[1];
        const mes = arrayAuxDia[0]
        const anio = arrayAux[2].trim();

        const map = { 'Jan': 0, 'Feb': 1, 'Mar': 2, 'Apr': 3, 'May': 4, 'Jun': 5, 'Jul': 6, 'Aug': 7, 'Sep': 8, 'Oct': 9, 'Nov': 10, 'Dec': 11 };
        this.prestamo.fechaFin = new Date(parseInt(anio), map[mes], parseInt(dia));
      }
    },
    limpiarCampos() {
      this.prestamo.id = '';
      this.prestamo.titulo = '';
      this.prestamo.autor = '';
      this.prestamo.email = '';
      this.prestamo.nombre = '';
      this.prestamo.apellidos = '';
      this.prestamo.fechaInicio = '';
      this.prestamo.fechaFin = '';
      this.prestamo.devuelto = '';
      this.prestamo.categoria = '';

      this.noPermitirDevolver = false;
    },

    fGuardarPrestamo(prestamo) {
      this.guardarPrestamo(prestamo).then(r => {
        this.limpiarCampos();
        if (r.data) {
          const nuevoPrestamo = this.incluirPrestamo(r.data);
          this.$refs.documntoPrestamoRef.gestionarCopias(r.data, nuevoPrestamo);
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
                <h5 class="margeninput" style="color: blue;">Formulario de grabación de Préstamos</h5><br>

                <p class="margeninput">ISBN / ISAN</p>
                <input type="number" placeholder="Introduzca el Identificador del documento" class="form-control disabled"
                  v-model.trim="prestamo.isbn_isan">
                <p class="margeninput">Título</p>
                <input type="text" placeholder="Introduzca el título del documento" class="form-control disabled"
                  v-model.trim="prestamo.documento.titulo">
                <p class="margeninput">Autor</p>
                <input type="text" placeholder="Introduzca el título del documento" class="form-control disabled"
                  v-model.trim="prestamo.documento.autor">

                <p class="margeninput">E-mail</p>
                <input type="email" placeholder="Introduzca el email" class="form-control" v-model.trim="prestamo.email"
                  required>

                <p class="margeninput">Nombre usuario</p>
                <input type="text" placeholder="Nombre usuario" class="form-control" v-model.trim="prestamo.nombre"
                  required>

                <p class="margeninput">Apellidos</p>
                <input type="text" placeholder="Apellidos del usuario" class="form-control"
                  v-model.trim="prestamo.apellidos" required>

                <p class="margeninput">Fecha de alta (Fecha actual)</p>
                <Calendar v-model.trim="prestamo.fechaInicio" dateFormat="dd/MM/yy" @focusout="cambiarFechaAlta($event)">
                </Calendar>

                <p class="margeninput">Fecha finalización préstamo (Escrito +7 días | Audiovisual +3 días)</p>
                <Calendar class="disabled" v-model.trim="prestamo.fechaFin" dateFormat="dd/MM/yy"></Calendar>

                <div class="my-2">
                  <p class="margeninput">Devolución del documento:<br></p>
                  <div class="form-radio form-radio-inline">
                    <input :disabled='noPermitirDevolver' type="checkbox" class="form-check-input" id="check-1"
                      v-model="prestamo.devuelto" value="true">
                    <label for="check-1" class="form-check-label">Devolver prestamo</label>
                  </div>
                </div>

                <div>
                  <div class="button-container">
                    <br>
                    <button type="button" class="btn btn-success" @click="fGuardarPrestamo(prestamo)">Confirmar
                      reserva</button>
                  </div>
                </div>

              </div>

              <div class="col-12 col-sm-7 fondoEditElim">
                <h5 class="margeninput" style="color: blue;">Asistente para añadir Documentos disponibles al formulario
                </h5>
                <p class="margeninput" style="color: blue;">Localice el Documento a añadir por su título y seleccione
                  reservar para añadir campos</p>
                <DocumentoPrestamo ref="documntoPrestamoRef" :docId="docId" @editarPrestamo="editarPrestamo">
                </DocumentoPrestamo>
              </div>
            </div>
          </div>
        </form>

      </TabPanel>
      <TabPanel header="Listado de Préstamos">
        <div class="col-12 col-sm-7">

          <h2 style="color: blue;">Listado de Préstamos</h2>
          <Prestamo :prestamo="prestamo" @editarPrestamo="editarP"> </Prestamo>
        </div>

      </TabPanel>
    </TabView>
  </div>
  
    
</template>


<style></style>

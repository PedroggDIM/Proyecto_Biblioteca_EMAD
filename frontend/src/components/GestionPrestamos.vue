<script>
import Calendar from "primevue/calendar";
import Prestamo from "@/components/Prestamo.vue";
import { mapState, mapActions } from "pinia";
import { prestamosStore } from "@/stores/prestamos.js";
import DocumentoPrestamo from "@/components/DocumentoPrestamo.vue";
import TabView from "primevue/tabview";
import TabPanel from "primevue/tabpanel";
import usuariosJson from "@/assets/usuarios.json";

const documntoPrestamoRef = null;

export default {
  components: {
    Prestamo,
    Calendar,
    DocumentoPrestamo,
    TabView,
    TabPanel,
  },
  emits: ["guardarPrestamo"],
  data() {
    return {
      mensajeError: [],
      usuarios: usuariosJson.usuario,
      noPermitirDevolver: true,
      prestamo: {
        id: "",
        isbn_isan: "",
        documento: {},
        email: "Selecciona el usuario",
        nombre: "",
        apellidos: "",
        fechaInicio: "",
        fechaFin: "",
        devuelto: "",
      },
    };
  },
  computed: {
    ...mapState(prestamosStore, ["prestamos"]),
  },
  methods: {
    ...mapActions(prestamosStore, [
      "guardarPrestamo",
      "getPrestamos",
      "incluirPrestamo",
    ]),
    validarFormulario() {
      this.mensajeError = [];
      let usuario = this.getUsuarioByEmail(this.prestamo.email.trim());
      let valid = true;
      if (usuario == null ||
        this.prestamo.nombre.trim() === "" ||
        this.prestamo.email.trim() === "Seleccione un email" ||
        this.prestamo.apellidos.trim() === "") {
        valid = false;
        this.mensajeError.push('Debe indicar un usuario para el prestamo.');
      }
      if (this.prestamo.documento == null ||
        (this.prestamo.documento.titulo && this.prestamo.documento.titulo.trim() === '') ||
        (this.prestamo.documento.autor && this.prestamo.documento.autor.trim() === '') ||
        this.prestamo.isbn_isan === '') {
        valid = false;
        this.mensajeError.push('Debe indicar un documento para el prestamo.');
      }
      if (this.prestamo.fechaInicio == "") {
        valid = false;
        this.mensajeError.push('Debe indicar una fecha de inicio.');
      }
      if (this.prestamo.fechaFin === "") {
        valid = false;
        this.mensajeError.push('Debe indicar una fecha de fin.');
      }
      return valid;
    },
    editarPrestamo(documento) {
      this.prestamo.documento = documento;
      if (documento.categoria === "escrito") {
        this.prestamo.isbn_isan = documento.isbn;
      } else {
        this.prestamo.isbn_isan = documento.isan;
      }
      this.prestamo.fechaInicio = "";
      this.prestamo.fechaFin = "";
      this.prestamo.devuelto = false;
      // Deshabilita el check para que no se pueda devolver el prestamo porque este es uno nuevo
      this.noPermitirDevolver = true;
    },
    editarP(prestamo) {
      this.prestamo.id = prestamo.id;
      this.prestamo.documento = prestamo.documento;
      if (prestamo.documento.categoria === "escrito") {
        this.prestamo.isbn_isan = prestamo.documento.isbn;
      } else {
        this.prestamo.isbn_isan = prestamo.documento.isan;
      }
      let usuario = this.getUsuarioById(prestamo.idUsuario);
      this.prestamo.email = usuario.correo_electronico;
      this.prestamo.nombre = usuario.nombre;
      this.prestamo.apellidos = usuario.apellidos;
      this.prestamo.fechaInicio = prestamo.fechaInicio;
      this.prestamo.fechaFin = prestamo.fechaFin;
      this.prestamo.devuelto = prestamo.devuelto;
      this.prestamo._links = prestamo._links;
      if (!prestamo.devuelto) {
        // Habilita el check para poder devolver el prestamo
        this.noPermitirDevolver = false;
      } else {
        this.noPermitirDevolver = true;
      }
    },
    cambiaUsuario(event) {
      let email = event.currentTarget.value;
      if (email !== 'Selecciona el usuario') {
        let usuario = this.getUsuarioByEmail(email);
        this.prestamo.nombre = usuario.nombre;
        this.prestamo.apellidos = usuario.apellidos;
      } else {
        this.prestamo.nombre = '';
        this.prestamo.apellidos = '';
      }
      this.prestamo.email = email;
    },
    cambiarFechaAlta(event) {
      if (
        this.prestamo.fechaInicio != null &&
        this.prestamo.fechaInicio !== ""
      ) {
        let date;
        const miliFechaInicio = this.prestamo.fechaInicio.getTime();
        if (this.prestamo.categoria === "escrito") {
          date = new Date(miliFechaInicio + 86400000 * 7);
        } else {
          date = new Date(miliFechaInicio + 86400000 * 3);
        }
        const fechaFormateada = date.toLocaleDateString("en-us", {
          weekday: "long",
          year: "numeric",
          month: "short",
          day: "numeric",
        });
        const arrayAux = fechaFormateada.split(",");
        const arrayAuxDia = arrayAux[1].trim().split(" ");
        const dia = arrayAuxDia[1];
        const mes = arrayAuxDia[0];
        const anio = arrayAux[2].trim();
        const map = {
          Jan: 0,
          Feb: 1,
          Mar: 2,
          Apr: 3,
          May: 4,
          Jun: 5,
          Jul: 6,
          Aug: 7,
          Sep: 8,
          Oct: 9,
          Nov: 10,
          Dec: 11,
        };
        this.prestamo.fechaFin = new Date(
          parseInt(anio),
          map[mes],
          parseInt(dia)
        );
      }
    },
    limpiarCampos() {
      this.prestamo.id = '';
      this.prestamo.isbn_isan = '';
      this.prestamo.documento = {};
      this.prestamo.email = 'Selecciona el usuario';
      this.prestamo.nombre = '';
      this.prestamo.apellidos = '';
      this.prestamo.fechaInicio = '';
      this.prestamo.fechaFin = '';
      this.prestamo.devuelto = '';
      this.prestamo.categoria = '';
      this.noPermitirDevolver = false;
      this.mensajeError = [];
    },
    fGuardarPrestamo(prestamo) {    
      if (this.validarFormulario()) {
        // Si los datos son válidos guardamos el prestamo
        this.guardarPrestamo(prestamo).then((r) => {
          this.limpiarCampos();
          if (r.data) {
            const nuevoPrestamo = this.incluirPrestamo(r.data);
            this.$refs.documntoPrestamoRef.gestionarCopias(r.data, nuevoPrestamo);
          }
        });
      }
    },
    getUsuarioById(idUsuario) {
      const usuario = this.usuarios.find(
        (user) => user.idUsuario === idUsuario
      );
      return usuario || {};
    },
    getUsuarioByEmail(email) {
      const usuario = this.usuarios.find((user) => {
        if (user.correo_electronico === email) {
          return user;
        }
      });
      return usuario;
    },
  },
  created() {
    this.getPrestamos();
  },
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
                <h5 class="colorAzul">Formulario de grabación de Préstamos</h5>
                <br />
                <p class="margeninput">ISBN / ISAN</p>
                <input type="number" min="0" placeholder="Introduzca el Identificador del documento"
                  class="form-control disabled" v-model.trim="prestamo.isbn_isan" readonly />
                <p class="margeninput">Título</p>
                <input type="text" placeholder="Introduzca el título del documento" class="form-control disabled"
                  v-model.trim="prestamo.documento.titulo" readonly />
                <p class="margeninput">Autor</p>
                <input type="text" placeholder="Introduzca el título del documento" class="form-control disabled"
                  v-model.trim="prestamo.documento.autor" readonly />
                <p class="margeninput">E-mail</p>
                <select class="form-control" v-model.trim="prestamo.email" @change="cambiaUsuario($event)">
                  <option selected="selected">Selecciona el usuario</option>
                  <option :value="user.correo_electronico" v-for="user in usuarios">{{ user.correo_electronico }}</option>
                </select>
                <p class="margeninput">Nombre usuario</p>
                <input type="text" placeholder="Nombre usuario" class="form-control" v-model.trim="prestamo.nombre"
                  required disabled />
                <p class="margeninput">Apellidos</p>
                <input type="text" placeholder="Apellidos del usuario" class="form-control"
                  v-model.trim="prestamo.apellidos" required disabled />
                <p class="margeninput">Fecha de alta (Fecha actual)</p>
                <Calendar v-model.trim="prestamo.fechaInicio" dateFormat="dd/MM/yy" @focusout="cambiarFechaAlta($event)">
                </Calendar>
                <p class="margeninput">
                  Fecha finalización préstamo (Escrito +7 días | Audiovisual +3
                  días)
                </p>
                <Calendar class="disabled" v-model.trim="prestamo.fechaFin" dateFormat="dd/MM/yy"></Calendar>
                <div class="my-2">
                  <p class="margeninput">Devolución del documento:<br /></p>
                  <div class="form-radio form-radio-inline">
                    <input :disabled="noPermitirDevolver" type="checkbox" class="form-check-input" id="check-1"
                      v-model="prestamo.devuelto" value="true" />
                    <label for="check-1" class="form-check-label">Devolver prestamo</label>
                  </div>
                </div>
                <div>
                  <div class="button-container">
                    <br />
                    <button type="button" class="btn btn-success" @click="fGuardarPrestamo(prestamo)">
                      Confirmar reserva
                    </button>
                  </div>
                  <div>
                    <p v-if="mensajeError.length != 0">Revise los siguientes errores:</p>
                    <p  class="error" v-for="error in mensajeError">{{ error }}</p>
                  </div>
                </div>
              </div>
              <div class="col-12 col-sm-7 fondoEditElim">
                <h5 class="colorAzul">
                  Asistente para añadir Documentos disponibles al formulario
                </h5>
                <p class="colorAzul">
                  Localice el Documento a añadir por su título y seleccione
                  reservar para añadir campos
                </p>
                <DocumentoPrestamo ref="documntoPrestamoRef" @editarPrestamo="editarPrestamo">
                </DocumentoPrestamo>
              </div>
            </div>
          </div>
        </form>
      </TabPanel>
      <TabPanel header="Listado de Préstamos">
        <div class="col-12 col-sm-7">
          <h2 class="colorAzul">Listado de Préstamos</h2>
          <Prestamo :prestamo="prestamo" @editarPrestamo="editarP"> </Prestamo>
        </div>
      </TabPanel>
    </TabView>
  </div>
</template>

<style scoped>
.error {
  color: red;
}
</style>
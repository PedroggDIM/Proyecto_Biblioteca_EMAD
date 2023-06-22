<script>
import { mapState } from "pinia";
import { prestamosStore } from "@/stores/prestamos.js";
import moment from "moment";
import usuariosJson from "@/assets/usuarios.json";

export default {
  props: ["prestamo"],
  emits: ["editarPrestamo"],
  data() {
    return {
      busquedaTitulo: "",
      usuarios: usuariosJson.usuario,
    };
  },
  computed: {
    ...mapState(prestamosStore, ["prestamos"]),
    filtroDeBusquedaTitulo() {
      return this.busquedaTitulo === ""
        ? this.prestamos.slice(0, 10)
        : this.prestamos.filter((prestamo) => {
            return prestamo.documento.titulo
              .toLowerCase()
              .includes(this.busquedaTitulo.toLowerCase());
          });
    },
  },
  methods: {
    confirmarBorrado(prestamo) {
      if (confirm("¿Estás seguro de que deseas borrar este préstamo?")) {
        this.$emit("borrarPrestamo", prestamo);
      }
    },
    editarPrestamo(prestamo) {
      this.$emit("editarPrestamo", prestamo);
    },
    formatDate(date) {
      return moment(date).format("YYYY/MM/DD");
    },
    loadPrestamos() {
      this.prestamos = prestamosStore().getPrestamos();
    },
    getUsuario(idUsuario) {
      const usuario = this.usuarios.find(
        (user) => user.idUsuario === idUsuario
      );
      return usuario || {};
    },
  },
};
</script>

<template>
  <div>
    <h5 class="colorAzul">Buscar por Título</h5>
    <input
      v-model="busquedaTitulo"
      type="text"
      placeholder="Buscar por Título (introduzca el Título)"
      class="form-control mb-3"
    />
    <div
      v-for="prestamo in filtroDeBusquedaTitulo"
      :key="prestamo.id"
      class="card mb-3"
    >
      <div class="card-body bg-light">
        <div class="d-flex justify-content-end">
          <button
            type="button"
            class="btn btn-warning"
            @click="editarPrestamo(prestamo)"
          >
            Editar
          </button>
        </div>
        <p><span>Usuario: </span>{{ prestamo.idUsuario }}</p>
        <p>
          <span>Identificación usuario: </span
          >{{ getUsuario(prestamo.idUsuario).correo_electronico }}
        </p>
        <p>
          <span>Nombre: </span>{{ getUsuario(prestamo.idUsuario).nombre }}
          {{ getUsuario(prestamo.idUsuario).apellidos }}
        </p>
        <p><span>Titulo: </span>{{ prestamo.documento.titulo }}</p>
        <p>
          <span>Fecha Inicio Préstamo: </span
          >{{ formatDate(prestamo.fechaInicio) }}
        </p>
        <p>
          <span>Fecha Fin Préstamo: </span>{{ formatDate(prestamo.fechaFin) }}
        </p>
        <p>
          <span>Devuelto: </span
          >{{ prestamo.devuelto ? "Sí devuelto" : "No devuelto (prestado)" }}
        </p>
      </div>
    </div>
  </div>
</template>

<style></style>

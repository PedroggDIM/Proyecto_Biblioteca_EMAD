import { defineStore } from "pinia";
import { getPrestamos, guardarPrestamo } from "./api-service_prestamos";

export const prestamosStore = defineStore("prestamos", {
  state: () => ({
    prestamos: [],
  }),
  actions: {
    getPrestamos() {
      getPrestamos().then((r) => {
        this.prestamos = r.data._embedded.prestamoModels;
        this.prestamos.forEach((prestamo) => {
          this.formatearFecha(prestamo);
        });
      });
    },
    formatearFecha(prestamo) {
      let arr = prestamo.fechaFin.split("T")[0];
      let dataf = arr.split("-");
      prestamo.fechaFin = new Date(
        parseInt(dataf[0]),
        parseInt(dataf[1]) - 1,
        parseInt(dataf[2])
      );
      arr = prestamo.fechaInicio.split("T")[0];
      dataf = arr.split("-");
      prestamo.fechaInicio = new Date(
        parseInt(dataf[0]),
        parseInt(dataf[1]) - 1,
        parseInt(dataf[2])
      );
    },
    getPrestamosPorId(id) {
      return this.prestamos.find((p) => p.id == id);
    },
    incluirPrestamo(prestamo) {
      this.formatearFecha(prestamo);
      let nuevoPrestamo = false;
      let pos = -1;
      for (let i = 0; i < this.prestamos.length && pos == -1; i++) {
        const prestamoAux = this.prestamos[i];
        if (prestamoAux.id === prestamo.id) {
          pos = i;
        }
      }
      if (pos !== -1) {
        const prestamoModificado = this.prestamos[pos];
        prestamoModificado.fechaInicio = prestamo.fechaInicio;
        prestamoModificado.fechaFin = prestamo.fechaFin;
      } else {
        nuevoPrestamo = true;
        this.prestamos.push(prestamo);
      }
      return nuevoPrestamo;
    },
    guardarPrestamo(prestamo) {
      return guardarPrestamo(prestamo);
    },
  },
});
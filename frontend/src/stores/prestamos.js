import { defineStore } from 'pinia'
import { getPrestamos, guardarPrestamo } from './api-service_prestamos'

export const prestamosStore = defineStore('prestamos', {
  state: () => ({
    prestamos: []
  }),
  actions: {
    async getPrestamos() {
      await getPrestamos().then(r =>{
        debugger;
        this.prestamos = r.data._embedded.prestamoModels;

        this.prestamos.forEach(prestamo => {
          debugger;

          // 2020-01-26T00:00:00.000+00:00"
          let arr = prestamo.fechaFin.split('T')[0];
          let dataf = arr.split('-');
          prestamo.fechaFin = new Date(parseInt(dataf[0]) ,parseInt(dataf[1]) , parseInt(dataf[2]));

          arr = prestamo.fechaInicio.split('T')[0];
          dataf = arr.split('-');
          prestamo.fechaInicio = new Date(parseInt(dataf[0]) ,parseInt(dataf[1]) , parseInt(dataf[2]));
          debugger;
        });
      });
    },


    getPrestamosPorId(id) {
      return this.prestamos.find(p => p.id == id)
    },
    guardarPrestamo(prestamo) {
      debugger;
      if (prestamo.idDocumento != null || prestamo.idDocumento !== '') {
        this.prestamos.push(prestamo);
      }
      debugger;
      return guardarPrestamo(prestamo);
    },

  }
})



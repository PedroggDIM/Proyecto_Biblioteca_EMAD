import { defineStore } from 'pinia';
import { getDocumentos, guardarDocumento, borrarDocumento } from './api-service';

export const documentosStore = defineStore('documentos', {
  state: () => ({
    documentos: []
  }),
  actions: {
    getDocumentos() {
      getDocumentos().then(response => {
        this.documentos = response.data._embedded.documentoModels;
        this.documentos.forEach(documento => {
          this.formatearFecha(documento);
        });
      });
    },

    formatearFecha(documento) {
      let arr = documento.fechaAlta.split('T')[0];
      let dataf = arr.split('-');
      documento.fechaAlta = new Date(parseInt(dataf[0]), parseInt(dataf[1]) - 1, parseInt(dataf[2]));
    },

    getDocumentoPorId(id) {
      return this.documentos.find(p => p.id == id);
    },

    guardarDocumento(documento) {
      return guardarDocumento(documento);
    },

    incluirNuevoDocumento(documento) {
      let pos = -1;
      for (let i = 0; i < this.documentos.length && pos == -1; i++) {
        const documentoAux = this.documentos[i];
        if (documentoAux.id === documento.id) {
          pos = i;
        }
      }
      if (pos !== -1) {
        const documentoModificado = this.documentos[pos];

        documentoModificado.titulo = documento.titulo;
        documentoModificado.autor = documento.autor;
        documentoModificado.sinopsis = documento.sinopsis;
        documentoModificado.estanteria = documento.estanteria;
        documentoModificado.numCopias = documento.numCopias;
        documentoModificado.fechaAlta = documento.fechaAlta;
        documentoModificado.disponible = documento.disponible;
        documentoModificado.categoria = documento.categoria;
        documentoModificado.duracion = documento.duracion;
        documentoModificado.numPaginas = documento.numPaginas;
        documentoModificado.tamano = documento.tamano;
        documentoModificado.isan = documento.isan;
        documentoModificado.isbn = documento.isbn;
        documentoModificado.tipo = documento.tipo;

        if (documentoModificado.numCopias === 0) {
          documentoModificado.disponible = false;
        } else {
          documentoModificado.disponible = true;
        }

      } else {
        this.documentos.push(documento);
      }
    },

    eliminarDocumento(documento) {
      return borrarDocumento(documento);
    },

    incrementarCopiasDisponibles(documentoId) {
      let pos = -1;
      for (let i = 0; i < this.documentos.length && pos === -1; i++) {
        const documentoAuxiliar = this.documentos[i];
        if (documentoAuxiliar.id === documentoId) {
          pos = i;
        }
      }

      if (pos !== -1) {
        const documentoModificado = this.documentos[pos];
        documentoModificado.numCopias++;
        documentoModificado.disponible = true;
      }
    }
  }
});



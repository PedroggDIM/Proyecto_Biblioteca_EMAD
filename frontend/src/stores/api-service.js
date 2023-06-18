import axios from 'axios'

const host = 'https://proyectobibliotecaemad-pedroggsegosego.b4a.run/api';

export function cambiarHttpPorHttps(enlace) {
  return enlace.replace('http', 'https')
}

export function llamadaApi(path, method, body) {
  let config = {
    method: method,
    maxBodyLength: Infinity,
    url: path,
    headers: {}
  }
  if (body) {
    config.data = body,
      config.headers['Content-Type'] = 'application/json'
  }
  return axios.request(config)
}

export function guardarDocumento(documento) {

  documento.fechaAlta = convierteFecha(documento.fechaAlta);
  
  const url = documento._links ? cambiarHttpPorHttps(documento._links.self.href) : `${host}/documentos`;
  const method = documento._links ? 'put' : 'post';

  return llamadaApi(url, method, documento);
}

export function borrarDocumento(documento) {
  return llamadaApi(cambiarHttpPorHttps(documento._links.self.href), 'delete') 
}

export function getEntidades(nombre) {
  return llamadaApi(`${host}/${nombre}`, 'get');
}
export function getDocumentos() {
  return getEntidades('documentos');
}

export function convierteFecha(date) {
  let diaFecha
  let dia = date.getDate();
  if (dia < 10) {
    diaFecha = '0' + dia;
  } else {
    diaFecha = dia.toString();
  }

  let mesFecha
  let mes = date.getMonth() + 1;
  if (mes < 10) {
    mesFecha = '0' + mes;
  } else {
    mesFecha = mes.toString();
  }

  return date.getFullYear() + '-' + mesFecha + '-' + diaFecha+'T00:00:00.00+00:00';
}

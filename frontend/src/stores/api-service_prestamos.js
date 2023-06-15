import axios from 'axios';
import usuariosJson from '@/assets/usuarios.json'

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

export function guardarPrestamo(prestamo) {

  let method = 'post';
  let url = `${host}/prestamos`;
  const arrayUsuarios = usuariosJson.usuario;
  let userId = -1;
  arrayUsuarios.forEach(usu => {
    if (usu.correo_electronico === prestamo.email) {
      userId = usu.idUsuario;
    }
  });


  let data = {};
  if (userId !== -1) {
    data['idUsuario'] = userId;
  } else {
    data['idUsuario'] = null;
  }

  if (prestamo._links) {
    method = 'put';
    url = cambiarHttpPorHttps(prestamo._links.self.href);
  }

  data['fechaInicio'] = convierteFecha(prestamo.fechaInicio);
  data['fechaFin'] = convierteFecha(prestamo.fechaFin);
  data['_links'] = {};
  data['_links']['documento']={};
  data['_links']['documento']['href'] = cambiarHttpPorHttps(prestamo.documento._links.self.href);

  return llamadaApi(url, method, data);
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


export function getEntidades(nombre) {
  return llamadaApi(`${host}/${nombre}`, 'get')
}

export function getPrestamos() {
  return getEntidades('prestamos')
}

export function getDocumentosMasPrestados(fechaIni, fechaFin, n) {
  const fechaInicioStr = convierteFecha(fechaIni);
  const fechaFinStr = convierteFecha(fechaFin);


  
  return llamadaApi(`${host}/personalizado/ndocumentosmasprestado`, 'post',
    { fechaInicio: fechaInicioStr, fechaFin: fechaFinStr, numeroDeDocumentos: n });
}
import axios from 'axios';
import usuariosJson from '@/assets/usuarios.json'

const host = 'https://apirestbiblioemad-pedroggsegosego.b4a.run/api';

export function llamadaApi(path, method, body) {
  debugger;
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

  debugger;
  let data = {};
  if (userId !== -1) {
    data['idUsuario'] = userId;
  } else {
    data['idUsuario'] = null;
  }

  if (prestamo.id) {
    method = 'put';
    url += '/' + prestamo.id;
  }

  data['fechaInicio'] = convertDate(prestamo.fechaInicio);
  data['fechaFin'] = convertDate(prestamo.fechaFin);
  data['documento'] = prestamo.idDocumento;

  return llamadaApi(url, method, data);
}

export function convertDate(date) {
  let dayStr
  let day = date.getDate();
  if (day < 10) {
    dayStr = '0' + day;
  } else {
    dayStr = day.toString();
  }

  let monthStr
  let month = date.getMonth();
  if (month < 10) {
    monthStr = '0' + month;
  } else {
    monthStr = month.toString();
  }

  return date.getFullYear() + '-' + monthStr + '-' + dayStr;
}


export function getEntidades(nombre) {
  return llamadaApi(`${host}/${nombre}`, 'get')
}

export function getPrestamos() {
  return getEntidades('prestamos')
}

export function getDocumentosMasPrestados(fechaIni, fechaFin, n) {
  const fechaInicioStr = convertDate(fechaIni);
  const fechaFinStr = convertDate(fechaFin);

  debugger;
  return llamadaApi(`${host}/personalizado/ndocumentosmasprestado`, 'post',
    { fechaInicio: fechaInicioStr, fechaFin: fechaFinStr, n: n });
}
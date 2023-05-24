import axios from 'axios'
// api producción
const host = 'https://proyectobibliotecaemad-pedroggsegosego.b4a.run/api';

export function llamadaApi(path, method, body) {
  let config = {
    method: method,    
    maxBodyLength: Infinity,
    url: path,
    headers: {}
  }
  if(body) {
    config.data = body,
    config.headers['Content-Type'] = 'application/json'
  }
return axios.request(config)
}

export function guardarDocumento(documento) {
  debugger;
  if(documento.id){
    return llamadaApi(`${host}/documentos/${documento.id}`, 'put', documento);
  }else{
    return llamadaApi(`${host}/documentos`, 'post', documento);
  }
}
export function borrarDocumento(documento) {
  debugger;
  return llamadaApi(`${host}/documentos/${documento.id}`, 'delete') // con la api poner la url tipo: (documento._links.self.href.replace('http', 'https'), 'delete')
}

export function getEntidades(nombre) {
  return llamadaApi(`${host}/${nombre}`,'get');
}
export function getDocumentos() {
  return getEntidades('documentos');
}

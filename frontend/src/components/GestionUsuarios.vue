<script>
import usuariosJson from '@/assets/usuarios.json'

export default {
  components: {   
  },
  data() {
    return {
      usuarios : usuariosJson.usuario
    }
  }

};
</script>


<template>  
 
<h4>Usuarios de la aplicación</h4>
<div class="row">
  <div class="col-12 col-md-6" v-for="usuario of usuarios" :key="usuario.idUsuario">
    <div class="card" >
      <div class="card-body">
        <h5 class="card-title">Usuario {{ usuario.idUsuario }}</h5>
        <p>
          <span>UserName:</span> {{ usuario.username }}
        </p>
        <p>
          <span>Nombre:</span> {{ usuario.nombre }}
        </p>
        <p>
          <span>Apellidos:</span> {{ usuario.apellidos }}
        </p>
        <p>
          <span>Extensión:</span> {{ usuario.telefono }}
        </p>
        <p>
          <span>Correo:</span> {{ usuario.correo_electronico }}
        </p>
      </div>
    </div>
  </div>
</div>    

</template>

<style>
 .card-body { 
     background-color: #f2f2f2;
  }
</style>


<script setup>
import { storeToRefs } from "pinia";
import { authStore } from "@/stores/auth";
const auth = authStore();
const { tipoUsuario } = storeToRefs(auth);
const { cambiarNivel } = auth;
</script>

<template>
  <div class="login">
    <div class="p-2 d-flex flex-column align-items-center">
      <div class="card h-100">
        <div class="card-body">
          <div class="row align-items-center">
            <div class="header-text mb-4">
              <img src="../assets/logo_app_lite.png" alt="emd" />
              <h3 class="text-center">Iniciar sesión</h3>
            </div>
            <div class="input-group mb-3">
              <input
                type="text"
                class="form-control form-control-lg bg-light fs-6"
                placeholder="Utilice el botón inferior"
              />
            </div>
            <div class="input-group mb-1">
              <input
                type="password"
                class="form-control form-control-lg bg-light fs-6"
                placeholder="Use botón Básico/Bibliotecario"
              />
            </div>
            <div class="p-2 justify-content-around">
              Selección del tipo de usuario
              <span class="btn btn-primary" @click="cambiarNivel">{{
                tipoUsuario
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.login {
  margin-top: 50px;
  margin-left: 100px;
  background-color: #f2f2f2;
}
</style>

import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const authStore = defineStore("autenticacion", () => {
  const tipoUsuario = ref("Básico");
  //propiedad computada para saber si el usuario es admin o no
  const esAdmin = computed(() => tipoUsuario.value == "Bibliotecario");
  function cambiarNivel() {
    tipoUsuario.value = this.esAdmin ? "Básico" : "Bibliotecario";
  }
  // esto es lo que puedo usar fuera
  return { tipoUsuario, esAdmin, cambiarNivel };
});

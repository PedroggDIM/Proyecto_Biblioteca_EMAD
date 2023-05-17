# BiblioEMAD

## Descripción
El uso de la biblioteca del EMAD es intensivo en algunos documentos, la correcta organización y adquisición de nuevos documentos necesita una adecuada obtención de datos de uso.
Actualmente ese control se hace "a mano", mediante fichas de préstamo y se desea automatizar ese proceso, con el objetivo de obtener la información necesaria para conseguir optimizar la disposición de los ejemplares y la adquisición de nuevos en función de la demanda.
Para ello la aplicación permitirá:
1. Gestionar el préstamo y devolución de de los documentos. 
1. Consulta de los usuarios que han tenido acceso a un documento.
1. Consulta de los documentos prestados a un usuario.
1. Obtener los documentos más utilizados entre dos fechas determinadas con le objeto de adquirir nuevas copias.
1. Se mostrarán los préstamos de un usuario y los de cada documento indicando los documentos que no se hayan utilizado.

Además se espera que la aplicación permita consultas avanzadas que proporcionen información útil para la gestión de la biblioteca.

## Diagrama de clases de diseño

![Diagrama_de_Diseño](https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad/-/wikis/uploads/a161639bd4128b385a0fd6f16930fbe5/EsquemaBiblioteca_EMAD.jpg)

**Cumplimiento de requisitos**
- 1. **Herencia**: Se realizará sobre `Documento` y sus subtipos: `Escrito`, `Audiovisual`.
- 2. **Relación One-To-Many**: Relación entre `Prestamo` y `Documento`.`Un solo documento puede tener varios préstamos.
- 3. **Método personalizado**: Obtener los documentos más utilizados entre dos fechas determinadas con le objetivo de adquirir nuevas copias.
- 4. **Listado**: Se mostrarán los préstamos de un usuario y los de cada documento indicando además los documentos que no se hayan utilizado.
- 5. **CRUD**: Se usarán los controles adecuados para hacer el CRUD. Se usará los siguientes formularios.
* Formulario de préstamo (figura 1).
* Formulario de devolución (figura 2).
* formulario de consulta de usuario (figura 3).
* Formulario de consulta de documento (figura 4). 

- 6. **URLs del proyecto**:--

      Repositorio proyecto: https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad

      Libería: https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad/-/wikis/home

- 7. **Despliegue en internet**:--

https://biblioteca-emad.netlify.app/#/


## Interfaz de usuario
  
![Enlace a la página de Interfaces](https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad/-/wikis/Interfaces-del-Proyecto)


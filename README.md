# BiblioEMAD

## Descripción
El uso de la biblioteca del EMAD es intensivo en algunos documentos, la correcta organización y adquisición de nuevos documentos necesita una adecuada obtención de datos de uso.
Actualmente ese control se hace "a mano", mediante fichas de préstamo y se desea automatizar ese proceso, con el objetivo de obtener la información necesaria para conseguir optimizar la disposición de los ejemplares y la adquisición de nuevos en función de la demanda.
Para ello la aplicación permitirá:
1. Gestionar el préstamo y devolución de de los documentos. 
1. Gestionar los documentos.
1. Obtener los documentos más utilizados entre dos fechas determinadas con el objeto de adquirir nuevas copias.
1. Se mostrarán los préstamos de cada documento.


## Diagrama de clases de diseño

https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad/-/wikis/Diagramas#diagrama-de-clases

**Cumplimiento de requisitos**
- 1. **Herencia**: Se realizará sobre `Documento` y sus subtipos: `Escrito`, `Audiovisual`.
- 2. **Relación One-To-Many**: Relación entre `Prestamo` y `Documento`.`Un solo documento puede tener varios préstamos.
- 3. **Método personalizado**: Obtener los documentos más utilizados entre dos fechas determinadas con le objetivo de adquirir nuevas copias.
- 4. **Listado**: Se mostrará listados de documentos con filtro por título y autor y de préstamos pudiendose filtrar por título del documento.
- 5. **CRUD**: Se usarán los controles adecuados y formularios adecuados. 

- 6. **URLs del proyecto**:--

      Repositorio proyecto: https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad

      Libería: https://git.institutomilitar.com/Fluqueta/proyecto_biblioteca_emad/-/wikis/home

- 8. **Despliegue en internet**:--

https://biblioteca-emad.netlify.app/#/

- 7. **Libreria externa**:--

https://github.com/PedroggDIM/libreriaEMAD




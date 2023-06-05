package es.mdef.proyecto_biblioteca_emad.REST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.proyecto_biblioteca_emad.dto.PrestamoAgrupadoPorDocumentoResponse;
import es.mdef.proyecto_biblioteca_emad.dto.DocumentosMasPrestadosDTO;
import es.mdef.proyecto_biblioteca_emad.dto.DocumentosMasPrestadosResponse;
import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad.repositorios.PrestamoRepositorioMetPersonalizado;
import es.mdef.proyecto_biblioteca_emad.repositorios.impl.PrestamoAgrupadoPorDocumento;
import es.mdef.proyecto_biblioteca_emad.repositorios.spec.PrestamoSpecs;

@CrossOrigin(origins = "*",methods={RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/personalizado/")
public class PersonalizadoController {

	@Autowired
	private PrestamoRepositorioMetPersonalizado prestamoRepositorioMetPersonalizado;

	@Autowired
	private DocumentoAssembler assembler;

	@PostMapping(path = "ndocumentosmasprestado")
	public ResponseEntity<DocumentosMasPrestadosResponse> documentosMasPrestadosResponse(
			@RequestBody DocumentosMasPrestadosDTO documentosMasPrestadosDTO) {

		Date ini = documentosMasPrestadosDTO.getFechaInicio();
		Date end = documentosMasPrestadosDTO.getFechaFin();
		int n = documentosMasPrestadosDTO.getN();

		Specification<Prestamo> spec = PrestamoSpecs.enuentraEntreFechas(ini, end);

		String mensaje = "";
		HttpStatus status = HttpStatus.OK;
		List<PrestamoAgrupadoPorDocumentoResponse> lstDocumentosMasPrestadosModel = new ArrayList<>();
		try {
			List<PrestamoAgrupadoPorDocumento> lstDocumentosMasPrestados = prestamoRepositorioMetPersonalizado
					.prestamoGroupByDocumento(spec, n);

			for (PrestamoAgrupadoPorDocumento prestamoGroupByDocumento : lstDocumentosMasPrestados) {

				DocumentoModel documentoModel = assembler.toModel(prestamoGroupByDocumento.getDocumento());
				PrestamoAgrupadoPorDocumentoResponse prestamoGroupByDocumentoResponse = new PrestamoAgrupadoPorDocumentoResponse(
						prestamoGroupByDocumento.getCount(), documentoModel);

				lstDocumentosMasPrestadosModel.add(prestamoGroupByDocumentoResponse);
			}

		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "Error al recuperar los documentos m�s prestados";
		}

		DocumentosMasPrestadosResponse documentosMasPrestadosResponse = new DocumentosMasPrestadosResponse();
		documentosMasPrestadosResponse.setMensaje(mensaje);
		documentosMasPrestadosResponse.setLstDocumentosMasPrestados(lstDocumentosMasPrestadosModel);

		return new ResponseEntity<DocumentosMasPrestadosResponse>(documentosMasPrestadosResponse, status);
	}
}

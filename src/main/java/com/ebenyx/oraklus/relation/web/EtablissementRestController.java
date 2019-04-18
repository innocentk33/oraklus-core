package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.Etablissement;
import com.ebenyx.oraklus.relation.json.request.EtablissementJsonRequest;
import com.ebenyx.oraklus.relation.json.response.EtablissementJsonResponse;
import com.ebenyx.oraklus.relation.service.EtablissementService;

import com.ebenyx.oraklus.utils.Constants;
import com.ebenyx.oraklus.utils.RestApiResponse;
import com.ebenyx.oraklus.utils.error.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/settings/etablissement")
public class EtablissementRestController {

	private final EtablissementService etablissementService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public EtablissementRestController(EtablissementService etablissementService, JwtTokenUtil jwtTokenUtil) {
		this.etablissementService = etablissementService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody EtablissementJsonRequest etablissementJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <EtablissementJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <Etablissement> errorResponse = etablissementService.beforeSave(etablissementJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				Etablissement etablissement = errorResponse.getEntity();
				restApiResponse.setMessage((etablissement.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				etablissement = etablissementService.save(etablissement);
				EtablissementJsonResponse  etablissementJsonResponse = new EtablissementJsonResponse(etablissement);
				restApiResponse.setRow(etablissementJsonResponse);
				restApiResponse.setError(false);
				return ResponseEntity.ok(restApiResponse);
			}
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestParam(value="id") Long id, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Etablissement etablissement = etablissementService.findOne(id);
			ErrorResponse <Etablissement> errorResponse = etablissementService.beforeDelete(etablissement);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<EtablissementJsonResponse> restApiResponse = new RestApiResponse<>();
				etablissementService.delete(etablissement);
				restApiResponse.setError(false);
				restApiResponse.setMessage("? supprimé avec succès");
				return ResponseEntity.ok(restApiResponse);
			}
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/find-one", method = RequestMethod.GET)
	public ResponseEntity<?> findOne(@RequestParam(value="id") Long id, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse<EtablissementJsonResponse> restApiResponse = new RestApiResponse<>();
			Etablissement etablissement = etablissementService.findOne(id);
			restApiResponse.setError(false);
			if(etablissement == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				EtablissementJsonResponse etablissementJsonResponse = new EtablissementJsonResponse(etablissement);
				restApiResponse.setRow(etablissementJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<EtablissementJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <Etablissement> pages = etablissementService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <EtablissementJsonResponse> list = new ArrayList <>();
			for(Etablissement etablissement : pages.getContent()){
				list.add(new EtablissementJsonResponse(etablissement));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

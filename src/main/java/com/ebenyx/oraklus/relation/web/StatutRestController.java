package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.Statut;
import com.ebenyx.oraklus.relation.json.request.StatutJsonRequest;
import com.ebenyx.oraklus.relation.json.response.StatutJsonResponse;
import com.ebenyx.oraklus.relation.service.StatutService;

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
@RequestMapping("/settings/statut")
public class StatutRestController {

	private final StatutService statutService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public StatutRestController(StatutService statutService, JwtTokenUtil jwtTokenUtil) {
		this.statutService = statutService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody StatutJsonRequest statutJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <StatutJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <Statut> errorResponse = statutService.beforeSave(statutJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				Statut statut = errorResponse.getEntity();
				restApiResponse.setMessage((statut.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				statut = statutService.save(statut);
				StatutJsonResponse  statutJsonResponse = new StatutJsonResponse(statut);
				restApiResponse.setRow(statutJsonResponse);
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
			Statut statut = statutService.findOne(id);
			ErrorResponse <Statut> errorResponse = statutService.beforeDelete(statut);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<StatutJsonResponse> restApiResponse = new RestApiResponse<>();
				statutService.delete(statut);
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
			RestApiResponse<StatutJsonResponse> restApiResponse = new RestApiResponse<>();
			Statut statut = statutService.findOne(id);
			restApiResponse.setError(false);
			if(statut == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				StatutJsonResponse statutJsonResponse = new StatutJsonResponse(statut);
				restApiResponse.setRow(statutJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<StatutJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <Statut> pages = statutService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <StatutJsonResponse> list = new ArrayList <>();
			for(Statut statut : pages.getContent()){
				list.add(new StatutJsonResponse(statut));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

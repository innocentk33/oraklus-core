package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.Lit;
import com.ebenyx.oraklus.relation.json.request.LitJsonRequest;
import com.ebenyx.oraklus.relation.json.response.LitJsonResponse;
import com.ebenyx.oraklus.relation.service.LitService;

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
@RequestMapping("/settings/lit")
public class LitRestController {

	private final LitService litService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public LitRestController(LitService litService, JwtTokenUtil jwtTokenUtil) {
		this.litService = litService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody LitJsonRequest litJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <LitJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <Lit> errorResponse = litService.beforeSave(litJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				Lit lit = errorResponse.getEntity();
				restApiResponse.setMessage((lit.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				lit = litService.save(lit);
				LitJsonResponse  litJsonResponse = new LitJsonResponse(lit);
				restApiResponse.setRow(litJsonResponse);
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
			Lit lit = litService.findOne(id);
			ErrorResponse <Lit> errorResponse = litService.beforeDelete(lit);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<LitJsonResponse> restApiResponse = new RestApiResponse<>();
				litService.delete(lit);
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
			RestApiResponse<LitJsonResponse> restApiResponse = new RestApiResponse<>();
			Lit lit = litService.findOne(id);
			restApiResponse.setError(false);
			if(lit == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				LitJsonResponse litJsonResponse = new LitJsonResponse(lit);
				restApiResponse.setRow(litJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<LitJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <Lit> pages = litService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <LitJsonResponse> list = new ArrayList <>();
			for(Lit lit : pages.getContent()){
				list.add(new LitJsonResponse(lit));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

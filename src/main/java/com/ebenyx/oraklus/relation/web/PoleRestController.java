package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.Pole;
import com.ebenyx.oraklus.relation.json.request.PoleJsonRequest;
import com.ebenyx.oraklus.relation.json.response.PoleJsonResponse;
import com.ebenyx.oraklus.relation.service.PoleService;

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
@RequestMapping("/settings/pole")
public class PoleRestController {

	private final PoleService poleService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public PoleRestController(PoleService poleService, JwtTokenUtil jwtTokenUtil) {
		this.poleService = poleService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody PoleJsonRequest poleJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <PoleJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <Pole> errorResponse = poleService.beforeSave(poleJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				Pole pole = errorResponse.getEntity();
				restApiResponse.setMessage((pole.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				pole = poleService.save(pole);
				PoleJsonResponse  poleJsonResponse = new PoleJsonResponse(pole);
				restApiResponse.setRow(poleJsonResponse);
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
			Pole pole = poleService.findOne(id);
			ErrorResponse <Pole> errorResponse = poleService.beforeDelete(pole);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<PoleJsonResponse> restApiResponse = new RestApiResponse<>();
				poleService.delete(pole);
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
			RestApiResponse<PoleJsonResponse> restApiResponse = new RestApiResponse<>();
			Pole pole = poleService.findOne(id);
			restApiResponse.setError(false);
			if(pole == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				PoleJsonResponse poleJsonResponse = new PoleJsonResponse(pole);
				restApiResponse.setRow(poleJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<PoleJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <Pole> pages = poleService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <PoleJsonResponse> list = new ArrayList <>();
			for(Pole pole : pages.getContent()){
				list.add(new PoleJsonResponse(pole));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

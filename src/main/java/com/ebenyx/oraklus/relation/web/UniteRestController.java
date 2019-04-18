package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.Unite;
import com.ebenyx.oraklus.relation.json.request.UniteJsonRequest;
import com.ebenyx.oraklus.relation.json.response.UniteJsonResponse;
import com.ebenyx.oraklus.relation.service.UniteService;

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
@RequestMapping("/settings/unite")
public class UniteRestController {

	private final UniteService uniteService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public UniteRestController(UniteService uniteService, JwtTokenUtil jwtTokenUtil) {
		this.uniteService = uniteService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody UniteJsonRequest uniteJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <UniteJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <Unite> errorResponse = uniteService.beforeSave(uniteJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				Unite unite = errorResponse.getEntity();
				restApiResponse.setMessage((unite.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				unite = uniteService.save(unite);
				UniteJsonResponse  uniteJsonResponse = new UniteJsonResponse(unite);
				restApiResponse.setRow(uniteJsonResponse);
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
			Unite unite = uniteService.findOne(id);
			ErrorResponse <Unite> errorResponse = uniteService.beforeDelete(unite);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<UniteJsonResponse> restApiResponse = new RestApiResponse<>();
				uniteService.delete(unite);
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
			RestApiResponse<UniteJsonResponse> restApiResponse = new RestApiResponse<>();
			Unite unite = uniteService.findOne(id);
			restApiResponse.setError(false);
			if(unite == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				UniteJsonResponse uniteJsonResponse = new UniteJsonResponse(unite);
				restApiResponse.setRow(uniteJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<UniteJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <Unite> pages = uniteService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <UniteJsonResponse> list = new ArrayList <>();
			for(Unite unite : pages.getContent()){
				list.add(new UniteJsonResponse(unite));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

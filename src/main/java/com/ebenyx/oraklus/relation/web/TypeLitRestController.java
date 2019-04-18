package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.TypeLit;
import com.ebenyx.oraklus.relation.json.request.TypeLitJsonRequest;
import com.ebenyx.oraklus.relation.json.response.TypeLitJsonResponse;
import com.ebenyx.oraklus.relation.service.TypeLitService;

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
@RequestMapping("/settings/typeLit")
public class TypeLitRestController {

	private final TypeLitService typeLitService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public TypeLitRestController(TypeLitService typeLitService, JwtTokenUtil jwtTokenUtil) {
		this.typeLitService = typeLitService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody TypeLitJsonRequest typeLitJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <TypeLitJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <TypeLit> errorResponse = typeLitService.beforeSave(typeLitJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				TypeLit typeLit = errorResponse.getEntity();
				restApiResponse.setMessage((typeLit.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				typeLit = typeLitService.save(typeLit);
				TypeLitJsonResponse  typeLitJsonResponse = new TypeLitJsonResponse(typeLit);
				restApiResponse.setRow(typeLitJsonResponse);
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
			TypeLit typeLit = typeLitService.findOne(id);
			ErrorResponse <TypeLit> errorResponse = typeLitService.beforeDelete(typeLit);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<TypeLitJsonResponse> restApiResponse = new RestApiResponse<>();
				typeLitService.delete(typeLit);
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
			RestApiResponse<TypeLitJsonResponse> restApiResponse = new RestApiResponse<>();
			TypeLit typeLit = typeLitService.findOne(id);
			restApiResponse.setError(false);
			if(typeLit == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				TypeLitJsonResponse typeLitJsonResponse = new TypeLitJsonResponse(typeLit);
				restApiResponse.setRow(typeLitJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<TypeLitJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <TypeLit> pages = typeLitService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <TypeLitJsonResponse> list = new ArrayList <>();
			for(TypeLit typeLit : pages.getContent()){
				list.add(new TypeLitJsonResponse(typeLit));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

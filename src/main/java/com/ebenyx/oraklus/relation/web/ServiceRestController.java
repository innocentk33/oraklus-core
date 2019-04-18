package com.ebenyx.oraklus.relation.web;

import com.ebenyx.oraklus.security.JwtTokenUtil;
import com.ebenyx.oraklus.relation.entity.Service;
import com.ebenyx.oraklus.relation.json.request.ServiceJsonRequest;
import com.ebenyx.oraklus.relation.json.response.ServiceJsonResponse;
import com.ebenyx.oraklus.relation.service.ServiceService;

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
@RequestMapping("/settings/service")
public class ServiceRestController {

	private final ServiceService serviceService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public ServiceRestController(ServiceService serviceService, JwtTokenUtil jwtTokenUtil) {
		this.serviceService = serviceService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody ServiceJsonRequest serviceJsonRequest, HttpServletRequest request) {
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			RestApiResponse <ServiceJsonResponse> restApiResponse = new RestApiResponse<>();
			ErrorResponse <Service> errorResponse = serviceService.beforeSave(serviceJsonRequest);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				Service service = errorResponse.getEntity();
				restApiResponse.setMessage((service.getId() == null) ? "? enregistrée avec succès" : "? modifiée avec succès");
				service = serviceService.save(service);
				ServiceJsonResponse  serviceJsonResponse = new ServiceJsonResponse(service);
				restApiResponse.setRow(serviceJsonResponse);
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
			Service service = serviceService.findOne(id);
			ErrorResponse <Service> errorResponse = serviceService.beforeDelete(service);
			if(errorResponse.getError())
				return ResponseEntity.ok(errorResponse);
			else {
				RestApiResponse<ServiceJsonResponse> restApiResponse = new RestApiResponse<>();
				serviceService.delete(service);
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
			RestApiResponse<ServiceJsonResponse> restApiResponse = new RestApiResponse<>();
			Service service = serviceService.findOne(id);
			restApiResponse.setError(false);
			if(service == null){
				restApiResponse.setMessage("? inexistant");
			} else {
				ServiceJsonResponse serviceJsonResponse = new ServiceJsonResponse(service);
				restApiResponse.setRow(serviceJsonResponse);
			}
				return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load(@RequestParam(defaultValue = "0") int page, @RequestParam(value="keyword", required=false) String keyword, HttpServletRequest request) {
		RestApiResponse<ServiceJsonResponse> restApiResponse = new RestApiResponse<>();
		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			Page <Service> pages = serviceService.load(PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), keyword);
			List <ServiceJsonResponse> list = new ArrayList <>();
			for(Service service : pages.getContent()){
				list.add(new ServiceJsonResponse(service));
			}
			restApiResponse.setRows(list);
			restApiResponse.setMessage((pages.getTotalPages() == 0) ? "Aucun ? trouvé" : String.format("Total %d/%d", pages.getNumberOfElements(), pages.getTotalElements()));
			return ResponseEntity.ok(restApiResponse);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}

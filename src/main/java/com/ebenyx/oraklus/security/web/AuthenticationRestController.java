package com.ebenyx.oraklus.security.web;

import com.ebenyx.oraklus.exception.AuthenticationException;
import com.ebenyx.oraklus.security.entity.User;
import com.ebenyx.oraklus.security.json.response.JwtAuthenticationResponse;
import com.ebenyx.oraklus.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.ebenyx.oraklus.security.json.request.JwtAuthenticationRequest;
import com.ebenyx.oraklus.security.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@CrossOrigin
@RestController
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.filter}")
	private String tokenFilter;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final UserDetailsService userDetailsService;

	private final UserRepository userRepository;

	@Autowired
	public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, UserRepository userRepository) {

		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		// Reload password post-security so we can generate the token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		User user = userRepository.findByUsername(userDetails.getUsername());

		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(user.getUsername(), user.getFullName(), user.getEmail(), user.getAuthoritiesCSV(), token));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = (authToken.startsWith(tokenFilter)) ? authToken.substring(15) : authToken;
		String username = this.jwtTokenUtil.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(request)) {
			String refreshedToken = tokenFilter+jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(user.getUsername(), user.getFullName(), user.getEmail(), user.getAuthoritiesCSV(), refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	/**
	 * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
	 */
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("User is disabled!", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Bad credentials!", e);
		}
	}
}

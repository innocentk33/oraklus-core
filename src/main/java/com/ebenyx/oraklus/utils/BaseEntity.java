package com.ebenyx.oraklus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

import lombok.Setter;

/**
 * base entity abstract class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@MappedSuperclass
public abstract class BaseEntity<T> extends AuditEntity {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntity.class);

	/**
	* <p> Table identifier key</p>
	*/

	@Id
	@Getter @Setter
	@Column(name="ID", unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected T id;

	@Getter @Setter
	@Column(name = "CREATE_IP", updatable = false)
	protected String createIp;

	@Getter @Setter
	@Column(name = "UPDATE_IP", insertable = false)
	protected String updateIp;

	@PrePersist
	protected void onPrePersist() {
		try {
			Optional<HttpServletRequest> requestOptional = Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
			requestOptional.ifPresent(request -> {
				createIp = getClientIP(request);
			});
		} catch (IllegalStateException e) {
			LOGGER.error(String.format("IllegalStateException --> %s", Arrays.toString(e.getStackTrace())));
		}
	}

	@PreUpdate
	protected void onPreUpdate() {
		try {
			Optional<HttpServletRequest> requestOptional = Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
			requestOptional.ifPresent(request -> {
				updateIp = request.getRemoteAddr();
			});
		} catch (IllegalStateException e) {
			LOGGER.error("IllegalStateException / onPreUpdate", Arrays.toString(e.getStackTrace()));
		}
	}

	private String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

}


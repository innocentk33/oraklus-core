package com.ebenyx.oraklus.utils;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

	@CreatedBy
	@Getter @Setter
	@Column(name = "CREATED_BY", updatable = false)
	protected String createdBy;

	@CreatedDate
	@Getter @Setter
	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	private LocalDateTime createDate;

	@LastModifiedBy
	@Getter @Setter
	@Column(name = "UPDATED_BY", insertable = false)
	protected String updatedBy;

	@LastModifiedDate
	@Getter @Setter
	@Column(name = "UPDATE_DATE", insertable = false)
	private LocalDateTime updateDate;

}


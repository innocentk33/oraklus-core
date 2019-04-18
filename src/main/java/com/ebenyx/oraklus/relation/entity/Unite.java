package com.ebenyx.oraklus.relation.entity;

import javax.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.ebenyx.oraklus.utils.BaseEntity;

/**
 * Unite entity class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myUnite")
@Table(name="UNITE")
public class Unite extends BaseEntity<Long>{

	/**
	 * <p> Unite ...</p>
	 */

	@Getter @Setter
	@Column(name="NOM")
	private String nom;

	/**
	 * <p> Unite ...</p>
	 */

	@Getter @Setter
	@Column(name="ABRIVIATION")
	private String abriviation;

	/**
	 * <p> Unite ...</p>
	 */

	@Getter @Setter
	@Column(name="DESCRIPTION")
	private String description;

}
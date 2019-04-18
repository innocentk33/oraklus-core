package com.ebenyx.oraklus.relation.entity;

import javax.persistence.*;
import org.springframework.context.annotation.Scope;
import java.util.List;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.ebenyx.oraklus.utils.BaseEntity;

/**
 * Service entity class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myService")
@Table(name="SERVICE")
public class Service extends BaseEntity<Long>{

	/**
	 * <p> Service ...</p>
	 */

	@Getter @Setter
	@Column(name="NOM")
	private String nom;

	/**
	 * <p> Service ...</p>
	 */

	@Getter @Setter
	@Column(name="ABREVIATION")
	private String abreviation;

	/**
	 * <p> Service ...</p>
	 */

	@Getter @Setter
	@Column(name="DESCRIPTION")
	private LocalDate description;

	/**
	 * <p> Service ...</p>
	 */

	@Getter @Setter
	@OneToMany(mappedBy = "service")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List <Unite> unite;

	/**
	 * <p> Service ...</p>
	 */

	@Getter @Setter
	@OneToMany(mappedBy = "service")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List <Lit> lit;

}
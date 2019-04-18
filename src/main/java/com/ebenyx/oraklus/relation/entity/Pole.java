package com.ebenyx.oraklus.relation.entity;

import javax.persistence.*;
import org.springframework.context.annotation.Scope;
import java.util.List;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.ebenyx.oraklus.utils.BaseEntity;

/**
 * Pole entity class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myPole")
@Table(name="POLE")
public class Pole extends BaseEntity<Long>{

	/**
	 * <p> Pole ...</p>
	 */

	@Getter @Setter
	@Column(name="NOM")
	private String nom;

	/**
	 * <p> Pole ...</p>
	 */

	@Getter @Setter
	@Column(name="ABREVIATION")
	private String abreviation;

	/**
	 * <p> Pole ...</p>
	 */

	@Getter @Setter
	@Column(name="ABREVIATION")
	private String description;

	/**
	 * <p> Pole ...</p>
	 */

	@Getter @Setter
	@OneToMany(mappedBy = "pole")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List <Service> service;

}
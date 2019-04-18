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
 * Etablissement entity class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myEtablissement")
@Table(name="ETABLISSEMENT")
public class Etablissement extends BaseEntity<Long>{

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="NOM")
	private String nom;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="ABREVIATION")
	private String abreviation;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="VILLE")
	private String ville;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="COMMUNE")
	private String commune;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="LOCAL")
	private String local;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="CONTACT")
	private String contact;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@Column(name="DESCRIPTION")
	private String description;

	/**
	 * <p> Etablissement ...</p>
	 */

	@Getter @Setter
	@OneToMany(mappedBy = "etablissement")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List <Pole> pole;

}
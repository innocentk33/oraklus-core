package com.ebenyx.oraklus.relation.entity;

import javax.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.ebenyx.oraklus.utils.BaseEntity;

/**
 * Lit entity class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myLit")
@Table(name="LIT")
public class Lit extends BaseEntity<Long>{

	/**
	 * <p> Lit ...</p>
	 */

	@Getter @Setter
	@Column(name="NUMERO")
	private Integer numero;

	/**
	 * <p> Lit ...</p>
	 */

	@Getter @Setter
	@Column(name="DATE_ENTREE")
	private LocalDate dateEntree;

	/**
	 * <p> Lit ...</p>
	 */

	@Getter @Setter
	@Column(name="MAX_HEURE")
	private LocalDateTime maxHeure;

	/**
	 * <p> Lit ...</p>
	 */

	@ManyToOne
	@Getter @Setter
	@JoinColumn(name = "STATUT", nullable=false)
	private Statut statut;

	/**
	 * <p> Lit ...</p>
	 */

	@ManyToOne
	@Getter @Setter
	@JoinColumn(name = "TYPE_LIT", nullable=false)
	private Type_Lit typeLit;

}
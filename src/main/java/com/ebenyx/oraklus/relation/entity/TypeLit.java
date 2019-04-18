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
 * TypeLit entity class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myTypeLit")
@Table(name="TYPE_LIT")
public class TypeLit extends BaseEntity<Long>{

	/**
	 * <p> TypeLit ...</p>
	 */

	@Getter @Setter
	@Column(name="LIBELLE")
	private String libelle;

}
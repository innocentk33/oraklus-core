package com.ebenyx.oraklus.security.entity;

import com.ebenyx.oraklus.utils.DateUtils;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * User est la classe représentant un utilisateur du système.
 *
 * @author brice-boris
 * @version 1.0
*/

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myUser")
@Table(name = "USERS")
public class User {

	/**
	 * ID de la table <i>USERS</i>
	 */

	@Id
	@Getter @Setter
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
	@SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
	private Long id;

	/**
	 * Login ou identifiant de l'utilisateur.
	 */

	@NotNull
	@Getter @Setter
	@Size(min = 4, max = 50)
	@Column(name = "USERNAME", length = 50, unique = true)
	private String username;

	/**
	 * Mot de passe de l'utilisateur.
	 */

	@NotNull
	@Getter @Setter
	@Size(min = 4, max = 100)
	@Column(name = "PASSWORD", length = 100)
	private String password;

	/**
	 * Confirmation mot de passe de l'utilisateur.
	 */

	 @Transient
	@Getter @Setter
	private String confirmPassword;

	/**
	 * Ancien mot de passe de l'utilisateur.
	 */

	@Transient
	@Getter @Setter
	private String oldPassword;

	/**
	 * Nom complet de l'utilisateur.
	 */

	@NotNull
	@Getter @Setter
	@Size(min = 4, max = 50)
	@Column(name = "FULL_NAME", length = 50)
	private String fullName;

	/**
	 * Email de l'utilisateur.
	 */

	@NotNull
	@Getter @Setter
	@Size(min = 4, max = 50)
	@Column(name = "EMAIL", length = 50)
	private String email;

	/**
	 * Statut de l'utilisateur dans le système : <i>true = actif</i> et <i>false = inactif</i>.
	 */

	@NotNull
	@Getter @Setter
	@Column(name = "ENABLED")
	private Boolean enabled;

	/**
	 * Date de la création de l'utilisateur.
	 */

	@NotNull
	@Getter @Setter
	@Column(name = "CREATE_DATE")
	private LocalDateTime createDate;

	@Setter
	@Transient
	private String createDateValue;

	/**
	 * Date de la dernière modification de l'utilisateur.
	 */

	@Getter @Setter
	@Column(name = "LAST_EDIT_DATE")
	private LocalDateTime lastEditDate;

	@Setter
	@Transient
	private String lastEditDateValue;

	/**
	 * Date de la dernière modification du mot de passe de l'utilisateur.
	 */

	@Getter @Setter
	@Column(name = "LAST_PASSWORD_RESET_DATE")
	private LocalDateTime lastPasswordResetDate;

	@Setter
	@Transient
	private String lastPasswordResetDateValue;

	@Setter
	@Transient
	private String authoritiesCSV;

	/**
	 * Liste des roles de l'utilisateur.
	 *
	 * @see Authority
	*/

	@Getter @Setter
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "USER_AUTHORITY",
		joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
		inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
	private List<Authority> authorities;

	public String getCreateDateValue() {
		return DateUtils.dateToString(createDate);
	}

	public String getLastEditDateValue() {
		return DateUtils.dateToString(lastEditDate);
	}

	public String getAuthoritiesCSV() {
		StringBuilder sb = new StringBuilder();
		for (Iterator <Authority> iter = this.authorities.iterator(); iter.hasNext();){
			sb.append(iter.next().getName());
			if (iter.hasNext()) {
				sb.append(',');
			}
		}
		return sb.toString();
	}

	@PrePersist
	public void prePersist() {
		createDate = LocalDateTime.now();
		lastPasswordResetDate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		lastEditDate = LocalDateTime.now();
	}

}

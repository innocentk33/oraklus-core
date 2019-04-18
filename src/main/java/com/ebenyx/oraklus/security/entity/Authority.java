package com.ebenyx.oraklus.security.entity;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Scope("prototype")
@Component("myAuthority")
@Table(name = "AUTHORITY")
public class Authority {

	@Id
	@Getter @Setter
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHORITY_SEQ")
	@SequenceGenerator(name = "AUTHORITY_SEQ", sequenceName = "AUTHORITY_SEQ", allocationSize = 1)
	private Long id;

	@NotNull
	@Getter @Setter
	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", length = 50)
	private AuthorityName name;

	@Getter @Setter
	@ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
	private List<User> users;
}


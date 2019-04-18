package com.ebenyx.oraklus;

import com.ebenyx.oraklus.security.entity.Authority;
import com.ebenyx.oraklus.security.entity.AuthorityName;
import com.ebenyx.oraklus.security.entity.User;
import com.ebenyx.oraklus.security.repository.AuthorityRepository;
import com.ebenyx.oraklus.security.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Init {

	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Init(UserRepository userRepository, AuthorityRepository authorityRepository) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}

	@PostConstruct
	@Transactional(rollbackOn = Exception.class)
	public void init() {
		if(userRepository.count() == 0){
			List <Authority> authorities = new ArrayList <>();
			Authority authority = new Authority(null, AuthorityName.ROLE_ADMIN, null);
			authority = authorityRepository.save(authority);
			authorities.add(authority);
			authority = new Authority(null, AuthorityName.ROLE_USER, null);
			authority = authorityRepository.save(authority);
			authorities.add(authority);

			User user = new User(null, "brice.beda@gmail.com", passwordEncoder.encode("12345"), null, null, "Brice-Boris BEDA", "brice.beda@gmail.com", true, LocalDateTime.now(), null, null, null, null, null, null, authorities);
			userRepository.save(user);
		}
	}
}

package com.ebenyx.oraklus.security.service.impl;

import com.ebenyx.oraklus.exception.ValidationException;
import com.ebenyx.oraklus.security.entity.User;
import com.ebenyx.oraklus.security.repository.UserRepository;
import com.ebenyx.oraklus.security.service.UserService;
import com.ebenyx.oraklus.utils.error.ErrorMessage;
import com.ebenyx.oraklus.utils.error.ErrorPattern;
import com.ebenyx.oraklus.utils.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	ErrorPattern<User> ep;

	@Override
	public ErrorResponse <User> beforeSave(User user){

		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		if (user == null){
			logger.warn("User save error : user null");
			errors = ep.getError(0, "object user can not be null", "object user can not be null", errors);
		} else {
			if(user.getUsername() == null || user.getUsername().trim().equals("")){
				logger.warn("User save error : username is null");
				errors = ep.getError(1, "Le nom d'utilisateur est requis", "Le nom d'utilisateur est requis", errors);
			} else if((user.getId() == null && userRepository.findByUsername(user.getUsername()) != null) ||
					(user.getId() != null && userRepository.findByIdIsNotAndUsername(user.getId(), user.getUsername()) != null)){
				logger.warn("User save error : username is already saved");
				errors = ep.getError(1, "Ce nom d'utilisateur déjà enregistré", "Nom utilisateur déjà enregistré", errors);
			}

			if(user.getPassword() == null || user.getPassword().equals("")){
				logger.warn("User save error : password is null");
				errors = ep.getError(2, "Le mot de passe est requis", "Le mot de passe de l'utilisateur est requis", errors);
			}

			if(user.getConfirmPassword() == null || user.getConfirmPassword().equals("")){
				logger.warn("User save error : confirmpPassword is null");
				errors = ep.getError(3, "Veuillez confirmer le mot de passe svp", "Le mot de passe de l'utilisateur n'est pas confirmé", errors);
			} else if(user.getConfirmPassword().equals(user.getPassword())){
				logger.warn("User save error : password is not confirm");
				errors = ep.getError(3, "Veuillez confirmer le mot de passe svp", "Le mot de passe de l'utilisateur n'est pas confirmé", errors);
			}

			if(user.getFullName() == null || user.getFullName().equals("")){
				logger.warn("User save error : full name is null");
				errors = ep.getError(4, "Le nom complet est requis", "Le nom complet de l'utilisateur est requis", errors);
			}

			if(user.getEmail() == null || user.getEmail().trim().equals("")){
				logger.warn("User save error : email is null");
				errors = ep.getError(5, "L'email est requis", "L'email de l'utilisateur est requis", errors);
			} else if((user.getId() == null && userRepository.findByEmail(user.getEmail()) != null) ||
					(user.getId() != null && userRepository.findByIdIsNotAndEmail(user.getId(), user.getEmail()) != null)){
				logger.warn("User save error : email is already saved");
				errors = ep.getError(5, "Cet email déjà enregistré", "L'email de l'utilisateur déjà enregistré", errors);
			}
		}

		return ep.errorResultBuilder(errors, user);
	}

	/**
	 *
	 * @param user
	 * @return
	*/

	@Override
	public User save(User user) {
		if (user == null){
			logger.warn("User save failed");
			throw new ValidationException("user can not be null");
		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(user);
		logger.info("User save successfully", user);
		return user;
	}

	@Override
	public ErrorResponse<User> beforeDelete(User user){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(user == null){
			errors = ep.getError(0, "User not exist", "User not exist", errors);
		}
		return ep.errorResultBuilder(errors, user);
	}

	@Override
	public void delete(User user) {
		if (user == null){
			logger.warn("User delete failed");
			throw new ValidationException("user can not be null");
		}

		userRepository.delete(user);
		logger.info("User delete successfully", user);
	}

	@Override
	public User findOne(Long id) {
		if (id == null){
			logger.warn("User find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<User> user = userRepository.findById(id);
		logger.info("User find by id successfully", user);
		return user.get();
	}

	@Override
	public User findByUsername(String username) {
		if (username == null){
			logger.warn("User find by username failed");
			throw new ValidationException("username can not be null");
		}

		User user = userRepository.findByUsername(username);
		logger.info("User find by username successfully", user);
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		logger.info("Users found successfully", users.size());
		return users;
	}

	@Override
	public List<User> search(String keyword) {
		if (keyword == null){
			logger.warn("Search user by keyword failed");
			throw new ValidationException("keyword can not be null");
		}
		List<User> userList = null;
		logger.info("Search users by keyword successfully");
		return userList;
	}
}

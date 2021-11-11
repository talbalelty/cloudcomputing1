package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplementation implements UserService {
	private UserDao userDao;

	public UserServiceImplementation() {
		super();
	}

	@Autowired
	public UserServiceImplementation(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary createNewUser(UserBoundary userBoundary) {
		UserEntity userEntity = null;
		if (isValidEmail(userBoundary.getEmail())) {
			Optional<UserEntity> op = this.userDao.findById(userBoundary.getEmail());
			if (!op.isPresent()) {
				userEntity = convertToEntity(userBoundary);
				this.userDao.save(userEntity);
				return convertToBoundary(userEntity);
			}
			throw new RuntimeException(userBoundary.getEmail() + " already exists!");
		}
		throw new RuntimeException("Invalid email: " + userBoundary.getEmail());
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary getUserById(String email) {
		if (isValidEmail(email)) {
			Optional<UserEntity> op = this.userDao.findById(email);
			if (op.isPresent()) {
				return convertToBoundary(op.get());
			}
			throw new KeyNotFoundException("User with " + email + " not found");
		}
		throw new RuntimeException("Invalid email: " + email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String email, String password) {
		if (isValidEmail(email) && isValidPassword(password)) {
			Optional<UserEntity> op = this.userDao.findById(email);
			if (op.isPresent()) {
				UserEntity userEntity = op.get();
				if (userEntity.getPassword().equals(password)) {
					return convertToBoundary(userEntity);
				}
				throw new UnauthorizedException("Incorrect password!");
			}
			throw new KeyNotFoundException("User email " + email + " not found");
		}
		throw new RuntimeException("Invalid credentials");
	}

	@Override
	@Transactional(readOnly = false)
	public void updateUser(String email, UserBoundary userBoundary) {
		if (isValidEmail(email)) {
			Optional<UserEntity> op = this.userDao.findById(email);
			if (op.isPresent()) {
				UserEntity userEntity = convertToEntity(userBoundary);
				userEntity.setEmail(email);
				this.userDao.save(userEntity);
			}
			throw new KeyNotFoundException("User email " + email + " not found");
		}
		throw new RuntimeException("Invalid email: " + email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsersByCriteriaType(int size, int page, String sortBy, String sortOrder,
			String criteriaType, String criteriaValue) {
		if (sortBy == null || sortBy.trim().isEmpty()) {
			sortBy = "email";
		}

		Direction sortDirection = Direction.ASC;
		Optional<Direction> op = Direction.fromOptionalString(sortOrder.toUpperCase());
		if (op.isPresent()) {
			sortDirection = op.get();
		}

		String formattedCriteriaValue;
		List<UserEntity> entities = null;

		switch (criteriaType) {
		case "byEmailDomain":
			formattedCriteriaValue = "@%" + criteriaValue;
			entities = this.userDao.findAllByEmailLike(formattedCriteriaValue,
					PageRequest.of(page, size, sortDirection, sortBy));
			break;
		case "byBirthYear":
			formattedCriteriaValue = "%" + criteriaValue + "%";
			entities = this.userDao.findAllByBirthdateLike(formattedCriteriaValue,
					PageRequest.of(page, size, sortDirection, sortBy));
			break;
		case "byRole":
			formattedCriteriaValue = "%" + criteriaValue + "%";
			entities = this.userDao.findAllByRolesLike(formattedCriteriaValue,
					PageRequest.of(page, size, sortDirection, sortBy));
			break;
		default:
			Page<UserEntity> pageOfEntities = this.userDao.findAll(PageRequest.of(page, size, sortDirection, sortBy));
			entities = pageOfEntities.getContent();
			break;
		}

		List<UserBoundary> rv = new ArrayList<UserBoundary>();
		for (UserEntity userEntity : entities) {
			rv.add(convertToBoundary(userEntity));
		}
		return rv;
	}

	@Override
	public void deleteAll() {
		this.userDao.deleteAll();
	}

	private String getFormattedRoles(String[] roles) {
		String formattedRoles = "";

		for (int i = 0; i < roles.length; i++) {
			formattedRoles += roles[i];

			if (i != roles.length - 1)
				formattedRoles += " ";
		}

		return formattedRoles;
	}

	private UserEntity convertToEntity(UserBoundary userBoundary) {
		UserEntity userEntity = new UserEntity();
		if (isValidEmail(userBoundary.getEmail()) && isValidName(userBoundary.getName())
				&& isValidPassword(userBoundary.getPassword()) && isValidBirthdate(userBoundary.getBirthdate())
				&& isValidRoles(userBoundary.getRoles())) {
			userEntity.setEmail(userBoundary.getEmail());
			userEntity.setfName(userBoundary.getName().get("first"));
			userEntity.setlName(userBoundary.getName().get("last"));
			userEntity.setPassword(userBoundary.getPassword());
			userEntity.setBirthdate(userBoundary.getBirthdate().toString());
			userEntity.setRoles(getFormattedRoles(userBoundary.getRoles()));
			return userEntity;
		} else {
			throw new RuntimeException("Invalid Information");
		}
	}

	private UserBoundary convertToBoundary(UserEntity userEntity) {
		String email = userEntity.getEmail();
		String fName = userEntity.getfName();
		String lName = userEntity.getlName();
		Map<String, String> name = new HashMap<String, String>();
		name.put("first", fName);
		name.put("last", lName);
		LocalDate birthdate = LocalDate.parse(userEntity.getBirthdate());
		String[] roles = userEntity.getRoles().split(" ");
		UserBoundary boundary = new UserBoundary(email, name, null, birthdate, roles);
		return boundary;
	}

	private boolean isValidRoles(String[] roles) {
		if (roles != null) {
			for (String role : roles) {
				if (role == null || role.length() == 0) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean isValidBirthdate(LocalDate birthdate) {
		return birthdate != null;
	}

	private boolean isValidPassword(String password) {
		return password.matches("^(?=.*[a-zA-Z0-9])[0-9a-zA-Z!@#$%]{5,}$");
	}

	private boolean isValidName(Map<String, String> name) {
		if (name != null && name.get("first") != null && name.get("last") != null) {
			return name.get("first").length() > 0 && name.get("last").length() > 0;
		}
		return false;
	}

	private boolean isValidEmail(String email) {
		return email != null && email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	}

}

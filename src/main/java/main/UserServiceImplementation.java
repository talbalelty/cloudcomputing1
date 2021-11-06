package main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public UserBoundary createNewUser(UserBoundary userBoundary) {
		UserEntity userEntity = convertToEntity(userBoundary);

		Optional<UserEntity> op = this.userDao.findById(userEntity.getEmail());
		if (!op.isPresent()) {
			this.userDao.save(userEntity);
		} else {
			throw new RuntimeException();
		}
		return userBoundary;
	}

	@Override
	public UserBoundary getUserById(String email) {
		if (isValidEmail(email)) {
			Optional<UserEntity> op = this.userDao.findById(email);
			if (op.isPresent()) {
				return convertToBoundary(op.get());
			}
		}
		throw new KeyNotFoundException("User with " + email + " not found.");
	}

	private UserEntity convertToEntity(UserBoundary userBoundary) {
		UserEntity userEntity = new UserEntity();
		if (isValidEmail(userBoundary.getEmail()) && isValidName(userBoundary.getName())
				&& isValidPassword(userBoundary.getPassword()) && isValidBirthday(userBoundary.getBirthday())
				&& isValidRoles(userBoundary.getRoles())) {
			userEntity.setEmail(userBoundary.getEmail());
			userEntity.setfName(userBoundary.getName().get("first"));
			userEntity.setlName(userBoundary.getName().get("last"));
			userEntity.setPassword(userBoundary.getPassword());
			userEntity.setBirthday(userBoundary.getBirthday());
			userEntity.setRoles(userBoundary.getRoles());
			return userEntity;
		} else {
			throw new RuntimeException();
		}
	}

	private UserBoundary convertToBoundary(UserEntity userEntity) {
		String email = userEntity.getEmail();
		String fName = userEntity.getfName();
		String lName = userEntity.getlName();
		Map<String, String> name = new HashMap<String, String>();
		name.put("first", fName);
		name.put("last", lName);
		LocalDate birthday = userEntity.getBirthday();
		String[] roles = userEntity.getRoles();
		UserBoundary boundary = new UserBoundary(email, name, null, birthday, roles);
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

	private boolean isValidBirthday(LocalDate birthday) {
		return birthday != null;
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

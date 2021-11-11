package main;

import java.time.LocalDate;
import java.util.Map;

// {
//   "email": "tal@email.com",
//   "name": {
//     "first": "string",
//     "last": "string"
//   },
//   "password": "string",
//   "birthdate": "11-08-2021",
//   "roles": [
//     "string"
//   ]
// }

public class UserBoundary {
	private String email;
	private Map<String, String> name;
	private String password;
	private LocalDate birthdate;
	private String[] roles;

	public UserBoundary() {
	}

	public UserBoundary(String email, Map<String, String> name, String password, LocalDate birthdate, String[] roles) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.birthdate = birthdate;
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthday(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

}

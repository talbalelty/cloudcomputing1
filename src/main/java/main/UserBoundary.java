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
//   "birthday": "2021-11-08",
//   "roles": [
//     "string"
//   ]
// }

public class UserBoundary {
	private String email;
	private Map<String, String> name;
	private String password;
	private LocalDate birthday;
	private String[] roles;

	public UserBoundary() {
	}

	public UserBoundary(String email, Map<String, String> name, String password, LocalDate birthday, String[] roles) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

}

package main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
	private UserService userService;

	public UserController() {
	}

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@RequestMapping(path = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody UserBoundary userBoundary) {
		return this.userService.createNewUser(userBoundary);
	}

	/*
	 * There is a difference in the usage of @RequestParam and @PathVariable.
	 * 
	 * @RequestParam finds the params implicitly, while @PathVariable finds the
	 * params explicitly.
	 */

	// criteriaType can be one of the following: "byEmailDomain / byBirthYear /
	// byRole
	@RequestMapping(path = "/users/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserBoundary> getAllUsers(
			@RequestParam(name = "criteriaType", required = false, defaultValue = "") String criteriaType,
			@RequestParam(name = "criteriaValue", required = false) String criteriaValue,
			// customDomain.co.il
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "email") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order) {
		return this.userService.getAllUsersByCriteriaType(size, page, sortAttribute, order, criteriaType, criteriaValue);
	}

	@RequestMapping(path = "/users/login/{email}?password={password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("email") String email,
			@RequestParam(name = "password", required = false) String password) {
		return this.userService.login(email, password);
	}

	@RequestMapping(path = "/users/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary getUserById(@PathVariable("email") String email) {
		return this.userService.getUserById(email);
	}

	@RequestMapping(path = "/users/{email}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody UserBoundary userBoundary, @PathVariable("email") String email) {
		this.userService.updateUser(email, userBoundary);
	}

	@RequestMapping(path = "/users", method = RequestMethod.DELETE)
	public void deleteAll() {
		this.userService.deleteAll();
	}

}

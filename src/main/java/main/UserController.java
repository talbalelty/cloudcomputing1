package main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(path="/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody UserBoundary userBoundary) {
		return this.userService.createNewUser(userBoundary);
	}
	
	@RequestMapping(path="/users/search/size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserBoundary> getAllUsers(
			@PathVariable("size") int size,
			@PathVariable("page") int page,
			@PathVariable("sortAttribute") String sortAttribute,
			@PathVariable("order") String order) {
		return this.userService.getAllUsers(size, page, sortAttribute, order);
	}
	
	@RequestMapping(path="/users/{email}?password={password}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(
			@PathVariable("email") String email,
			@PathVariable("password") String password) {
		return this.userService.login(email, password);
	}
	
	@RequestMapping(path="/users/{email}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary getUserById(@PathVariable("email") String email) {
		return this.userService.getUserById(email);
	}	
	
	@RequestMapping(path="/users/{email}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(
			@RequestBody UserBoundary userBoundary, 
			@PathVariable("email") String email) {
		this.userService.updateUser(email, userBoundary);
	}
	
	@RequestMapping(path="/users",
			method = RequestMethod.DELETE)
	public void deleteAll() {
		this.userService.deleteAll();
	}
}

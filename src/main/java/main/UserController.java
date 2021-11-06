package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
	
	
	
	
	
}

package finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.NewUserDetails;
import finance.boundaries.UserBoundary;
import finance.logic.UserService;

@RestController
public class UserController {
	private UserService userService;
	private final String pageSize = "10";

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	@RequestMapping(path = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser(@RequestBody NewUserDetails details) {
		UserBoundary user = new UserBoundary();
		user.setFirstName(details.getFirstName());
		user.setLastName(details.getLastName());
		user.setPassword(details.getPassword());
		user.setEmail(details.getEmail().toLowerCase());
		return userService.createUser(user);
	}

	@RequestMapping(path = "/user/login/{userEmail}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("userEmail") String email, @PathVariable("password") String password) {
		return userService.login(email.toLowerCase(), password);
	}

	@RequestMapping(path = "/user/login/gmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginGmail(@RequestBody UserBoundary user) {
		return userService.loginGmail(user);
	}

	@RequestMapping(path = "/user/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("userId") String userId, @RequestBody UserBoundary user) {
		userService.updateUser(userId, user);
	}

	@RequestMapping(path = "/user/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] getAllUsers(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = pageSize) int size) {
		return userService.getAllUsers(page, size).toArray(new UserBoundary[0]);
	}
}
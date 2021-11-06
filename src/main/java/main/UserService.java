package main;

public interface UserService {

	public UserBoundary createNewUser(UserBoundary userBoundary);

	public UserBoundary getUserById(String email);

}

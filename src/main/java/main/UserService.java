package main;

import java.util.List;

public interface UserService {

	public UserBoundary createNewUser(UserBoundary userBoundary);

	public UserBoundary getUserById(String email);

	public UserBoundary login(String email, String password);

	public void updateUser(String email, UserBoundary userBoundary);

	public void deleteAll();

	// public List<UserBoundary> getAllUsers(int size, int page, String sortBy,
	// String sortOrder);

	public List<UserBoundary> getAllUsersByCriteriaType(int size, int page, String sortBy, String sortOrder,
			String criteriaType, String criteriaValue);

}

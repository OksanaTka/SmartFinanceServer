package finance.logic;

import java.util.List;

import finance.boundaries.UserBoundary;

public interface UserService {

	public UserBoundary createUser(UserBoundary user);

	public UserBoundary login(String userEmail,String password);
	
	public UserBoundary loginGmail(UserBoundary user);

	public void updateUser(String userEmail, UserBoundary update);
	
	//Admin functions	
	public List<UserBoundary> getAllUsers(int page, int size);
	
}

package finance.logic;

import java.util.List;

import finance.boundaries.UserBoundary;

public interface UserService {

	public UserBoundary createUser(UserBoundary user);

	public UserBoundary login(String userEmail,String password);
	
	public UserBoundary loginGmail(String userEmail,String password);

	public UserBoundary updateUser(String userEmail, UserBoundary update);
	
	//Admin functions	
	public List<UserBoundary> getAllUsers(int page, int size);
	
}

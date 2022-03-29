package finance.logic.jpa;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.boundaries.UserBoundary;
import finance.data.UserEntity;
import finance.data.dao.UserDao;
import finance.logic.UserService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.UnauthorizedException;
import finance.utils.Utils;

@Service
public class UserJpa implements UserService {

	private UserDao userDao;
	private Utils utils;
	private EntityConverter<UserEntity, UserBoundary> entityConverter;

	public UserJpa() {
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setEntityConverter(EntityConverter<UserEntity, UserBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary user) {
		utils.assertNull(user);
		utils.assertNull(user.getEmail());
		utils.assertValidEmail(user.getEmail());
		utils.assertNull(user.getPassword());

		// Check if there is a user with the same email
		List<UserEntity> users = this.userDao.findAllByEmail(user.getEmail());
		if (!users.isEmpty()) {
			throw new ConflictException("Email already exists: " + user.getEmail());
		}

		UserEntity entity = this.entityConverter.fromBoundary(user);
		entity.setEmail(user.getEmail());
		entity.setPassword((user.getPassword()));
		entity = this.userDao.save(entity);

		return this.entityConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userEmail, String password) {
		utils.assertNull(userEmail);
		utils.assertValidEmail(userEmail);
		utils.assertNull(password);

		List<UserEntity> users = this.userDao.findAllByEmail(userEmail);

		if (!users.isEmpty()) {
			UserEntity entity = users.get(0);
			if (entity.getPassword().equals(password)) {
				return this.entityConverter.toBoundary(entity);
			} else {
				throw new UnauthorizedException("password incorrect: " + password);
			}
		} else {
			throw new NotFoundException("could not find user by email: " + userEmail);
		}
	}

	@Override
	@Transactional
	public UserBoundary loginGmail(String userEmail, String password) {
		utils.assertNull(userEmail);
		utils.assertValidEmail(userEmail);
		utils.assertNull(password);

		List<UserEntity> users = this.userDao.findAllByEmail(userEmail);

		if (!users.isEmpty()) {
			UserEntity entity = users.get(0);

			if (entity.getPassword().equals(password)) {
				return this.entityConverter.toBoundary(entity);
			} else {
				throw new UnauthorizedException("password incorrect: " + password);
			}
		} else {
			UserBoundary user = new UserBoundary();
			user.setPassword(password);
			user.setEmail(userEmail.toLowerCase());
			return createUser(user);
		}
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userId, UserBoundary update) {
		utils.assertNull(update);
		utils.assertNull(userId);

		List<UserEntity> users = this.userDao.findAllById(userId);

		if (!users.isEmpty()) {
			UserEntity existing = users.get(0);
			if (update.getAvatar() != null) {
				existing.setAvatar(update.getAvatar());
			}
			if (update.getFirstName() != null) {
				existing.setFirstName(update.getFirstName());
			}
			if (update.getLastName() != null) {
				existing.setLastName(update.getLastName());
			}
			if (update.getPhone() != null) {
				existing.setPhone(update.getPhone());
			}
			if (update.getGender() != null) {
				existing.setGender(update.getGender());
			}
			if (update.getIdentityNumber() != null) {
				existing.setIdentity_number(update.getIdentityNumber());
			}
			if (update.getPassword() != null) {
				existing.setPassword(update.getPassword());
			}
			if (update.getEmail() != null) {
				
				String newEmail = update.getEmail().toLowerCase();
				if (utils.checkValidEmail(newEmail)) {
					// check if this email already exist
					List<UserEntity> newUsers = this.userDao.findAllByEmail(newEmail);

					// set new email to user
					if (newUsers.isEmpty()) {
						existing.setEmail(newEmail);
					} else {
						UserEntity entity = newUsers.get(0);
						throw new ConflictException("This email is allready used  " + entity.getEmail());
					}

				}
			}

			existing = this.userDao.save(existing);
			return this.entityConverter.toBoundary(existing);

		} else {
			throw new NotFoundException("could not find user by email: " + userId);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(int page, int size) {
		return this.userDao.findAll(PageRequest.of(page, size, Direction.DESC, "id")).stream()
				.map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

}

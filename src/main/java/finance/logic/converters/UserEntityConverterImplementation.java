package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.UserBoundary;
import finance.data.UserEntity;

@Component
public class UserEntityConverterImplementation implements EntityConverter<UserEntity, UserBoundary> {

	@Override
	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary ub = new UserBoundary();
		ub.setId(entity.getId());
		ub.setEmail(entity.getEmail());
		ub.setFirstName(entity.getFirstName());
		ub.setLastName(entity.getLastName());
		ub.setPassword(entity.getPassword());
		ub.setAvatar(entity.getAvatar());
		ub.setGender(entity.getGender());
		ub.setPhone(entity.getPhone());
		ub.setIdentityNumber(entity.getIdentity_number());
		return ub;
	}

	@Override
	public UserEntity fromBoundary(UserBoundary boundary) {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(boundary.getFirstName());
		userEntity.setLastName(boundary.getLastName());
		userEntity.setPassword(boundary.getPassword());
		userEntity.setEmail(boundary.getEmail());
		userEntity.setAvatar(boundary.getAvatar());
		userEntity.setGender(boundary.getGender());
		userEntity.setPhone(boundary.getPhone());
		userEntity.setIdentity_number(boundary.getIdentityNumber());
		return userEntity;
	}

}

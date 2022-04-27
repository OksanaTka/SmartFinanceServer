package finance.logic.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.boundaries.NewPensionAccountDetails;
import finance.boundaries.PensionAccountBoundary;
import finance.data.FundEntity;
import finance.data.PensionAccountEntity;
import finance.data.PensionDetailsEntity;
import finance.data.UserEntity;
import finance.data.dao.FundDao;
import finance.data.dao.PensionAccountDao;
import finance.data.dao.PensionDetailsDao;
import finance.data.dao.UserDao;
import finance.logic.PensionAccountService;
import finance.logic.converters.ExtendedEntityConverter;
import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.UnauthorizedException;
import finance.utils.UnsupportedExecption;
import finance.utils.Utils;

@Service
public class PensionAccountJpa implements PensionAccountService {

	private PensionAccountDao pensionDao;
	private PensionDetailsDao pensionDetailsDao;
	private FundDao fundDao;
	private UserDao userDao;
	private Utils utils;
	private ExtendedEntityConverter<PensionAccountEntity, PensionAccountBoundary> entityConverter;

	public PensionAccountJpa() {
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setPensionAccountDao(PensionAccountDao pensionAccountDao) {
		this.pensionDao = pensionAccountDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setFundDao(FundDao fundDao) {
		this.fundDao = fundDao;
	}

	@Autowired
	public void setPensionDetailsDao(PensionDetailsDao pensionDetailsDao) {
		this.pensionDetailsDao = pensionDetailsDao;
	}

	@Autowired
	public void setEntityConverter(
			ExtendedEntityConverter<PensionAccountEntity, PensionAccountBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	@Transactional
	public PensionAccountBoundary createPensionAccount(NewPensionAccountDetails pensionAccount) {
		utils.assertNull(pensionAccount);
		utils.assertNull(pensionAccount.getUserId());
		utils.assertNull(pensionAccount.getFundId());
		utils.assertNull(pensionAccount.getIdentityNumber());
		utils.assertNull(pensionAccount.getCode());
		utils.assertNull(pensionAccount.getPhone());

		// Check if the Fund is supported
		List<FundEntity> funds = this.fundDao.findAllByFundId(pensionAccount.getFundId());
		if (funds.isEmpty()) {
			throw new UnsupportedExecption("Fund isn't supported " + pensionAccount.getFundId());
		}

		// Connect to pension API and get details
		List<PensionDetailsEntity> pensionDetails = this.pensionDetailsDao
				.findAllByFundIdAndIdentityNumber(pensionAccount.getFundId(), pensionAccount.getIdentityNumber());

		if (pensionDetails.isEmpty()) {
			throw new NotFoundException("Account doesn't exist");
		}

		PensionDetailsEntity pensionDetailsEntity = pensionDetails.get(0);
		if (!pensionDetailsEntity.getCode().equals(pensionAccount.getCode())) {
			throw new UnauthorizedException("Account code is incorrect ");
		}

		// Check if the pension account already exists in database
		List<PensionAccountEntity> pensionAccounts = this.pensionDao
				.findAllByFundIdAndUserId(pensionAccount.getFundId(), pensionAccount.getUserId());
		if (!pensionAccounts.isEmpty()) {
			throw new ConflictException("Account already exists: " + pensionAccounts.get(0).getPensionId());
		}

		PensionAccountEntity entity = this.entityConverter.fromDetails(pensionDetailsEntity);
		entity = this.pensionDao.save(entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	@Transactional
	public PensionAccountBoundary updatePensionAccount(String userId,String fundId) {
		utils.assertNull(userId);
		utils.assertNull(fundId);

		// Get user from DB
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("User doesn't exist");
		}
		UserEntity user = users.get(0);

		// Connect to pension API
		List<PensionDetailsEntity> pensionDetails = this.pensionDetailsDao.findAllByFundIdAndIdentityNumber(fundId,
				user.getIdentity_number());

		if (pensionDetails.isEmpty()) {
			throw new NotFoundException("Account doesn't exist");
		}

		PensionDetailsEntity updatedDetails = pensionDetails.get(0);
		// Get pension account from DB and update
		List<PensionAccountEntity> pensionAccounts = this.pensionDao.findAllByFundIdAndUserId(fundId, userId);
		if (pensionAccounts.isEmpty()) {
			throw new ConflictException("Account doesn't exist " + pensionAccounts.get(0).getPensionId());
		}

		PensionAccountEntity pensionAccount = pensionAccounts.get(0);
		pensionAccount.setCompensation(updatedDetails.getCompensation());
		pensionAccount.setEmployerDeposit(updatedDetails.getEmployerDeposit());
		pensionAccount.setManagementFee(updatedDetails.getManagementFee());
		pensionAccount.setTotalAmount(updatedDetails.getTotalAmount());
		pensionAccount.setWorkerDeposit(updatedDetails.getWorkerDeposit());

		pensionAccount = this.pensionDao.save(pensionAccount);
		return this.entityConverter.toBoundary(pensionAccount);
	}

	@Override
	public PensionAccountBoundary getSpecificPensionAccount(String pensionId) {
		utils.assertNull(pensionId);

		List<PensionAccountEntity> pensionAccount = this.pensionDao.findAllByPensionId(pensionId);
		if (!pensionAccount.isEmpty()) {
			return entityConverter.toBoundary(pensionAccount.get(0));
		} else {
			throw new NotFoundException("Could not find pension: " + pensionId);
		}
	}

	@Override
	public List<PensionAccountBoundary> getAllPensionAccounts(String userId) {
		utils.assertNull(userId);

		List<UserEntity> users = this.userDao.findAllById(userId);
		if (!users.isEmpty()) {
			UserEntity entity = users.get(0);
			return this.pensionDao.findAllByUserId(entity.getId()).stream().map(this.entityConverter::toBoundary)
					.collect(Collectors.toList());
		} else {
			throw new NotFoundException("could not find user by id: " + userId);
		}
	}

	@Override
	public void deletePensionAccount(String userId, String pensionId) {
		utils.assertNull(userId);
		utils.assertNull(pensionId);

		// Get the pension account
		List<PensionAccountEntity> pensionAccounts = this.pensionDao.findAllByPensionId(pensionId);
		if (!pensionAccounts.isEmpty()) {

			// Check if the account belongs to user
			PensionAccountEntity pensionAccount = pensionAccounts.get(0);
			if (pensionAccount.getUserId().equals(userId)) {
				this.pensionDao.delete(pensionAccount);
			} else {
				throw new NotFoundException(
						"This pension account " + pensionId + " doesn't belong to the user: " + userId);
			}

		} else {
			throw new NotFoundException("There is no pension account with this id: " + pensionId);
		}

	}

}

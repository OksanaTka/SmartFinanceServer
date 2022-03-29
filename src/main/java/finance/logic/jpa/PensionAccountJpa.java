package finance.logic.jpa;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.boundaries.PensionAccountBoundary;
import finance.data.FundEntity;
import finance.data.PensionAccountEntity;
import finance.data.UserEntity;
import finance.data.dao.FundDao;
import finance.data.dao.PensionAccountDao;
import finance.data.dao.UserDao;
import finance.logic.PensionAccountService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.UnsupportedExecption;
import finance.utils.Utils;

@Service
public class PensionAccountJpa implements PensionAccountService {

	private PensionAccountDao pensionDao;
	private FundDao fundDao;
	private UserDao userDao;
	private Utils utils;
	private EntityConverter<PensionAccountEntity, PensionAccountBoundary> entityConverter;

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
	public void setEntityConverter(EntityConverter<PensionAccountEntity, PensionAccountBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	@Transactional
	public PensionAccountBoundary createPensionAccount(PensionAccountBoundary pensionAccount) {
		utils.assertNull(pensionAccount);
		utils.assertNull(pensionAccount.getUserId());
		utils.assertNull(pensionAccount.getFundId());

		// Check if the Fund is supported
		List<FundEntity> funds = this.fundDao.findAllByFundId(pensionAccount.getFundId());
		if (funds.isEmpty()) {
			throw new UnsupportedExecption("Fund isn't supported " + pensionAccount.getFundId());
		}

		// Check if the pension account already exists
		List<PensionAccountEntity> pensionAccounts = this.pensionDao.findAllByUserId(pensionAccount.getUserId());
		if (!pensionAccounts.isEmpty()) {
			for (Iterator<PensionAccountEntity> iterator = pensionAccounts.iterator(); iterator.hasNext();) {
				PensionAccountEntity pensionAccountEntity = (PensionAccountEntity) iterator.next();

				// Check if there is a pension account with this fund company
				if (pensionAccountEntity.getFundId().equals(pensionAccount.getFundId())) {
					throw new ConflictException("Account already exists: " + pensionAccountEntity.getPensionId());
				}
			}
		}

		PensionAccountEntity entity = this.entityConverter.fromBoundary(pensionAccount);
		entity.setFundId(pensionAccount.getFundId());
		entity.setUserId(pensionAccount.getUserId());
		entity = this.pensionDao.save(entity);
		return this.entityConverter.toBoundary(entity);
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

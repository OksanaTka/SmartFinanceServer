package finance.logic.jpa;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.boundaries.BankAccountBoundary;
import finance.data.BankAccountEntity;
import finance.data.BankEntity;
import finance.data.UserEntity;
import finance.data.dao.BankAccountDao;
import finance.data.dao.BankDao;
import finance.data.dao.UserDao;
import finance.logic.BankAccountService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.UnsupportedExecption;
import finance.utils.Utils;

@Service
public class BankAccountJpa implements BankAccountService {

	private BankAccountDao bankAccountDao;
	private BankDao bankDao;
	private UserDao userDao;
	private Utils utils;
	private EntityConverter<BankAccountEntity, BankAccountBoundary> entityConverter;

	public BankAccountJpa() {
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setBankAccountDao(BankAccountDao bankAccountDao) {
		this.bankAccountDao = bankAccountDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}

	@Autowired
	public void setEntityConverter(EntityConverter<BankAccountEntity, BankAccountBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	@Transactional
	public BankAccountBoundary createBankAccount(BankAccountBoundary bankAccount) {
		utils.assertNull(bankAccount);
		utils.assertNull(bankAccount.getBankId());
		utils.assertNull(bankAccount.getUserId());
		utils.assertNull(bankAccount.getAccountCode());
		utils.assertNull(bankAccount.getAccountPassword());

		// Check if the bank is supported
		List<BankEntity> banks = this.bankDao.findAllByBankId(bankAccount.getBankId());
		if (banks.isEmpty()) {
			throw new UnsupportedExecption("Bank isnt supported " + bankAccount.getBankId());
		}

		// Check if the account already exists
		List<BankAccountEntity> bankAccounts = this.bankAccountDao.findAllByBankId(bankAccount.getBankId());
		if (!bankAccounts.isEmpty()) {
			for (Iterator<BankAccountEntity> iterator = bankAccounts.iterator(); iterator.hasNext();) {
				BankAccountEntity bankAccountEntity = (BankAccountEntity) iterator.next();
				if (bankAccountEntity.getAccountCode().equals(bankAccount.getAccountCode())) {
					throw new ConflictException("Account already exists: " + bankAccount.getAccountCode());
				}
			}
		}

		BankAccountEntity entity = this.entityConverter.fromBoundary(bankAccount);
		entity.setBankId(bankAccount.getBankId());
		entity.setUserId(bankAccount.getUserId());
		entity.setAccountCode(bankAccount.getAccountCode());
		entity.setAccountPassword(bankAccount.getAccountPassword());
		entity = this.bankAccountDao.save(entity);

		return this.entityConverter.toBoundary(entity);
	}

	@Override
	public BankAccountBoundary getSpecificBankAccount(String bankAccountId) {
		utils.assertNull(bankAccountId);

		List<BankAccountEntity> bankAccount = this.bankAccountDao.findAllByAccountId(bankAccountId);
		if (!bankAccount.isEmpty()) {
			return entityConverter.toBoundary(bankAccount.get(0));
		} else {
			throw new NotFoundException("Could not find bank: " + bankAccountId);
		}
	}

	@Override
	public List<BankAccountBoundary> getAllBankAccounts(String userId) {
		utils.assertNull(userId);

		List<UserEntity> users = this.userDao.findAllById(userId);
		if (!users.isEmpty()) {
			UserEntity entity = users.get(0);
			return this.bankAccountDao.findAllByUserId(entity.getId()).stream().map(this.entityConverter::toBoundary)
					.collect(Collectors.toList());
		} else {
			throw new NotFoundException("Could not find user: " + userId);
		}
	}

	@Override
	public void deleteBankAccount(String userId, String bankAccountId) {
		utils.assertNull(userId);
		utils.assertNull(bankAccountId);

		// Get the bank with this bankId
		List<BankAccountEntity> bankAccounts = this.bankAccountDao.findAllByAccountId(bankAccountId);
		if (!bankAccounts.isEmpty()) {

			// check if the account belongs to the user
			BankAccountEntity bankAccount = bankAccounts.get(0);
			if (bankAccount.getUserId().equals(userId)) {
				this.bankAccountDao.delete(bankAccount);
			} else {
				throw new NotFoundException(
						"This bank account " + bankAccountId + " doesn't belong to the user: " + userId);
			}
		} else {
			throw new NotFoundException("There is no bank account with this id: " + bankAccountId);
		}

	}
}

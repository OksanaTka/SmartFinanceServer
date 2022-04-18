package finance.logic.jpa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.BankTransactionsDetailsBoundary;
import finance.boundaries.TransactionBoundary;
import finance.data.BankTransactionsDetailsEntity;
import finance.data.TransactionEntity;
import finance.data.UserEntity;
import finance.data.dao.BankTransactionsDetailsDao;
import finance.data.dao.TransactionDao;
import finance.data.dao.UserDao;
import finance.logic.TransactionService;
import finance.logic.converters.EntityConverter;
import finance.utils.NotFoundException;
import finance.utils.Utils;

@Service
public class TransactionJpa implements TransactionService {

	private BankTransactionsDetailsDao bankTransactionsDetailsDao;
	private TransactionDao transactionDao;
	private UserDao userDao;
	private Utils utils;
	private EntityConverter<TransactionEntity, TransactionBoundary> entityConverter;

	public TransactionJpa() {
	}

	@Autowired
	public void setBankTransactionsDetailsDao(BankTransactionsDetailsDao bankTransactionsDetailsDao) {
		this.bankTransactionsDetailsDao = bankTransactionsDetailsDao;
	}

	@Autowired
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setEntityConverter(EntityConverter<TransactionEntity, TransactionBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	public TransactionBoundary createTransaction(TransactionBoundary transactionBoundary) {
		utils.assertNull(transactionBoundary);
		utils.assertNull(transactionBoundary.getUserId());
		utils.assertNull(transactionBoundary.getBankAccountId());

		TransactionEntity entity = this.entityConverter.fromBoundary(transactionBoundary);
		entity = this.transactionDao.save(entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	public TransactionBoundary getSpecificTransaction(String transactionId) {
		utils.assertNull(transactionId);

		List<TransactionEntity> transactions = this.transactionDao.findAllByTransactionId(transactionId);
		if (!transactions.isEmpty()) {
			return entityConverter.toBoundary(transactions.get(0));
		} else {
			throw new NotFoundException("Could not find transaction: " + transactionId);
		}
	}

	@Override
	public List<TransactionBoundary> getTransactionsByUserId(String userId) {
		utils.assertNull(userId);

		List<UserEntity> users = this.userDao.findAllById(userId);
		if (!users.isEmpty()) {
			UserEntity entity = users.get(0);
			return this.transactionDao.findAllByUserId(entity.getId()).stream().map(this.entityConverter::toBoundary)
					.collect(Collectors.toList());
		} else {
			throw new NotFoundException("Could not find user: " + userId);
		}
	}

	@Override
	public void deleteTransaction(String transactionId) {
		utils.assertNull(transactionId);

		// Get the transaction
		List<TransactionEntity> transactions = this.transactionDao.findAllByTransactionId(transactionId);
		if (!transactions.isEmpty()) {
			this.transactionDao.delete(transactions.get(0));
		} else {
			throw new NotFoundException("Could not find transaction: " + transactionId);
		}

	}

	@Override
	public List<TransactionBoundary> getAllUserTransactionsByCategory(String userId, String categoryId) {
		utils.assertNull(userId);
		utils.assertNull(categoryId);

		// TODO: Check if the user exists
		// TODO: Check if the category exists

		// Get the transactions by category - food/clothes
		List<TransactionEntity> transactions = this.transactionDao.findAllByUserIdAndCategoryId(userId, categoryId);
		if (!transactions.isEmpty()) {
			return transactions.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException("Could not find transactions for user: " + userId);
		}
	}

	@Override
	public List<TransactionBoundary> getTransactionsByUserIdAndCategoryAndDateAfter(String userId,
			List<String> categoryId, String date) {

		utils.assertNull(userId);
		utils.assertNull(categoryId);
		utils.assertNull(date);

		List<TransactionEntity> transactions = this.transactionDao
				.findAllByUserIdAndCategoryIdInAndDateAfterOrderByDate(userId, categoryId, date);
		if (!transactions.isEmpty()) {
			return transactions.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException(
					"Could not find transactions for user : " + userId + " and categories" + categoryId.toString());
		}
	}

	@Override
	public List<TransactionBoundary> getTransactionsByUserIdAndDateAfter(String userId, String date) {
		utils.assertNull(userId);
		utils.assertNull(date);

		List<TransactionEntity> transactions = this.transactionDao.findAllByUserIdAndDateAfterOrderByDate(userId, date);

		if (!transactions.isEmpty()) {
			return transactions.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException(
					"Could not find transactions for user : " + userId + " and date" + date.toString());
		}
	}

	@Override
	public List<TransactionBoundary> getAllTransactionsFromBankApi(BankAccountBoundary bankAccountBoundary) {
		utils.assertNull(bankAccountBoundary);
		utils.assertNull(bankAccountBoundary.getAccountId());
		utils.assertNull(bankAccountBoundary.getBankId());
		utils.assertNull(bankAccountBoundary.getBankBranch());
		utils.assertNull(bankAccountBoundary.getBankAccountNumber());

		String bankId = bankAccountBoundary.getBankId();
		String bankBranch = bankAccountBoundary.getBankBranch();
		String bankAccountNum = bankAccountBoundary.getBankAccountNumber();

	
		// get bank account from DB
		List<TransactionEntity> transactions = this.transactionDao
				.findAllByUserIdOrderByDate(bankAccountBoundary.getUserId());

		List<BankTransactionsDetailsEntity> transactionsDetails;

		if (!transactions.isEmpty()) {
			
			// get last transaction from DB
			TransactionEntity lastTransaction = transactions.get(transactions.size() - 1);
			String date = lastTransaction.getDate();
			
			// get all transaction after last updated day
			transactionsDetails = this.bankTransactionsDetailsDao
					.findAllByBankIdAndBankBranchAndBankAccountNumberAndDateAfter(bankId, bankBranch, bankAccountNum,
							date);

		} else {
			/// get all transaction from bank
			
			transactionsDetails = this.bankTransactionsDetailsDao
					.findAllByBankIdAndBankBranchAndBankAccountNumber(bankId, bankBranch, bankAccountNum);
			

		}
		if (transactionsDetails != null && !transactionsDetails.isEmpty()) {
			List<TransactionEntity> transactionsList = new ArrayList<>();
			// add transactions to DB
			for (BankTransactionsDetailsEntity bankTransactionsDetailsEntity : transactionsDetails) {
				TransactionEntity entity = new TransactionEntity();
				entity.setBankAccountId(bankAccountBoundary.getAccountId());
				entity.setAmount(bankTransactionsDetailsEntity.getAmount());
				entity.setBankAccountId(bankAccountBoundary.getAccountId());
				entity.setCategoryId(bankTransactionsDetailsEntity.getCategoryId());
				entity.setDate(bankTransactionsDetailsEntity.getDate());
				entity.setUserId(bankAccountBoundary.getUserId());
				entity = this.transactionDao.save(entity);
				transactionsList.add(entity);
			}
			return transactionsList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		}
		return null;
	}

}

package finance.logic.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.TransactionBoundary;
import finance.data.BankTransactionsDetailsEntity;
import finance.data.CategoryEntity;
import finance.data.TransactionEntity;
import finance.data.UserEntity;
import finance.data.dao.BankTransactionsDetailsDao;
import finance.data.dao.CategoryDao;
import finance.data.dao.TransactionDao;
import finance.data.dao.UserDao;
import finance.logic.TransactionService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.Utils;

@Service
public class TransactionJpa implements TransactionService {

	private BankTransactionsDetailsDao bankTransactionsDetailsDao;
	private TransactionDao transactionDao;
	private UserDao userDao;
	private CategoryDao categoryDao;
	private BudgetJpa budgetJpa;
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
	public void setBudgetJpa(BudgetJpa budgetJpa) {
		this.budgetJpa = budgetJpa;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
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

	/**
	 * Get all the users transactions by Category after specified date
	 */
	@Override
	public List<TransactionBoundary> getTransactionsByUserIdAndCategoryAndDateAfter(String userId,
			List<String> categoryId, String date) {
		utils.assertNull(userId);
		utils.assertNull(categoryId);
		utils.assertNull(date);

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		// Check if categories exist
		for (String category : categoryId) {
			List<CategoryEntity> categories = this.categoryDao.findAllByCategoryId(category);
			if (categories.isEmpty()) {
				throw new ConflictException("Category doesn't exist " + category);
			}
		}

		List<TransactionEntity> transactions = this.transactionDao
				.findAllByUserIdAndCategoryIdInAndDateAfterOrderByDate(userId, categoryId, date);
		if (!transactions.isEmpty()) {
			return transactions.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException(
					"Could not find transactions for user : " + userId + " and categories" + categoryId.toString());
		}
	}
	

	/**
	 * Get all the users transactions by Category between two specified dates
	 */
	@Override
	public List<TransactionBoundary> getTransactionsByUserIdAndDateBetween(String userId, String startDate,
			String endDate) {
		utils.assertNull(userId);
		utils.assertNull(startDate);
		utils.assertNull(endDate);

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		List<TransactionEntity> transactions = this.transactionDao.findAllByUserIdAndAndDateBetween(userId, startDate, endDate);

		if (!transactions.isEmpty()) {
			return transactions.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException(
					"Could not find transactions for user : " + userId + " and startDate: " + startDate +" end date: "+endDate);
		}
	}

	/**
	 * Get all the users transactions after specified date
	 */
	@Override
	public List<TransactionBoundary> getTransactionsByUserIdAndDateAfter(String userId, String date) {
		utils.assertNull(userId);
		utils.assertNull(date);

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		List<TransactionEntity> transactions = this.transactionDao.findAllByUserIdAndDateAfterOrderByDate(userId, date);

		if (!transactions.isEmpty()) {
			return transactions.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException(
					"Could not find transactions for user : " + userId + " and date" + date.toString());
		}
	}

	/**
	 * Get all the users transactions from external Bank Database 
	 */
	@Override
	public List<TransactionBoundary> getAllTransactionsFromBankApi(List<BankAccountBoundary> bankAccountBoundarys) {

		List<TransactionEntity> transactionsList = new ArrayList<>();

		for (BankAccountBoundary bankAccountBoundary : bankAccountBoundarys) {
			utils.assertNull(bankAccountBoundary);
			utils.assertNull(bankAccountBoundary.getAccountId());
			utils.assertNull(bankAccountBoundary.getBankId());
			utils.assertNull(bankAccountBoundary.getBankBranch());
			utils.assertNull(bankAccountBoundary.getBankAccountNumber());
			String bankId = bankAccountBoundary.getBankId();
			String bankBranch = bankAccountBoundary.getBankBranch();
			String bankAccountNum = bankAccountBoundary.getBankAccountNumber();

			// get transactions of bank account from DB
			List<TransactionEntity> transactions = this.transactionDao.findAllByUserIdAndBankAccountIdOrderByDate(
					bankAccountBoundary.getUserId(), bankAccountBoundary.getAccountId());

			List<BankTransactionsDetailsEntity> transactionsDetails;

			if (!transactions.isEmpty()) {
				// get last transaction from DB
				TransactionEntity lastTransaction = transactions.get(transactions.size() - 1);
				String date = lastTransaction.getDate();

				// get all transaction after last updated day
				transactionsDetails = this.bankTransactionsDetailsDao
						.findAllByBankIdAndBankBranchAndBankAccountNumberAndDateAfter(bankId, bankBranch,
								bankAccountNum, date);
			} else {
				/// get all transaction from bank
				transactionsDetails = this.bankTransactionsDetailsDao
						.findAllByBankIdAndBankBranchAndBankAccountNumber(bankId, bankBranch, bankAccountNum);
			}

			if (transactionsDetails != null && !transactionsDetails.isEmpty()) {
				// add transactions to DB
				for (BankTransactionsDetailsEntity bankTransactionsDetailsEntity : transactionsDetails) {
					TransactionEntity entity = new TransactionEntity();
					entity.setBankAccountId(bankAccountBoundary.getAccountId());
					entity.setAmount(bankTransactionsDetailsEntity.getAmount());
					entity.setBankAccountId(bankAccountBoundary.getAccountId());
					entity.setCategoryId(bankTransactionsDetailsEntity.getCategoryId());
					entity.setDate(bankTransactionsDetailsEntity.getDate());
					entity.setUserId(bankAccountBoundary.getUserId());

					// updateBudget
					budgetJpa.updateBudgetCurrentValue(entity.getUserId(), entity.getCategoryId(), entity.getAmount(),
							bankTransactionsDetailsEntity.getDate());
					entity = this.transactionDao.save(entity);
					transactionsList.add(entity);
				}
			}
		}
		return transactionsList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

	/**
	 * Calculate prediction of specified categories 
	 */
	@Override
	public String[] getYearPrediction(String userId, List<String> categoryList) {

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		Float[] prediction = new Float[12];
		Arrays.fill(prediction, 0.0f);

		for (String categoryId : categoryList) {
			// Check if the category exists
			List<CategoryEntity> category = this.categoryDao.findAllByCategoryId(categoryId);
			if (category.isEmpty()) {
				throw new ConflictException("Category doesn't exist " + category);
			}

			//Get all transactions by category and calculate prediction
			List<TransactionEntity> transactions = transactionDao.findAllByUserIdAndCategoryId(userId, categoryId);
			if (!transactions.isEmpty()) {
				prediction = calculateAverage(transactions, prediction);
			}
		}
		return Arrays.stream(prediction).map(String::valueOf).toArray(String[]::new);
	}

	/**
	 * Go through all transactions and calculate average by month 
	 * @param transactionsList
	 * @param prediction
	 * @return Prediction for one category
	 */
	public Float[] calculateAverage(List<TransactionEntity> transactionsList, Float[] prediction) {
		Map<String, Float[]> yearSummery = convertByYears(transactionsList);

		Float[] monthValues = new Float[12];
		Float[] countedYears = new Float[12];
		Arrays.fill(monthValues, 0.0f);
		Arrays.fill(countedYears, 0.0f);

		for (Map.Entry<String, Float[]> year : yearSummery.entrySet()) {
			int i = 0;
			for (Float month : year.getValue()) {
				monthValues[i] += month;
				if (month > 0) {
					countedYears[i]++;
				}
				i++;
			}
		}

		for (int i = 0; i < monthValues.length; i++) {
			if (countedYears[i] != 0) {
				prediction[i] += monthValues[i] / countedYears[i];
			}
		}
		return prediction;
	}

	/**
	 * Convert transactions to map where the key is the year and the value is transactions amount by months
	 * @param transactionsList
	 * @return 
	 */
	public Map<String, Float[]> convertByYears(List<TransactionEntity> transactionsList) {
		Map<String, Float[]> yearSummery = new HashMap<>();

		if (transactionsList != null) {
			for (TransactionEntity transaction : transactionsList) {
				String month = utils.getMonthFromDate(transaction.getDate());
				String year = utils.getYearFromDate(transaction.getDate());

				Float[] amountsByMonths;

				if (yearSummery.containsKey(year)) {
					amountsByMonths = yearSummery.get(year);
					if (amountsByMonths == null)
						return null;
					amountsByMonths[Integer.parseInt(month)] += Float.parseFloat(transaction.getAmount());
				} else {
					amountsByMonths = new Float[12];
					Arrays.fill(amountsByMonths, 0.0f);
					amountsByMonths[Integer.parseInt(month)] = Float.parseFloat(transaction.getAmount());
				}
				yearSummery.put(year, amountsByMonths);
			}
			return yearSummery;
		}
		return null;
	}
}

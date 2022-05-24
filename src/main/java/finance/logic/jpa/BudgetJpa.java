package finance.logic.jpa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.boundaries.BudgetBoundary;
import finance.data.BudgetEntity;
import finance.data.CategoryEntity;
import finance.data.TransactionEntity;
import finance.data.UserEntity;
import finance.data.dao.BudgetDao;
import finance.data.dao.CategoryDao;
import finance.data.dao.TransactionDao;
import finance.data.dao.UserDao;
import finance.logic.BudgetService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.ForbiddenException;
import finance.utils.NotFoundException;
import finance.utils.UnsupportedExecption;
import finance.utils.Utils;

@Service
public class BudgetJpa implements BudgetService {

	private UserDao userDao;
	private BudgetDao budgetDao;
	private CategoryDao categoryDao;
	private TransactionDao transactionDao;
	private TransactionJpa transactionJpa;
	private Utils utils;
	private EntityConverter<BudgetEntity, BudgetBoundary> entityConverter;

	public BudgetJpa() {
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setTransactionJpa(TransactionJpa transactionJpa) {
		this.transactionJpa = transactionJpa;
	}

	@Autowired
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setBudgetDao(BudgetDao budgetDao) {
		this.budgetDao = budgetDao;
	}

	@Autowired
	public void setEntityConverter(EntityConverter<BudgetEntity, BudgetBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	public BudgetBoundary createBudget(BudgetBoundary budgetBoundary) {
		utils.assertNull(budgetBoundary);
		utils.assertNull(budgetBoundary.getCategoryId());
		utils.assertNull(budgetBoundary.getFinishDate());
		utils.assertNull(budgetBoundary.getMaxAmount());
		utils.assertNull(budgetBoundary.getStartDate());
		utils.assertNull(budgetBoundary.getUserId());

		String userId = budgetBoundary.getUserId();
		float maxAmount = Float.parseFloat(budgetBoundary.getMaxAmount());
		String categoryId = budgetBoundary.getCategoryId();
		String startDate = budgetBoundary.getStartDate();
		String finishDate = budgetBoundary.getFinishDate();

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		// Check if category exists
		List<CategoryEntity> category = this.categoryDao.findAllByCategoryId(categoryId);
		if (category.isEmpty()) {
			throw new NotFoundException("Could not find category: " + categoryId);
		}

		// Check if budget with this category already exists
		List<BudgetEntity> budget = this.budgetDao.findAllByUserIdAndCategoryId(userId, categoryId);
		if (!budget.isEmpty()) {
			throw new ConflictException("Budget with this category is already exists: " + budget.get(0).getBudgetId());
		}

		// get all transactions between start and finish date by category
		List<TransactionEntity> transactions = this.transactionDao.findAllByUserIdAndCategoryIdAndDateBetween(userId,
				categoryId, startDate, finishDate);

		// sum all transactions by amount
		float sumTransactions = 0f;
		if (!transactions.isEmpty()) {
			sumTransactions = sumTransactions(transactions);
		}

		// check if transactions sum is less than maximum budget amount entered by user
		if (sumTransactions > maxAmount) {
			throw new ForbiddenException(
					"Transactions sum: " + sumTransactions + " is greater than Budget max amount: " + maxAmount);
		} else {
			// add transaction sum to current amount of budget
			String sum = String.valueOf(sumTransactions);
			budgetBoundary.setCurrentAmount(sum);
		}

		// get all budgets
		List<BudgetEntity> allBudgets = this.budgetDao.findAllByUserIdAndStartDateBetween(userId, startDate,
				finishDate);

		// Calculate total budget max amount including current budget
		float totalBudgetsAmount = Float.parseFloat(budgetBoundary.getMaxAmount());
		if (!allBudgets.isEmpty()) {
			totalBudgetsAmount += sumAllBudgetsAmount(allBudgets);
		}

		// Get salary prediction to check if the amount for current budget is accepted
		float totalSalaryAmount = getSalaryPrediction(budgetBoundary);
		if (totalBudgetsAmount > totalSalaryAmount) {
			throw new UnsupportedExecption("Total budgets max amount: " + totalBudgetsAmount
					+ " is greater than total salary amount prediction: " + totalSalaryAmount);
		}

		BudgetEntity entity = this.entityConverter.fromBoundary(budgetBoundary);
		entity = this.budgetDao.save(entity);

		return this.entityConverter.toBoundary(entity);
	}

	
	private float sumAllBudgetsAmount(List<BudgetEntity> allBudgets) {
		float sum = 0f;
		for (BudgetEntity budget : allBudgets) {
			sum += Float.parseFloat(budget.getMaxAmount());
		}
		return sum;
	}

	/**
	 * Get salary prediction for specific months
	 * @param budgetBoundary
	 * @return 
	 */
	private float getSalaryPrediction(BudgetBoundary budgetBoundary) {
		List<String> list = new ArrayList<>();
		list.add(Utils.salary);

		int startMonth = getMonthFromDate(budgetBoundary.getStartDate());
		int endMonth = getMonthFromDate(budgetBoundary.getFinishDate());

		String[] prediction = transactionJpa.getYearPrediction(budgetBoundary.getUserId(), list);
		float totalSalaryAmount = 0f;
		for (int i = startMonth; i <= endMonth; i++) {
			totalSalaryAmount += Float.parseFloat(prediction[i]);
		}
		return totalSalaryAmount;
	}
	

	private float sumTransactions(List<TransactionEntity> transactions) {

		float sum = 0f;
		for (TransactionEntity transactionEntity : transactions) {
			sum += Float.parseFloat(transactionEntity.getAmount());
		}
		return sum;
	}
	

	public int getMonthFromDate(String date) {
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			return calendar.get(Calendar.MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	@Override
	public List<BudgetBoundary> getAllBudgets(String userId) {
		utils.assertNull(userId);

		List<UserEntity> users = this.userDao.findAllById(userId);
		if (!users.isEmpty()) {
			UserEntity entity = users.get(0);
			return this.budgetDao.findAllByUserId(entity.getId()).stream().map(this.entityConverter::toBoundary)
					.collect(Collectors.toList());
		} else {
			throw new NotFoundException("Could not find user: " + userId);
		}
	}
	

	@Override
	public void deleteBudget(String userId, String budgetId) {
		utils.assertNull(userId);
		utils.assertNull(budgetId);

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		// get budget by ID
		List<BudgetEntity> budgets = this.budgetDao.findAllByBudgetId(budgetId);
		if (!budgets.isEmpty()) {
			// check if the budget belongs to the user
			BudgetEntity budgetEntity = budgets.get(0);
			if (budgetEntity.getUserId().equals(userId)) {
				this.budgetDao.delete(budgetEntity);
			} else {
				throw new NotFoundException("This budget " + budgetId + " doesn't belong to the user: " + userId);
			}

		} else {
			throw new NotFoundException("There is no budget with this id: " + budgetId);
		}
	}
	

	/**
	 * For every transaction check if the budget with this category needs to be updated
	 */
	@Override
	public void updateBudgetCurrentValue(String userId, String categoryId, String currentAmount, String date) {
		utils.assertNull(userId);
		utils.assertNull(categoryId);
		utils.assertNull(currentAmount);
		utils.assertNull(date);

		// Check if user exists
		List<UserEntity> users = this.userDao.findAllById(userId);
		if (users.isEmpty()) {
			throw new NotFoundException("Could not find user: " + userId);
		}

		// Check if budget exists
		List<BudgetEntity> budget = this.budgetDao
				.findAllByUserIdAndCategoryIdAndStartDateLessThanEqualAndFinishDateGreaterThanEqual(userId, categoryId,
						date, date);
		if (!budget.isEmpty()) {
			float oldAmount = Float.parseFloat(budget.get(0).getCurrentAmount());
			float newAmount = oldAmount + Float.parseFloat(currentAmount);
			budget.get(0).setCurrentAmount(String.valueOf(newAmount));
			this.budgetDao.save(budget.get(0));
		}

	}

}

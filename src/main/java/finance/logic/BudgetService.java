package finance.logic;

import java.util.List;

import finance.boundaries.BudgetBoundary;

public interface BudgetService {
	
	public BudgetBoundary createBudget(BudgetBoundary budgetBoundary);
	
	public void deleteBudget(String userId , String budgetId);
	
	public void updateBudgetCurrentValue(String userId , String budgetId,String currentAmount,String date);

	public List<BudgetBoundary> getAllBudgets(String userId);

}

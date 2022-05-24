package finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.BudgetBoundary;
import finance.logic.BudgetService;

@RestController
public class BudgetController {
	private BudgetService budgetService;

	@Autowired
	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@RequestMapping(path = "/budget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BudgetBoundary createBudget(@RequestBody BudgetBoundary budgetBoundary) {
		return budgetService.createBudget(budgetBoundary);
	}

	@RequestMapping(path = "/budget/getAll/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BudgetBoundary[] getAllBudgets(@PathVariable("userId") String userId) {
		return budgetService.getAllBudgets(userId).toArray(new BudgetBoundary[0]);
	}

	@RequestMapping(path = "/budget/{userId}/{budgetId}", method = RequestMethod.DELETE)
	public void deleteBudget(@PathVariable("userId") String userId, @PathVariable("budgetId") String budgetId) {
		budgetService.deleteBudget(userId, budgetId);
	}
}

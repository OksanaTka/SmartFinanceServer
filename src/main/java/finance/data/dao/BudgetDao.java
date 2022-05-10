package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import finance.data.BudgetEntity;

public interface BudgetDao extends PagingAndSortingRepository<BudgetEntity,String> {
	
	public List<BudgetEntity> findAllByUserId(
			@Param("userId") String userId);
	
	public List<BudgetEntity> findAllByBudgetId(
			@Param("budgetId") String budgetId);
	
	public List<BudgetEntity> findAllByUserIdAndCategoryId(
			@Param("userId") String userId, @Param("categoryId") String categoryId);
	
	public List<BudgetEntity> findAllByUserIdAndStartDateBetween(
			@Param("userId") String userId,
			 @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String startDate,
			 @Param("endtDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String endtDate);

}

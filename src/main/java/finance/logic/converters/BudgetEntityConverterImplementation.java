package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.BudgetBoundary;
import finance.data.BudgetEntity;

@Component
public class BudgetEntityConverterImplementation implements EntityConverter<BudgetEntity, BudgetBoundary> {

	@Override
	public BudgetBoundary toBoundary(BudgetEntity entity) {
		BudgetBoundary boundary = new BudgetBoundary();
		boundary.setBudgetId(entity.getBudgetId());
		boundary.setCategoryId(entity.getCategoryId());
		boundary.setCurrentAmount(entity.getCurrentAmount());
		boundary.setFinishDate(entity.getFinishDate());
		boundary.setMaxAmount(entity.getMaxAmount());
		boundary.setStartDate(entity.getStartDate());
		boundary.setUserId(entity.getUserId());

		return boundary;
	}

	@Override
	public BudgetEntity fromBoundary(BudgetBoundary boundary) {
		BudgetEntity entity = new BudgetEntity();
		entity.setCategoryId(boundary.getCategoryId());
		entity.setCurrentAmount(boundary.getCurrentAmount());
		entity.setFinishDate(boundary.getFinishDate());
		entity.setMaxAmount(boundary.getMaxAmount());
		entity.setStartDate(boundary.getStartDate());
		entity.setUserId(boundary.getUserId());

		return entity;
	}

}

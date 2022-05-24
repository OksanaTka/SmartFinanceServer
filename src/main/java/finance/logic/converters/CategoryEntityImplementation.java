package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.CategoryBoundary;
import finance.data.CategoryEntity;

@Component
public class CategoryEntityImplementation implements EntityConverter<CategoryEntity, CategoryBoundary> {

	@Override
	public CategoryBoundary toBoundary(CategoryEntity entity) {
		CategoryBoundary boundary = new CategoryBoundary();
		boundary.setCategoryId(entity.getCategoryId());
		boundary.setCategoryName(entity.getCategoryName());
		boundary.setCategoryIcon(entity.getCategoryIcon());
		return boundary;
	}

	@Override
	public CategoryEntity fromBoundary(CategoryBoundary boundary) {
		CategoryEntity entity = new CategoryEntity();
		entity.setCategoryName(boundary.getCategoryName());
		entity.setCategoryId(boundary.getCategoryId());
		entity.setCategoryIcon(boundary.getCategoryIcon());
		return entity;
	}

}

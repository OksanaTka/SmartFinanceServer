package finance.logic;

import java.util.List;

import finance.boundaries.CategoryBoundary;

public interface CategoryService {

	public List<CategoryBoundary> createCategory(List<CategoryBoundary> category);

	public List<CategoryBoundary> getAllCategories(int page, int size);

}

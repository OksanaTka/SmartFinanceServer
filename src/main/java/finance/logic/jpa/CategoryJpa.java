package finance.logic.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.Utils;
import finance.boundaries.CategoryBoundary;
import finance.data.CategoryEntity;
import finance.data.TransactionEntity;
import finance.data.dao.CategoryDao;
import finance.logic.CategoryService;
import finance.logic.converters.EntityConverter;

@Service
public class CategoryJpa implements CategoryService {

	private CategoryDao categoryDao;
	private Utils utils;
	private EntityConverter<CategoryEntity, CategoryBoundary> entityConverter;

	public CategoryJpa() {
		super();
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
	public void setEntityConverter(EntityConverter<CategoryEntity, CategoryBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	@Transactional
	public CategoryBoundary createCategory(CategoryBoundary category) {
		utils.assertNull(category);
		utils.assertNull(category.getCategoryId());
		utils.assertNull(category.getCategoryName());
		utils.assertNull(category.getCategoryNameHeb());
		utils.assertNull(category.getCategoryIcon());

		List<CategoryEntity> categories = this.categoryDao.findAllByCategoryId(category.getCategoryId());
		if (!categories.isEmpty()) {
			throw new ConflictException("Category already exists " + category.getCategoryId());
		}

		CategoryEntity entity = this.entityConverter.fromBoundary(category);
		entity.setCategoryId(category.getCategoryId());
		entity.setCategoryName(category.getCategoryName());
		entity.setCategoryNameHeb(category.getCategoryNameHeb());
		entity.setCategoryIcon(category.getCategoryIcon());

		entity = this.categoryDao.save(entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryBoundary> getAllCategories(int page, int size) {

		List<CategoryEntity> categories = this.categoryDao.findAllByOrderByCategoryIdAsc();

		if (!categories.isEmpty()) {
			return categories.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NotFoundException("Could not find categories ");
		}
	}

}

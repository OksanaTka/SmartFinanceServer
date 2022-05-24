package finance.logic.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.utils.ConflictException;
import finance.utils.NotFoundException;
import finance.utils.Utils;
import finance.boundaries.CategoryBoundary;
import finance.data.CategoryEntity;
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
	public List<CategoryBoundary> createCategory(List<CategoryBoundary> category) {
		List<CategoryEntity> entityList = new ArrayList<>();
		for (CategoryBoundary categoryBoundary : category) {
			utils.assertNull(categoryBoundary);
			utils.assertNull(categoryBoundary.getCategoryId());
			utils.assertNull(categoryBoundary.getCategoryName());
			utils.assertNull(categoryBoundary.getCategoryNameHeb());
			utils.assertNull(categoryBoundary.getCategoryIcon());

			List<CategoryEntity> categories = this.categoryDao.findAllByCategoryId(categoryBoundary.getCategoryId());
			if (!categories.isEmpty()) {
				throw new ConflictException("Category already exists " + categoryBoundary.getCategoryId());
			}

			CategoryEntity entity = this.entityConverter.fromBoundary(categoryBoundary);
			entity.setCategoryId(categoryBoundary.getCategoryId());
			entity.setCategoryName(categoryBoundary.getCategoryName());
			entity.setCategoryNameHeb(categoryBoundary.getCategoryNameHeb());
			entity.setCategoryIcon(categoryBoundary.getCategoryIcon());
			entity = this.categoryDao.save(entity);
			entityList.add(entity);
		}
		return entityList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
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

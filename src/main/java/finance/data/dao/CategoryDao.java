package finance.data.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.CategoryEntity;

public interface CategoryDao extends PagingAndSortingRepository<CategoryEntity, String>{
	
	public List<CategoryEntity> findAllByCategoryId(@Param("categoryId") String fundId, Pageable pageable);

	public List<CategoryEntity> findAllByCategoryId(@Param("categoryId") String fundId);
	
	
	public List<CategoryEntity> findAllByOrderByCategoryIdAsc();

}
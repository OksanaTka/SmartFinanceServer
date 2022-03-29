package finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.CategoryBoundary;
import finance.logic.CategoryService;

@RestController
public class CategoryController {
	
	private CategoryService categoryService;
	private final String pageSize = "10";
	
	@Autowired
	public void setCategoryService (CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@RequestMapping(path = "/category",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary createCategory(@RequestBody CategoryBoundary category) {
		return categoryService.createCategory(category);
	}
	
	
	@RequestMapping(path = "/category/getAll", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary[] getAllCategories(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = pageSize) int size) {
		return categoryService.getAllCategories(page, size).toArray(new CategoryBoundary[0]);
	}

}

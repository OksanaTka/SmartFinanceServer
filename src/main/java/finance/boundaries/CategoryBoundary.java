package finance.boundaries;

public class CategoryBoundary {
	private String categoryId;
	private String categoryName;
	private String categoryNameHeb;
	private String categoryIcon;
	
	public CategoryBoundary() {
		super();
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryNameHeb() {
		return categoryNameHeb;
	}
	public void setCategoryNameHeb(String categoryNameHeb) {
		this.categoryNameHeb = categoryNameHeb;
	}
	public String getCategoryIcon() {
		return categoryIcon;
	}
	public void setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;
	}
	
}

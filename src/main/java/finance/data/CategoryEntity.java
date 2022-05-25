package finance.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class CategoryEntity {
	
	@Id
	private String categoryId;
	
	private String categoryName;
	private String categoryIcon;
	
	public CategoryEntity() {
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

	public String getCategoryIcon() {
		return categoryIcon;
	}

	public void setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;
	}
	
}

package jaykye.finalproject.dao;

import jaykye.finalproject.model.Category;

import java.util.List;

public interface CategoryDao {
    Category getCategoryById(String id);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategoryById(String id);
}

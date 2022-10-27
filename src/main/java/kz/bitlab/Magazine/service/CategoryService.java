package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();

    void addCategory(Category category);

    Category saveCategory(Category category);

    Category getCategory(Long id);

    void deleteCategory(Long id);

}

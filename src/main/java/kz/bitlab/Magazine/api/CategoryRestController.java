package kz.bitlab.Magazine.api;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.service.CategoryService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    List<Category> getCategories (){
        return categoryService.getCategories();
    }
    @PostMapping
    void createCategory (@RequestBody Category category) {
        categoryService.addCategory(category);
    }
    @GetMapping(value = "/{id}")
    Category getCategory(@PathVariable(name = "id")Long id){
        return categoryService.getCategory(id);
    }
    @PutMapping
    void updateCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
    }
    @DeleteMapping(value = "/{id}")
     void deleteCategory(@PathVariable(name = "id")Long id){
        categoryService.deleteCategory(id);
    }

}

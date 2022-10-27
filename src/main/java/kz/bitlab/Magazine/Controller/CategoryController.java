package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.service.CategoryService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String getCategories(Model model) {
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories",categoryList);
        model.addAttribute("currentUser",userService.getUserData());
        return "category_change";
    }
    @PostMapping(value = "/create")
    public String createCategory(@RequestParam(name = "category_name")String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.addCategory(category);
        return "redirect:/category";
    }
    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String openEditCategory (Model model, @PathVariable(name = "id")Long id) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category",category);
        model.addAttribute("currentUser",userService.getUserData());
        return "editCategory";
    }
    @PostMapping(value = "/edit")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String editCategory (@RequestParam(name = "category_name")String name,
                                @RequestParam(name = "category_id")Long id) {
        Category category = categoryService.getCategory(id);
        category.setName(name);
        categoryService.saveCategory(category);
        return "redirect:/category";
    }
    @PostMapping (value = "/delete")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String deleteCategory (@RequestParam(name = "category_id")Long id){
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}

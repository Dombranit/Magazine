package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.service.CategoryService;
import kz.bitlab.Magazine.service.KorzinaService;
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
    @Autowired
    private KorzinaService korzinaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String getCategories(Model model) {
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories",categoryList);
        model.addAttribute("currentUser",userService.getUserData());
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        return "Blackwood/admin_category";
    }
    @PostMapping(value = "/create")
    public String createCategory(@RequestParam(name = "category_name")String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.addCategory(category);
        return "redirect:/category";
    }
    @PostMapping (value = "/delete")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String deleteCategory (@RequestParam(name = "category_id")Long id){
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}

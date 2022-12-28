package kz.bitlab.Magazine;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.Entity.Comments;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class Blackwood {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private KorzinaService korzinaService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/cart")
    public String apenCart(Model model){
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        model.addAttribute("currentUser", userService.getUserData());
        return "Blackwood/cart";
    }
    @GetMapping(value = "/layout")
    public String openLayout(Model model){
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        model.addAttribute("currentUser", userService.getUserData());
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        return "Blackwood/layout";
    }
    @GetMapping(value = "/shop")
    public String openshop(Model model){
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        model.addAttribute("currentUser", userService.getUserData());
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        List<Product> products = productService.getProductsToAdmin();
        model.addAttribute("productList", products);
        return "sorted";
    }
    @GetMapping(value = "/{id}")
    public String getProductbyIdD(Model model, @PathVariable(name = "id") Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("currentUser", userService.getUserData());
        List<Comments> commentsList = commentService.getComments();
        model.addAttribute("comments", commentsList);
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        return "Blackwood/product-details";
    }
    @GetMapping(value = "/prof")
    @PreAuthorize("isAuthenticated()")
    public String profileee(Model model) {
        model.addAttribute("currentUser", userService.getUserData());
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        return "Blackwood/contact";
    }
}

package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.Entity.Comments;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.ProductCountry;
import kz.bitlab.Magazine.mapper.ProductMapper;
import kz.bitlab.Magazine.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/product")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CountryService countryService;


    @GetMapping
    public String getProducts(Model model) {
        List<Product> products = productService.getProductsToAdmin();
        model.addAttribute("productList", products);
        model.addAttribute("currentUser", userService.getUserData());
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        return "homepage";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String getProductList(Model model) {
        List<Product> productList = productService.getProductsToAdmin();
        model.addAttribute("products", productList);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser", userService.getUserData());
        List<ProductCountry> countryList = countryService.getCountries();
        model.addAttribute("countries",countryList);
        return "product_change";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    public String createProduct(@RequestParam(name = "product_title") String title,
                                @RequestParam(name = "product_price") BigDecimal price,
                                @RequestParam(name = "product_cat_id") Long catId,
                                @RequestParam(name = "product_country")Long id) {
        Product product = new Product();
        List<Category> categories = product.getCategories();
        ProductCountry productCountry = countryService.getCountry(id);
        if (categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(categoryService.getCategory(catId));
        product.setTitle(title);
        product.setCountry(productCountry);
        product.setPrice(price);
        product.setCategories(categories);
        productService.createProduct(product);
        return "redirect:/product/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String detEditProd(Model model, @PathVariable(name = "id") Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("products", product);
        model.addAttribute("categories", product.getCategories());
        List<Comments> commentsList = commentService.getComments();
        model.addAttribute("comments", commentsList);
        model.addAttribute("currentUser", userService.getUserData());
        List<ProductCountry> countries = countryService.getCountries();
        model.addAttribute("countries",countries);
        return "editProduct";
    }

    @PostMapping(value = "/edit")
    public String saveProduct(@RequestParam(name = "product_id") Long prodId,
                              @RequestParam(name = "product_title") String title,
                              @RequestParam(name = "product_price") BigDecimal price,
                              @RequestParam(name = "product_cat") Long catId,
                              @RequestParam(name = "product_country")Long countryid) {
        Product product = productService.getProductById(prodId);
        ProductCountry country = countryService.getCountry(countryid);
        if (product != null) {
            List<Category> categories = product.getCategories();
            if (categories != null) {
                categories.add(categoryService.getCategory(catId));
                product.setCountry(country);
                product.setCategories(categories);
                product.setTitle(title);
                product.setPrice(price);
                productService.editProduct(product);
                return "redirect:/product/admin?success";
            }
        }
        return "redirect:/product/admin?error";
    }

    @GetMapping(value = "/{id}")
    public String getProductbyId(Model model, @PathVariable(name = "id") Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", productMapper.toDto(product));
        model.addAttribute("currentUser", userService.getUserData());
        List<Comments> commentsList = commentService.getComments();
        model.addAttribute("comments", commentsList);
        return "product";
    }

    @PostMapping(value = "/delete")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String deleteProduct(@RequestParam(name = "product_id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product/admin";
    }
    @GetMapping(value = "/sorted/{id}")
    public String showByCat(Model model,@PathVariable(name = "id") Long id){
        Category category = categoryService.getCategory(id);
        List<Product> productList = productService.sortedProduct(category);
        model.addAttribute("sortedProduct",productList);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("currentUser",userService.getUserData());
        return "sorted";
    }

}

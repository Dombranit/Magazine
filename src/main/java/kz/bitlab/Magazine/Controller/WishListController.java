package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Korzina;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.service.KorzinaService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/wishlist")
public class WishListController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private KorzinaService korzinaService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addWishlist(@PathVariable(name = "id") Long id) {
        productService.addToUserKorzina(id, userService.getUserData().getEmail());
        return "redirect:/product";
    }


    @GetMapping
    public String openWishList (Model model){
        Korzina korzina = userService.getUserData().getKorzina();
        model.addAttribute("wishlist",korzina);
        model.addAttribute("currentUser",userService.getUserData());
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        return "Blackwood/wishlist";
    }

    @GetMapping(value = "/remove/{id}")
    public String removeFromWishList (@PathVariable(name = "id")Long id){
        productService.removeProductFromKorzina(id,userService.getUserData().getEmail());
        return "redirect:/wishlist";
    }


}

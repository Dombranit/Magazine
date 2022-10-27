package kz.bitlab.Magazine.Controller;


import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.dto.KorzinaDetailsDto;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.service.KorzinaService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/korzina")
@Controller
public class KorzinaController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private KorzinaService korzinaService;
    @Autowired
    private HttpSession session;


    @GetMapping(value = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addKorzina(@PathVariable(name = "id") Long id) {
        productService.addToUserKorzina(id, userService.getUserData().getEmail());
        return "redirect:/product";
    }

    @GetMapping(value = "/empty/{id}")
    @PreAuthorize("isAnonymous()")
    public String addEmptyKorzina(@PathVariable(name = "id") Long id) {
        productService.addToKorzina(id);
        return "redirect:/product";
    }

    @GetMapping
    public String openKorzina(Model model) {
        if (userService.getUserData().getEmail() == null) {
            model.addAttribute("korzina", new KorzinaDto());
        } else {
            KorzinaDto korzinaDto = korzinaService.getKorzinaByEmail(userService.getUserData().getEmail());
            model.addAttribute("korzina", korzinaDto);
            model.addAttribute("currentUser", userService.getUserData());
        }
        return "korzina";
    }
    @GetMapping(value = "/remove/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeFromKorzina(@PathVariable(name = "id") Long id){
        productService.removeProductFromKorzina(id,userService.getUserData().getEmail());
        return "redirect:/korzina";
    }

    @PostMapping
    public String commitKorzina(){
        if(userService.getUserData()!=null){
            korzinaService.commitKorzinaToOrder(userService.getUserData().getEmail());
        }
        return "redirect:/korzina";
    }
    @GetMapping(value = "/noUserKorzina/{id}")
    public String addToNoUserKorzina (@PathVariable(name = "id")Long productId) {
        List<Product> orderProductList = (List<Product>) session.getAttribute("orderProductList");
        Product product = productService.getProductById(productId);
        if(orderProductList == null){
            List<Product> newOrderProductList = new ArrayList<>();
            newOrderProductList.add(product);
            session.setAttribute("orderProductList",newOrderProductList);
        }
        else {
            orderProductList.removeIf(orderProduct-> productId.equals(orderProduct.getId()));
            orderProductList.add(product);
            session.setAttribute("orderProductList",orderProductList);
        }
        return "redirect:/product";
    }
}

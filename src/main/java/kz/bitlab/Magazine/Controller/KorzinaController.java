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
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/korzina")
@Controller
public class KorzinaController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private KorzinaService korzinaService;

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
        KorzinaDto korzinaDto = korzinaService.getKorzinaByEmail(userService.getUserData().getEmail());
        model.addAttribute("korzina",korzinaDto);
        model.addAttribute("currentUser",userService.getUserData());
        return "korzina";
    }

    @GetMapping(value = "/remove/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeFromKorzina(@PathVariable(name = "id") Long id){
        productService.removeProductFromKorzina(id,userService.getUserData().getEmail());
        return "redirect:/korzina";
    }


}

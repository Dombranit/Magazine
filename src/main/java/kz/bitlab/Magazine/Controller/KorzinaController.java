package kz.bitlab.Magazine.Controller;

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


    @GetMapping(value = "/add/{id}")
    public String addInKorzina(@PathVariable(name = "id") Long id) {
        productService.addToKorzinabyAnonym(id);
        return "redirect:/korzina";
    }

    @GetMapping
    public String openKorzina(Model model) {
            KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
            model.addAttribute("korzina",korzinaDto);
            model.addAttribute("currentUser", userService.getUserData());
        return "korzina";
    }

    @GetMapping(value = "/remove/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeFromKorzina(@PathVariable(name = "id") Long id) {
        korzinaService.removeProductFromSession(id);
        return "redirect:/korzina";
    }

    @PostMapping(value = "/a")
    @PreAuthorize("isAuthenticated()")
    public String commitAnonymKorzina() {
        korzinaService.commitAnonymKorzinaToOrder(userService.getUserData().getEmail());
    return "redirect:/order/myOrder";
    }

    @GetMapping(value = "/noUserKorzina/{id}")
    public String addToNoUserKorzina(@PathVariable(name = "id") Long productId) {
        productService.addToKorzinabyAnonym(productId);
        return "redirect:/product";

    }
}

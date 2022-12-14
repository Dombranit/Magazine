package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.ProductCountry;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.service.CountryService;
import kz.bitlab.Magazine.service.KorzinaService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private UserService userService;
    @Autowired
    private KorzinaService korzinaService;
    @GetMapping
    public String getCountries(Model model){
        List<ProductCountry> productCountries = countryService.getCountries();
        model.addAttribute("countries",productCountries);
        model.addAttribute("currentUser",userService.getUserData());
        KorzinaDto korzinaDto = korzinaService.getKorzinaByAnonym();
        model.addAttribute("korzina",korzinaDto);
        return "Blackwood/admin_country";
    }
}

package kz.bitlab.Magazine.api;

import kz.bitlab.Magazine.Entity.ProductCountry;
import kz.bitlab.Magazine.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/country")
public class CountryRestController {
    @Autowired
    private CountryService countryService;
    @GetMapping
    public List<ProductCountry> getCountries (){
        return countryService.getCountries();
    }
    @GetMapping(value = "/{id}")
    public ProductCountry getCountry(@PathVariable(name = "id")Long id){
        return countryService.getCountry(id);
    }
    @PostMapping
    void addCountry (@RequestBody ProductCountry country){
        countryService.updateCountry(country);
    }
    @PutMapping
    void updateCountry(@RequestBody ProductCountry country){
        countryService.updateCountry(country);
    }
    @DeleteMapping
    void deleteCountry(Long id){
        countryService.deleteCountry(id);
    }
}

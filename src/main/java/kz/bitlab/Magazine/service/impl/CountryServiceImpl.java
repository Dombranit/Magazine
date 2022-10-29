package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.ProductCountry;
import kz.bitlab.Magazine.repository.ProductCountryRepository;
import kz.bitlab.Magazine.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CountryServiceImpl implements CountryService {
    @Autowired
    private ProductCountryRepository productCountryRepository;

    @Override
    public List<ProductCountry> getCountries() {
        return productCountryRepository.findAll();
    }

    @Override
    public ProductCountry getCountry(Long id) {
        return productCountryRepository.getReferenceById(id);
    }

    @Override
    public void deleteCountry(Long id) {
        productCountryRepository.deleteById(id);
    }

    @Override
    public void updateCountry(ProductCountry productCountry) {
        productCountryRepository.save(productCountry);
    }
}

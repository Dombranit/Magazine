package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.ProductCountry;

import java.util.List;

public interface CountryService {
    List<ProductCountry> getCountries();

    ProductCountry getCountry(Long id);

    void deleteCountry(Long id);

    void updateCountry(ProductCountry productCountry);
}

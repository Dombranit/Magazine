package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.Korzina;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.KorzinaDetailsDto;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.repository.KorzinaRepository;
import kz.bitlab.Magazine.repository.ProductRepository;
import kz.bitlab.Magazine.service.KorzinaService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class KorzinaServiceImpl implements KorzinaService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KorzinaRepository korzinaRepository;
    @Autowired
    private UserService userService;

    @Override
    public Korzina createKorzina(Users users, Long id) {
        Korzina korzina = new Korzina();
        korzina.setUser(users);
        List<Product> products = Collections.singletonList(productRepository.getReferenceById(id));
        korzina.setProducts(products);
        return korzinaRepository.save(korzina);
    }

    @Override
    public Korzina createEmptyKorzina(Long id) {
        Korzina korzina = new Korzina();
        List<Product> products = Collections.singletonList(productRepository.getReferenceById(id));
        korzina.setProducts(products);
        return korzinaRepository.save(korzina);
    }

    @Override
    public void addProducts(Korzina korzina, Long id) {
        List<Product> products = korzina.getProducts();
        List<Product> newProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProducts.add(productRepository.getReferenceById(id));
        korzina.setProducts(newProducts);
        korzinaRepository.save(korzina);
    }

    @Override
    public Korzina removeProduct(Korzina korzina, Long id) {
        List<Product> products = korzina.getProducts();
        List<Product> newProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProducts.remove(productRepository.getReferenceById(id));
        korzina.setProducts(newProducts);
        return korzinaRepository.save(korzina);
    }

    @Override
    public KorzinaDto getKorzinaByEmail(String name) {
        Users users = userService.getUserByEmail(name);
        if (users == null || users.getKorzina() == null) {
            return new KorzinaDto();
        }
        KorzinaDto korzinaDto = new KorzinaDto();
        Map<Long, KorzinaDetailsDto> mapByProductId = new HashMap<>();

        List<Product> products = users.getKorzina().getProducts();
        for (Product product : products) {
            KorzinaDetailsDto detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new KorzinaDetailsDto(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal("1.0")));
                detail.setSum(detail.getSum() + Double.parseDouble(product.getPrice().toString()));
            }
        }
        korzinaDto.setKorzinaDetails(new ArrayList<>(mapByProductId.values()));
        korzinaDto.aggregate();

        return korzinaDto;
    }

    @Override
    public KorzinaDto getKorzinaByAnonym() {
        KorzinaDto korzinaDto = new KorzinaDto();
        Map<Long, KorzinaDetailsDto> mapByProductId = new HashMap<>();
        List<Product> products = new ArrayList<>();
        for (Product product : products) {
            KorzinaDetailsDto detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new KorzinaDetailsDto(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal("1.0")));
                detail.setSum(detail.getSum() + Double.parseDouble(product.getPrice().toString()));
            }
        }
        korzinaDto.setKorzinaDetails(new ArrayList<>(mapByProductId.values()));
        korzinaDto.aggregate();
        return korzinaDto;
    }
}

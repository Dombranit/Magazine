package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.Entity.Korzina;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.KorzinaDetailsDto;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.dto.ProductDto;
import kz.bitlab.Magazine.mapper.ProductMapper;
import kz.bitlab.Magazine.repository.ProductRepository;
import kz.bitlab.Magazine.service.KorzinaService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private KorzinaService korzinaService;
    @Override
    public void removeProductFromKorzina(Long productdId, String email) {
        Users users = userService.getUserByEmail(email);
        if (email != null) {
            Korzina korzina = users.getKorzina();
            if (korzina != null) {
                korzinaService.removeProduct(korzina, productdId);
            }
            users.setKorzina(korzina);
            userService.saveUser(users);
        }
    }

    @Override
    public void addToUserKorzina(Long productId, String email) {
        Users users = userService.getUserByEmail(email);
        if (email != null) {
            Korzina korzina = users.getKorzina();
            if (korzina != null) {
                korzinaService.addProducts(korzina, productId);
            } else {
                Korzina newKorzina = korzinaService.createKorzina(users, productId);
                users.setKorzina(newKorzina);
                userService.saveUser(users);
            }
        }
    }

    @Override
    public void addToKorzina(Long productId) {}

    @Override
    public List<ProductDto> getProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void editProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public List<Product> getProductsToAdmin() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> sortedProduct(Category category) {
        return productRepository.findAllByCategories(category);
    }
}


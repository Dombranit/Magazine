package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.Entity.Korzina;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();
    List<Product> getProductsToAdmin();
    void addToUserKorzina (Long productId,String email);
    void addToKorzina (Long productId);
    void removeProductFromKorzina (Long productdId,String email);
    void createProduct (Product product);
    void deleteProduct (Long id);
    void editProduct (Product product);
    Product getProductById(Long id);
    List<Product> sortedProduct(Category category);

}

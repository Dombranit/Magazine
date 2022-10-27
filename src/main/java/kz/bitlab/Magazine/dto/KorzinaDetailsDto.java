package kz.bitlab.Magazine.dto;

import kz.bitlab.Magazine.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KorzinaDetailsDto {
    private String title;
    private Long productId;
    private BigDecimal price;
    private BigDecimal amount;
    private String image;
    private double sum;

    public KorzinaDetailsDto (Product product){
        this.title=product.getTitle();
        this.productId=product.getId();
        this.price=product.getPrice();
        this.image=product.getProductImage();
        this.amount=new BigDecimal("1.0");
        this.sum=Double.parseDouble(product.getPrice().toString());

    }
}

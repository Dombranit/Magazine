package kz.bitlab.Magazine.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String productImage;
}

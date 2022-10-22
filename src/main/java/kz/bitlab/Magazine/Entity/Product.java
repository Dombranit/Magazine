package kz.bitlab.Magazine.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    private String title;
    private BigDecimal price;
    private String productImage;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;

}

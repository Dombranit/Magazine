package kz.bitlab.Magazine.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Proxy(lazy=false)
public class Product extends BaseEntity {
    private String title;
    private BigDecimal price;
    private String productImage;
    @ManyToMany(fetch = FetchType.LAZY  )
    private List<Category> categories;
    @ManyToOne
    private ProductCountry country;

}

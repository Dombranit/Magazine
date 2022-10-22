package kz.bitlab.Magazine.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails extends BaseEntity {
    @ManyToOne
    private Orders orders;
    private BigDecimal amount;
    private BigDecimal price;
    @ManyToOne
    private Product product;
}

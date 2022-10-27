package kz.bitlab.Magazine.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails extends BaseEntity {
    @ManyToOne
    private Orders orders;
    private BigDecimal amount;
    private BigDecimal price;
    @ManyToOne
    private Product product;

    public OrderDetails (Orders orders, Product product ,Long amount){
        this.orders = orders;
        this.amount=new BigDecimal(amount);
        this.product=product;
        this.price=new BigDecimal(String.valueOf(product.getPrice()));
    }

}

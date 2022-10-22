package kz.bitlab.Magazine.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseEntity{
    @CreationTimestamp
    private Date created;
    @ManyToOne
    private Users user;
    private BigDecimal totalPrice;
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

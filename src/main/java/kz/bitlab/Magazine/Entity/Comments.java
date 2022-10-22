package kz.bitlab.Magazine.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments extends BaseEntity{
    String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;
    @ManyToOne(fetch =FetchType.LAZY)
    Users users;
}

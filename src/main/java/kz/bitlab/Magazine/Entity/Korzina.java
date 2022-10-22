package kz.bitlab.Magazine.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Korzina extends BaseEntity{
    @OneToOne
    private Users user;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;

}

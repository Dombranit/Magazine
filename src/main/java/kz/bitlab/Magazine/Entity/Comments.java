package kz.bitlab.Magazine.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Proxy(lazy=false)
public class Comments extends BaseEntity{
    String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    Product product;
    @ManyToOne(fetch =FetchType.LAZY)
    @ToString.Exclude
    Users users;
}

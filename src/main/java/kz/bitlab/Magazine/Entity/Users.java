package kz.bitlab.Magazine.Entity;

import lombok.*;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {
    private String fullName;
    private String email;
    private String password;
    private String avatar;
    private boolean archive;
    private String address;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Korzina korzina;

}

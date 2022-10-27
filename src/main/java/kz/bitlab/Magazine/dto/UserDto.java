package kz.bitlab.Magazine.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String password;
    private String rePassword;
    private String avatar;
    private String address;

}

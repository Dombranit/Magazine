package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);

    Users getUserData();

    UserDto createUser(UserDto userDto);

    void saveUser(Users users);

    Users getUser(Long id);

    void deleteUser(Long id);

    List<Users> getUsers();

}

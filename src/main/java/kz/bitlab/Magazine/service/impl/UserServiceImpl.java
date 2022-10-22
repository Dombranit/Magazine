package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.Role;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.UserDto;
import kz.bitlab.Magazine.mapper.UserMapper;
import kz.bitlab.Magazine.repository.RoleRepository;
import kz.bitlab.Magazine.repository.UserRepository;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static liquibase.repackaged.net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Users getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User secUser = (User) authentication.getPrincipal();
            return getUserByEmail(secUser.getUsername());
        }
        return null;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Users checkUser = usersRepository.findByEmail(userDto.getEmail());
        if (checkUser == null) {
            Role role = roleRepository.findByRole("ROLE_USER");
            if (role != null) {
                List<Role> roles = new ArrayList<>();
                roles.add(role);
                if (userDto.getPassword().equals(userDto.getRePassword())) {
                    Users users = userMapper.toEntity(userDto);
                    users.setFullName(userDto.getFullName());
                    users.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    users.setEmail(userDto.getEmail());
                    users.setRoles(roles);
                    return userMapper.toDto(usersRepository.save(users));
                }
            }
        }
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Users myUser = usersRepository.findByEmail(username);
         if(myUser!=null){
                return new User(myUser.getEmail(),myUser.getPassword(),myUser.getRoles());
         }
        throw new UsernameNotFoundException ("User not found ");
    }
    @Override
    public void saveUser(Users users) {
        usersRepository.save(users);
    }

    @Override
    public Users getUser(Long id) {
        return usersRepository.getReferenceById(id);
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }
}
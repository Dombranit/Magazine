package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Role;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.UserDto;
import kz.bitlab.Magazine.service.RoleService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("currentUser", userService.getUserData());
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        model.addAttribute("currentUser", userService.getUserData());
        return "profile";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("currentUser", userService.getUserData());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "re_user_password") String repassword,
                           @RequestParam(name = "user_fullName") String fullname,
                           @RequestParam(name = "user_address")String address) {
        UserDto newUser = new UserDto();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFullName(fullname);
        newUser.setRePassword(repassword);
        newUser.setAddress(address);
        if (userService.createUser(newUser) != null) {
            return "/login";
        }

        return "redirect:register?error";
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    public String getUsers(Model model) {
        List<Users> usersList = userService.getUsers();
        model.addAttribute("Users", usersList);
        model.addAttribute("currentUser", userService.getUserData());
        return "user_change";
    }

    @GetMapping(value = "users/{id}")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String editUser(Model model, @PathVariable(name = "id") Long id) {
        Users users = userService.getUser(id);
        model.addAttribute("user", users);
        model.addAttribute("currentUser", userService.getUserData());
        List<Role> roleList = roleService.getAllRoles();
        roleList.removeAll(users.getRoles());
        model.addAttribute("roleList",roleList);
        return "editUser";
    }

    @PostMapping(value = "/editUser")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String editUser(@RequestParam(name = "user_fullName") String fullName,
                           @RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_id") Long id,
                           @RequestParam(name = "user_current_password") String currentPassword,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "re_password") String rePassword,
                           @RequestParam(name = "user_address")String address) {
        Users users = userService.getUser(id);
        if (users.getPassword().equals(passwordEncoder.encode(currentPassword))) {
            if (password.equals(rePassword)) {
                users.setPassword(password);
                users.setEmail(email);
                users.setFullName(fullName);
                users.setAddress(address);
                userService.saveUser(users);
                return "redirect:/profile";
            }
        }
        return "redirect:/profile?error";
    }
        @PostMapping(value = "/users/delete")
        @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
        public String deleteUser (@RequestParam(name = "user_id") Long id){
            userService.deleteUser(id);
            return "redirect:/users";
        }
    }

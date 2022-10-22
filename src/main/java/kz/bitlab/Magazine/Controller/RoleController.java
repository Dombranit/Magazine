package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Role;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.service.RoleService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/assignee")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String assigneeRole(@RequestParam(name = "user_id")Long userId,
                               @RequestParam(name = "role_id")Long roleId){
        Users users =userService.getUser(userId);
        if(users!=null){
            Role role = roleService.getRoleById(roleId);
            if(role!=null){
                List<Role> roleList = users.getRoles();
                if(roleList==null){
                    roleList=new ArrayList<>();
                }
                roleList.add(role);
                users.setRoles(roleList);
                userService.saveUser(users);
                return "redirect:/users/"+userId;
            }
        }
        return "redirect:/users";
    }
    @PostMapping("/unassignee")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String unassigneeRole(@RequestParam(name = "user_id")Long userId,
                                 @RequestParam(name = "role_id")Long roleId){
        Users users =userService.getUser(userId);
        if(users!=null){
            Role role = roleService.getRoleById(roleId);
            if(role!=null){
                List<Role> roleList = users.getRoles();
                if(roleList==null){
                    roleList=new ArrayList<>();
                }
                roleList.remove(role);
                users.setRoles(roleList);
                userService.saveUser(users);
                return "redirect:/users/"+userId;
            }
        }
        return "redirect:/users";
    }
}

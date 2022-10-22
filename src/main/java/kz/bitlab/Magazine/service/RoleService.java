package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}

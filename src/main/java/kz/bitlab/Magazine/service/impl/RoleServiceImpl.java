package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.Role;
import kz.bitlab.Magazine.repository.RoleRepository;
import kz.bitlab.Magazine.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    public RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }
}

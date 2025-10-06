package com.ofss.service;
import com.ofss.model.Roles;
import com.ofss.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Roles> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Roles createRole(Roles role) {
        role.setPassword(passwordEncoder.encode(role.getPassword()));
        return roleRepository.save(role);
    }

    public Roles updateRole(Long id, Roles updatedRole) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setUsername(updatedRole.getUsername());
                    role.setPassword(passwordEncoder.encode(role.getPassword()));
                    role.setRole(updatedRole.getRole());
                    return roleRepository.save(role);
                })
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

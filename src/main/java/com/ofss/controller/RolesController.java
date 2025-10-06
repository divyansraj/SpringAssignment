package com.ofss.controller;
import com.ofss.model.Roles;
import com.ofss.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesService roleService;

    @GetMapping
    public List<Roles> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("id/{id}")
    public Roles getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @PostMapping
    public Roles createRole(@RequestBody Roles role) {
        return roleService.createRole(role);
    }

    @PutMapping("id/{id}")
    public Roles updateRole(@PathVariable Long id, @RequestBody Roles role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("id/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}


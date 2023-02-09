package com.company.ProjectSpring.repo;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
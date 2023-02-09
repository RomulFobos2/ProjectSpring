package com.company.ProjectSpring.repo;

import com.company.ProjectSpring.models.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Department findByName(String name);
}

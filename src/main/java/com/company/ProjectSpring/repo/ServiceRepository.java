package com.company.ProjectSpring.repo;

import com.company.ProjectSpring.models.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Long> {
    List<Service> findByDepartmentId(Long id);

    @Query(value = "SELECT t_service.* FROM `t_service` WHERE online = 0",nativeQuery = true)
    List<Service> findOffline();

    @Query(value = "SELECT t_service.* FROM `t_service` WHERE online = 1",nativeQuery = true)
    List<Service> findOnline();
}

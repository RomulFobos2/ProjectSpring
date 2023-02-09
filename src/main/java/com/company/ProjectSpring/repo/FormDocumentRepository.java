package com.company.ProjectSpring.repo;

import com.company.ProjectSpring.models.FormDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FormDocumentRepository extends CrudRepository<FormDocument, Long> {
    FormDocument findByName(String name);

    @Query(value = "SELECT t_form_document.* FROM t_form_document,t_service\n" +
        "WHERE t_form_document.service_id = t_service.id AND\n" +
        "t_service.department_id = :id_dep",nativeQuery = true)
    List<FormDocument> findAllByDepartmentId(@Param("id_dep") Long id);

    @Query(value = "SELECT * FROM `t_form_document` WHERE service_id = :service_id AND document_id = :document_id AND name = :name",nativeQuery = true)
    FormDocument findUnique(@Param("service_id") Long service_id, @Param("document_id") Long document_id, @Param("name") String name);
}

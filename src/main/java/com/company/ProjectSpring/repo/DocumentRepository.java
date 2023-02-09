package com.company.ProjectSpring.repo;

import com.company.ProjectSpring.models.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document findByName(String name);
}

package com.company.ProjectSpring.repo;

import com.company.ProjectSpring.models.Appeal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppealRepository extends CrudRepository<Appeal, Long> {
    Appeal findByDateAnswer(Date date);
    List<Appeal> findByUserId(Long id);

    @Query(value = "SELECT date_answer FROM `t_appeal` WHERE user_id = :user_id",nativeQuery = true)
    List<Date> findDateByUserId(@Param("user_id") Long id);
}

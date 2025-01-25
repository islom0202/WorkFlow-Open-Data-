package org.example.projectdevtool.repo;

import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long> {
    List<Project> findByStartDate(LocalDate today);

    List<Project> findByStartDateOrEndDate(LocalDate startDate, LocalDate endDate);
    @Query("SELECT p FROM Project p JOIN p.employees e WHERE e = :employee")
    List<Project> findByEmployee(@Param("employee") Profile employee);

    @Query("SELECT p FROM Project p WHERE p.owner.id = :id")
    List<Project> findByOwner(@Param("id") Long id);
}

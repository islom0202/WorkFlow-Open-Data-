package org.example.projectdevtool.repo;

import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Project;
import org.example.projectdevtool.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByProjectAndAssignedTo(Project project, Profile assignedTo);

    List<Task> findByStartDate(LocalDate today);

    List<Task> findByProject(Project project);

}

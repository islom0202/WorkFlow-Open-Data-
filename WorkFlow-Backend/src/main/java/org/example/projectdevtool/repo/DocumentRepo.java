package org.example.projectdevtool.repo;

import org.example.projectdevtool.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<Document,Long> {
}

package org.example.projectdevtool.repo;

import org.example.projectdevtool.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByLogin(String login);

    Users findByEmail(String email);
}


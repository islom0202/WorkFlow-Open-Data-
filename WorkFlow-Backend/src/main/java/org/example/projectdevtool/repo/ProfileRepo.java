package org.example.projectdevtool.repo;

import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Profile,Long> {
    Profile findByUser(Users user);
}

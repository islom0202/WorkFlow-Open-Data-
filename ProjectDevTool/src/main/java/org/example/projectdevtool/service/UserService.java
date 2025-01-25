package org.example.projectdevtool.service;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.ProfileRequestDto;
import org.example.projectdevtool.dto.RegisterRequest;
import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Users;
import org.example.projectdevtool.repo.ProfileRepo;
import org.example.projectdevtool.repo.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepo profileRepo;
    public Users register(RegisterRequest request) {
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Users.Role.EMPLOYEE);
        user.setCreatedAt(LocalDateTime.now());
        return usersRepo.save(user);
    }

    public List<Profile> findAll() {
        return usersRepo.findAll().stream().map(
                profileRepo::findByUser
        ).toList();
    }

    public Profile findByEmail(String email) {
        Users user = usersRepo.findByEmail(email);
        return profileRepo.findByUser(user);
    }

    public Profile fillProfile(ProfileRequestDto dto, String login) {
//        Users user = usersRepo.findById(dto.getUserId())
//                .orElseThrow(()->new NoSuchElementException("not found"));
//
//        Profile profile = new Profile();
//        profile.setUser(user);
//        profile.setFirstname(dto.getFirstname());
//        profile.setLastname(dto.getLastname());
//        profile.setProfession(dto.getProfession());
//        profile.setGoodAt(dto.getGoodAt());
//        return profileRepo.save(profile);

        Users user = usersRepo.findByLogin(login)
                .orElseThrow(()-> new NoSuchElementException("not found"));

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFirstname(dto.getFirstname());
        profile.setLastname(dto.getLastname());
        profile.setProfession(dto.getProfession());
        profile.setGoodAt(dto.getGoodAt());
        return profileRepo.save(profile);
    }

    public Optional<Profile> myProfile(String username) {
        Users user = usersRepo.findByLogin(username)
                .orElseThrow(()->new NoSuchElementException("not found"));

        return Optional.ofNullable(profileRepo.findByUser(user));
    }

    public Profile findById(Long id) {
        return profileRepo.findById(id)
                .orElseThrow(()->new NoSuchElementException("not found"));
    }
}

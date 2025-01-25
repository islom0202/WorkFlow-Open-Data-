package org.example.projectdevtool.security;

import lombok.RequiredArgsConstructor;
import org.example.projectdevtool.entity.Users;
import org.example.projectdevtool.repo.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String login) {
        final Users user = usersRepo.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UsersDetails(user);
    }
}

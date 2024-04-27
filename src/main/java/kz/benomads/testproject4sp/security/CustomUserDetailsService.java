package kz.benomads.testproject4sp.security;

import kz.benomads.testproject4sp.repository.UserRepository;
import kz.benomads.testproject4sp.model.Role;
import kz.benomads.testproject4sp.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("UserEntity username=%s not found", username)));

        return new User(user.getUsername(),
                        user.getPassword(),
                        mapRolesToAuthorities(user.getRoles()));
    }


    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

    }
}

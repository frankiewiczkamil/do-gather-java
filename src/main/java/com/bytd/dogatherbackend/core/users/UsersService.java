package com.bytd.dogatherbackend.core.users;

import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
  private BCryptPasswordEncoder passwordEncoder;
  private UsersRepository usersRepo = new UsersRepository();

  UsersService(BCryptPasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public void signUp(String username, String password, String email) {
    usersRepo.save(new User(UUID.randomUUID(), email, username, passwordEncoder.encode(password)));
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = usersRepo.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    } else {
      return new org.springframework.security.core.userdetails.User(
          user.email(), user.password(), List.of(new SimpleGrantedAuthority("USER")));
    }
  }

  class UsersRepository {
    private Map<String, User> users = new HashMap<>();

    public void save(User user) {
      users.put(user.email(), user);
    }

    public User findByEmail(String email) {
      return users.get(email);
    }
  }
}

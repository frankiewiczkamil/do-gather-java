package com.bytd.dogatherbackend.core.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserServiceConfig {
  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Bean
  public UsersService usersService() {
    return new UsersService(passwordEncoder);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return passwordEncoder;
  }
}

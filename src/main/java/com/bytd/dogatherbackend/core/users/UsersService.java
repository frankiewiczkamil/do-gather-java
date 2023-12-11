package com.bytd.dogatherbackend.core.users;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
  Map<String, User> users = new HashMap<>();

  public void signUp(String username, String password, String email) {
    users.put(email, new User(UUID.randomUUID(), email, username, password));
  }

  public boolean authenticate(String email, String password) {
    var user = users.get(email);
    if (user == null) {
      return false;
    } else {
      return user.password().equals(password);
    }
  }
}

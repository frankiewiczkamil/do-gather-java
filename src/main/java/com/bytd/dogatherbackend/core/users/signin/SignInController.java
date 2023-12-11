package com.bytd.dogatherbackend.core.users.signin;

import com.bytd.dogatherbackend.core.users.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin")
@AllArgsConstructor
public class SignInController {

  UsersService usersService;

  @PostMapping
  public boolean signIn(@RequestBody SignInRequestDto signInDto) {
    return usersService.authenticate(signInDto.email(), signInDto.password());
  }
}

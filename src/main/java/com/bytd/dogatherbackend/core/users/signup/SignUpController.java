package com.bytd.dogatherbackend.core.users.signup;

import com.bytd.dogatherbackend.core.users.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@AllArgsConstructor
public class SignUpController {

  UsersService usersService;

  @PostMapping
  public SignUpRequestDto signUp(@RequestBody SignUpRequestDto signUpDto) {
    usersService.signUp(signUpDto.username(), signUpDto.password(), signUpDto.email());
    return signUpDto;
  }
}

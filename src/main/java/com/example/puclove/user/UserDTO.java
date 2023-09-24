package com.example.puclove.user;

import java.time.LocalDate;
import java.util.List;

public record UserDTO(String name,
                      String email,
                      String password,
                      LocalDate birthDate,
                      String course,
                      String campus,
                      List<String> interestsIds,
                      String instagram,
                      Intention intention) {

}

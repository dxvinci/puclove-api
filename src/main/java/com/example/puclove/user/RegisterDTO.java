package com.example.puclove.user;

import com.example.puclove.interest.Interest;

import java.time.LocalDate;
import java.util.List;

public record RegisterDTO(String name,
                          String email,
                          String password,
                          LocalDate birthDate,
                          String course,
                          String campus,
                          List<Interest> interests,
                          String phoneNumber,
                          String instagram,
                          Intention intention,
                          UserRole role) {
}

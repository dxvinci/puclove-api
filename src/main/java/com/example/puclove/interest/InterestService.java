package com.example.puclove.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public List<Interest> allInterests() {
        return interestRepository.findAll();
    }
}

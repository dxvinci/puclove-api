package com.example.puclove.user;

import com.example.puclove.interest.Interest;
import com.example.puclove.interest.InterestRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterestRepository interestRepository;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> singleUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);

        for (ObjectId interestId : data.interestsIds()) {
            Interest interest = interestRepository.findInterestById(interestId).orElse(null);
            if (interest != null)
                newUser.addInterest(interest);
        }

        saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}

package com.example.puclove.user;

import com.example.puclove.interest.Interest;
import com.example.puclove.interest.InterestRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterestRepository interestRepository;

    /**
     * Busca todos os usuários
     * @return lista de usuários
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário pelo username
     * @param name
     * @return usuário
     */
    public Optional<User> findUserByUsername(String name) { return userRepository.findUserByUsername(name);}

    public Optional<User> findUserById(String id) {
        return userRepository.findUserById(new ObjectId(id));
    }

    /**
     * Get de todos os usuários que tenham pelo menos um interesse em comum com o usuário atual
     * @param user
     * @return
     */
    public Optional<List<User>> matchingInterestsUsers(User user) {
        List<Interest> currentUserInterests = user.getInterests();
        List<User> users = new ArrayList<>();

        for (Interest interest : currentUserInterests) {
            List<User> matchingUsers = userRepository.findUsersByInterest(interest.getId());

            for (User matchingUser : matchingUsers) {
                if (!users.contains(matchingUser) && !matchingUser.equals(user)) {
                    users.add(matchingUser);
                }
            }
        }

        return Optional.of(users);
    }

}

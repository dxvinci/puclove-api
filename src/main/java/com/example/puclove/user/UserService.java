package com.example.puclove.user;

import com.example.puclove.interest.Interest;
import com.example.puclove.interest.InterestRepository;
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

    public Optional<User> singleUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    /**
     * Busca um usuário pelo username
     * @param name
     * @return usuário
     */
    public Optional<User> findUserByUsername(String name) { return userRepository.findUserByUsername(name);}

    /**
     * Cria um usuário no banco de dados
     * @param data
     * @return usuário criado
     */
    public User createUser(UserDTO data) {
        User newUser = new User(data);

        for (Interest interest : data.interestsIds()) {
            Interest interests = interestRepository.findInterestById(interest.getId()).orElse(null);
            newUser.addInterest(interest);
        }

        saveUser(newUser);
        return newUser;
    }

    /**
     * Salva um usuário no banco de dados
     * @param user
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }


    /**
     * Get de todos os usuários que tenham pelo menos um interesse em comum com o usuário atual
     * @return lista de usuários
     */
    public Optional<List<User>> matchingInterestsUsers(User user) {

        List<Interest> currentUserInterests = user.getInterests();
        List<User> users = new ArrayList<>();

        for (Interest interest : currentUserInterests) {
            users.addAll(userRepository.findUsersByInterest(interest));
        }

        return Optional.of(users);
    }

}

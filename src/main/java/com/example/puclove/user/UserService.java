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
    private InteractionRepository interactionRepository;
    @Autowired
    private MatchRepository matchRepository;

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

    /**
     * Adiciona um like ao usuário e verifica se houve um match entre os usuários, caso haja, adiciona um match para cada um.
     * @return
     */
    public boolean likeUser(User user, String targetUserId) {
        String userId = user.getId();
        Interaction interaction = new Interaction(userId, targetUserId, InteractionType.LIKE);

        user.addInteraction(interaction);
        userRepository.addInteraction(userId, interaction);
        interactionRepository.save(interaction);

        if (isAMatch(targetUserId, userId)) {
            User likedUser = userRepository.findUserById(targetUserId).get();

            Match match = new Match(userId, targetUserId);
            Match otherUserMatch = new Match(targetUserId, userId);

            user.addMatch(match);
            likedUser.addMatch(otherUserMatch);

            userRepository.addMatch(userId, match);
            userRepository.addMatch(targetUserId, otherUserMatch);

            matchRepository.save(match);
        }

        return false;
    }

    /**
     * Verifica se houve um match entre os usuários
     * @return
     */
    private boolean isAMatch(String targetUserId, String userId) {
        return interactionRepository.findByUserIdAndTargetUserId(targetUserId, userId).isPresent();
    }

    /**
     * Busca um match entre dois usuários
     * @return
     */
    public Match getMatch(String userId, String otherUserId) {
        return matchRepository.findByUserIdAndOtherUserId(userId, otherUserId);
    }
}

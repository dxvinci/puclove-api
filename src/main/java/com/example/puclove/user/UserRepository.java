package com.example.puclove.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findUserById(ObjectId id);

    @Query("{'interests':?0}")
    List<User> findUsersByInterest(ObjectId interestId);

    @Query("{name:'?0'}")
    Optional<User> findUserByUsername(String name);

    @Query("{email:'?0'}")
    UserDetails findByLogin(String login);

}

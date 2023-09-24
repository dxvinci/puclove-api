package com.example.puclove.interest;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends MongoRepository<Interest, ObjectId> {


}

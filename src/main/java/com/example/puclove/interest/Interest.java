package com.example.puclove.interest;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interests")
public class Interest {

    @Id
    private ObjectId id;
    private String interest;

}

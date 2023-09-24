package com.example.puclove.user;

import com.example.puclove.interest.Interest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String course;
    private String campus;
    @DocumentReference
    private List<Interest> interests;
    private String instagram;
    private Intention intention;

    public User(UserDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.birthDate = data.birthDate();
        this.course = data.course();
        this.campus = data.campus();
        this.instagram = data.instagram();
        this.intention = data.intention();
    }

    public void addInterest(Interest interest) {
        if(interests == null)
            interests = new ArrayList<>();
        interests.add(interest);
    }

}

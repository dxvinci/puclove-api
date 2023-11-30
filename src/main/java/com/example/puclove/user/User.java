package com.example.puclove.user;

import com.example.puclove.interest.Interest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @MongoId
    private String id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String course;
    private String campus;
    @Getter
    @DocumentReference
    private List<Interest> interests;
    private List<Match> matches;
    private List<Interaction> interactions;
    private String instagram;
    private String phoneNumber;
    private Intention intention;
    private UserRole role = UserRole.USER;

    public void addInterest(Interest interest) {
        if(interests == null)
            interests = new ArrayList<>();
        interests.add(interest);
    }

    public void addInteraction(Interaction interaction) {
        interactions.add(interaction);
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    //Spring security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role == UserRole.ADMIN ?
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
                :
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

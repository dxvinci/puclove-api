package com.example.puclove.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Interaction {
    @Id
    private String id;
    private String userId;
    private String targetUserId;
    private InteractionType interactionType;

    public Interaction(String userId, String targetUserId, InteractionType interactionType) {
        this.userId = userId;
        this.targetUserId = targetUserId;
        this.interactionType = interactionType;
    }

}

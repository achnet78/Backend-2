package com.example.Portfolio.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {
    private String _id;
    private String token;
    private String userId;
    private boolean isRevoked;
}

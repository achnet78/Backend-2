package com.example.Portfolio.contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "Contact")
public class Contact {
    private String _id;
    private String name;
    private String email;
    private String message;
}

package com.example.Portfolio.home_page;

import com.example.Portfolio.common_helpers.Header;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "HomePage")
public class HomePage {
    private String _id;
    private Header header;
    private AboutMe aboutMe;
}

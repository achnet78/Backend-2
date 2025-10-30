package com.example.Portfolio.article_page;

import com.example.Portfolio.common_helpers.Header;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ArticlePage")
public class ArticlePage {
    private String _id;
    private Header header;
}

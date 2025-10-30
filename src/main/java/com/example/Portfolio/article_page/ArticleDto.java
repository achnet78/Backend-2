package com.example.Portfolio.article_page;

import com.example.Portfolio.common_helpers.Header;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private String slug;
    private Header header;
    private Boolean isPinned;
}

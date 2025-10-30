package com.example.Portfolio.article_page;

import com.example.Portfolio.common_helpers.Header;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePageDto {
    private Header header;
    private List<ArticleDto> articleDtoList;
}

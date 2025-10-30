package com.example.Portfolio.home_page;

import com.example.Portfolio.article_page.ArticleDto;
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
public class HomePageDto {
    private Header header;
    private AboutMe aboutMe;
    private List<ArticleDto> articleDtoList;
}

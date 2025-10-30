package com.example.Portfolio.articles;

import com.example.Portfolio.common_helpers.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleRequest {
    private Text headerTitle;
    private Text headerDesc;
    private String headerImage;
    private Text content;
    private String contentImage;
    private Boolean isPinned;
}

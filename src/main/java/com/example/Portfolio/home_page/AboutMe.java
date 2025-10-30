package com.example.Portfolio.home_page;

import com.example.Portfolio.common_helpers.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutMe {
    private Text title;
    private Text desc;
    private Text keywords;
    private String imgUrl;
}

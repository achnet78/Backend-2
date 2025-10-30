package com.example.Portfolio.home_page;

import com.example.Portfolio.common_helpers.Header;
import com.example.Portfolio.common_helpers.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateHomePageRequest {
    private Text headerTitle;
    private Text headerDesc;
    private String headerImageBase64;
    private Text aboutMeTitle;
    private Text aboutMeDesc;
    private Text aboutMeKeyWords;
    private String aboutMeImageBase64;
}

package com.example.Portfolio.seo;

import com.example.Portfolio.common_helpers.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeoUpdateRequest {
    private String title;
    private String desc;
    private String keywords;
    private String imageBase64;
}

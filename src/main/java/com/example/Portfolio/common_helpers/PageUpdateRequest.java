package com.example.Portfolio.common_helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUpdateRequest {
    private Text title;
    private Text desc;
    private String imageBase64;
}

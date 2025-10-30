package com.example.Portfolio.common_helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header {
    private Text title;
    private Text desc;
    private String imgUrl;
}

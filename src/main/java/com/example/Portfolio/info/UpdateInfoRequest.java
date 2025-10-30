package com.example.Portfolio.info;

import com.example.Portfolio.common_helpers.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateInfoRequest {
    private String phone;
    private String email;
    private String linkedInLink;
    private String cvLink;
    private Text address;
    private Text footer;
}

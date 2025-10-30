package com.example.Portfolio.articles;

import com.example.Portfolio.common_helpers.Text;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {
    @NotNull(message = "Title must not be null")
    @Valid
    private Text title;
    @NotNull(message = "Description must not be null")
    @Valid
    private Text desc;
    @NotBlank(message = "Image Base64 must not be blank")
    @NotEmpty
    @NotBlank
    private String headerImageBase64;
    @NotNull(message = "Content must not be null")
    @Valid
    private Text content;
    @NotBlank(message = "Image Base64 must not be blank")
    @NotEmpty
    @NotBlank
    private String contentImageBase64;
    @NotNull(message = "Pinned must be explicitly set to true or false")
    private boolean isPinned;

}

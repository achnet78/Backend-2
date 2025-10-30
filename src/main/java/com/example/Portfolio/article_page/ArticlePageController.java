package com.example.Portfolio.article_page;

import com.example.Portfolio.common_helpers.PageUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles-page")
@RequiredArgsConstructor
@Tag(name = "Articles Page")
public class ArticlePageController {
    private final ArticlePageService service;

    @Operation(summary = "Get Article Page", description = "Fetch articles page content including header and articles list")
    @GetMapping
    public ResponseEntity<ArticlePageDto> getNewsPage() {
        return ResponseEntity.ok(service.getArticlePage());
    }

    @Operation(summary = "Update Articles Page Header", description = "Update the header (title, desc, imgUrl) of the articles page")
    @PutMapping
    public ResponseEntity<ArticlePage> updateNewsPageHeader(@RequestBody PageUpdateRequest request) {
        ArticlePage updated = service.updateArticlesPage(request);
        return ResponseEntity.ok(updated);
    }
}

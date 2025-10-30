package com.example.Portfolio.articles;

import com.example.Portfolio.common_helpers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
@Tag(name = "Articles", description = "Endpoints for managing articles")
public class ArticleController {

    private final ArticleService service;

    @GetMapping
    @Operation(summary = "Retrieve all articles", description = "Fetches a list of all available articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(service.getAllArticles());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve Article by slug", description = "Fetches a specific article by its slug")
    public ResponseEntity<Article> findArticleBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(service.findArticleBySlug(slug));
    }

    @DeleteMapping("/{slug}")
    @Operation(summary = "Delete Article By slug", description = "Delete a Specific Article By slug")
    public ResponseEntity<ApiResponse> deleteBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(service.deleteBySlug(slug));
    }

    @PostMapping
    @Operation(summary = "Add Article", description = "Add Article with base64 images")
    public ResponseEntity<Article> addArticle(
            @RequestBody @Valid CreateArticleRequest request) {
        return ResponseEntity.ok(service.addArticle(request));
    }

    @PutMapping("/{slug}")
    @Operation(summary = "Update Article", description = "Update Article with base64 images")
    public ResponseEntity<Article> updateArticle(
            @PathVariable String slug,
            @RequestBody @Valid UpdateArticleRequest request) {
        return ResponseEntity.ok(service.updateArticleBySlug(slug, request));
    }


}

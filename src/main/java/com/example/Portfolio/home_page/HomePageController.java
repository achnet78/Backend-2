package com.example.Portfolio.home_page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home-page")
@RequiredArgsConstructor
@Tag(name = "Home Page")
public class HomePageController {

    private final HomePageService service;

    @Operation(
            summary = "Get Home Page",
            description = "Fetch home page content including header, about me section, and pinned articles"
    )
    @GetMapping
    public ResponseEntity<HomePageDto> getHomePage() {
        return ResponseEntity.ok(service.getHomePage());
    }

    @Operation(
            summary = "Update Home Page",
            description = "Update header and about me sections (titles, descriptions, images, and keywords) of the home page"
    )
    @PutMapping
    public ResponseEntity<HomePage> updateHomePage(@RequestBody UpdateHomePageRequest request) {
        HomePage updated = service.updateHomePage(request);
        return ResponseEntity.ok(updated);
    }
}

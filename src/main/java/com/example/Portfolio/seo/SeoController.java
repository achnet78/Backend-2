package com.example.Portfolio.seo;

import com.example.Portfolio.info.Info;
import com.example.Portfolio.info.UpdateInfoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seo")
@Tag(name = "Seo Management")
public class SeoController {
    private final SeoService service;

    @GetMapping
    @Operation(summary = "Get getSEOData", description = "get all getSEOData")
    public ResponseEntity<SEO> getSEOData() {
        return ResponseEntity.ok(service.getSeoInfo());
    }

    @PutMapping
    @Operation(summary = "Update SEOData", description = "update specific data in SEOData")
    public ResponseEntity<SEO> updateSEOData(
            @RequestBody SeoUpdateRequest request
    ) {
        return ResponseEntity.ok(service.updateSeoInfo(request));
    }

}

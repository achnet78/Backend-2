package com.example.Portfolio.info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
@Tag(name = "Information Management")
public class InfoController {
    private final InfoService service;

    @GetMapping
    @Operation(summary = "Get Information", description = "get all Informations")
    public ResponseEntity<Info> getGlobalData() {
        return ResponseEntity.ok(service.getInformation());
    }

    @PutMapping
    @Operation(summary = "Update Information", description = "update specific data in Informations")
    public ResponseEntity<Info> updateGlobalData(
            @RequestBody UpdateInfoRequest request
    ) {
        return ResponseEntity.ok(service.updateInformation(request));
    }
}

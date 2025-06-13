package com.hazavao.api.endpoint.rest.controller;

import com.hazavao.api.model.DefinitionResponse;
import com.hazavao.api.service.HazavaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hazavao")
public class HazavaoController {

    @Autowired
    private HazavaoService hazavaoService;

    @GetMapping
    public ResponseEntity<DefinitionResponse> getDefinition(@RequestParam String teny) {
        if (teny == null || teny.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        DefinitionResponse response = hazavaoService.getDefinition(teny.trim());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Hazavao API is running!");
    }
}

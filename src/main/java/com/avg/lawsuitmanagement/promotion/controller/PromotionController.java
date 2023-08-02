package com.avg.lawsuitmanagement.promotion.controller;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.promotion.service.PromotionService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/clients")
    public ResponseEntity<String> createClientPromotionKey(@Valid @NotBlank long clientId) {
        return ResponseEntity.ok(promotionService.createClientPromotionKey(clientId));
    }

    @GetMapping("/clients")
    public ResponseEntity<ClientDto> resolveClientPromotionKey(@Valid @NotBlank String key) {
        return ResponseEntity.ok(promotionService.resolveClientPromotionKey(key));
    }

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployeePromotionKey() {
        return ResponseEntity.ok(promotionService.createEmployeePromotionKey());
    }


}

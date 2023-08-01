package com.avg.lawsuitmanagement.promotion.controller;

import com.avg.lawsuitmanagement.promotion.dto.CreatePromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/clients")
    public ResponseEntity<CreatePromotionKeyDto> getClientPromotionKey(long clientId) {
        return ResponseEntity.ok(promotionService.getClientPromotionKey(clientId));
    }


}

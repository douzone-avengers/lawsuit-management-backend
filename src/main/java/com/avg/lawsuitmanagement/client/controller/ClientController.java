package com.avg.lawsuitmanagement.client.controller;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import com.avg.lawsuitmanagement.client.service.ClientService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    // 의뢰인 등록
    @PostMapping()
    public ResponseEntity insertClient(@RequestBody @Valid InsertClientForm form) {
        clientService.insertClient(form);
        return ResponseEntity.ok().build();
    }
}


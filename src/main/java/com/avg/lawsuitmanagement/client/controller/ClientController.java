package com.avg.lawsuitmanagement.client.controller;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.service.ClientService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    // 의뢰인 상세정보
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> selectClientDetailInfo(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    // 의뢰인 등록
    @PostMapping()
    public ResponseEntity<Void> insertClient(@RequestBody @Valid InsertClientForm form) {
        clientService.insertClient(form);
        return ResponseEntity.ok().build();
    }

    // 의뢰인 수정
    @PutMapping("/{clientId}")
    public ResponseEntity<Void> updateClientInfo(@PathVariable("clientId") Long clientId, @RequestBody @Valid UpdateClientInfoForm form) {
        clientService.updateClientInfo(clientId, form);
        return ResponseEntity.ok().build();
    }

    // 의뢰인 삭제
    @PatchMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientInfo(@PathVariable("clientId") Long clientId) {
        clientService.deleteClientInfo(clientId);
        return ResponseEntity.ok().build();
    }

    // 의뢰인 목록
    @GetMapping()
    public ResponseEntity<List<ClientDto>> selectClientList() {
        return ResponseEntity.ok(clientService.selectClientList());
    }
}


package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.dto.ATMStatusDTO;
import br.com.ibm.challenge.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

@RestController
@RequestMapping("/atm")
public class ATMController {
    @Autowired
    private ATMService atmService;

    @GetMapping("/open")
    public ResponseEntity<ATMStatusDTO> openATM() {
        return ResponseEntity.ok(atmService.setATM(FALSE));
    }

    @GetMapping("/close")
    public ResponseEntity<ATMStatusDTO> closeATM() {
        return ResponseEntity.ok(atmService.setATM(TRUE));
    }
}

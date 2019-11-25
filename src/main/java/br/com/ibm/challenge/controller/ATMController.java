package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("/atm")
public class ATMController {
    @Autowired
    private ATMService atmService;

    @GetMapping("/open")
    public ResponseEntity<String> openATM() {
        atmService.setClosed(FALSE);

        return ResponseEntity.ok(atmService.status());
    }

    @GetMapping("/close")
    public ResponseEntity<String> closeATM() {
        atmService.setClosed(TRUE);

        return ResponseEntity.ok(atmService.status());
    }
}

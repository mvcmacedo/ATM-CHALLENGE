package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<History>> get() {
        return ResponseEntity.ok(historyService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> get(@PathVariable String id) {
        try {
            return ResponseEntity.ok(historyService.get(id));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "History Not Found", e);
        }
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<History>> getByAccount(@PathVariable String id) {
        try {
            return ResponseEntity.ok(historyService.getByAccountId(id));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "History Not Found", e);
        }
    }
}

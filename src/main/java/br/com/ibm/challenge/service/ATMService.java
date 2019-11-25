package br.com.ibm.challenge.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class ATMService {
    private boolean isClosed = true;

    public String status() {
        return "ATM is " + (this.isClosed() ? "Closed" : "Open");
    }
}

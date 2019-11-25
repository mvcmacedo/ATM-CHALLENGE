package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dto.ATMStatusDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class ATMService {
    private boolean isClosed = true;

    private String status() {
        return "ATM is " + (this.isClosed() ? "Closed" : "Open");
    }

    public ATMStatusDTO setATM(boolean status) {
        this.isClosed = status;

        return ATMStatusDTO.builder()
            .status(this.status())
            .build();
    }
}

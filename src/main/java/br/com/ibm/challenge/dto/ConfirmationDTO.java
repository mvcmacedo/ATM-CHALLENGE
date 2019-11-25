package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.model.History;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConfirmationDTO {
    private Account account;
    private History history;
}

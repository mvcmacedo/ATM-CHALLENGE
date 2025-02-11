package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.model.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDTO {
    private Account account;
    private History history;
}

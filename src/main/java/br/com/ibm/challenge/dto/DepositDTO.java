package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.helper.DepositType;
import lombok.Getter;

@Getter
public class DepositDTO {
    private Double amount;
    private DepositType type;
}

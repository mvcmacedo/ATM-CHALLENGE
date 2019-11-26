package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.helper.DepositType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepositDTO {
    private Double amount;
    private DepositType type;
}

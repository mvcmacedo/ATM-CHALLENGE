package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.helper.DepositType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {
    private Double amount;
    private DepositType type;
}

package br.com.ibm.challenge.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WithdrawalDTO {
    private Double amount;
}

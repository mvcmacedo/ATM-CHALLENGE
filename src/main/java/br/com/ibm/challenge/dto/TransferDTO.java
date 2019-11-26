package br.com.ibm.challenge.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferDTO {
    private String destination_account;
    private Double amount;
}

package br.com.ibm.challenge.dto;

import lombok.Getter;

@Getter
public class TransferDTO {
    private String destination_account;
    private Double amount;
}

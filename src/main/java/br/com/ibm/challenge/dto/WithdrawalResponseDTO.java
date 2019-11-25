package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.helper.MoneyBills;
import br.com.ibm.challenge.model.History;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WithdrawalResponseDTO {
    private Double balance;
    private History history;
    private List<MoneyBills> moneyBills;
}

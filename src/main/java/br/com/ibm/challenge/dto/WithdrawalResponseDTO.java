package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.helper.MoneyBills;
import br.com.ibm.challenge.model.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalResponseDTO {
    private Double balance;
    private History history;
    private List<MoneyBills> money_bills;
}

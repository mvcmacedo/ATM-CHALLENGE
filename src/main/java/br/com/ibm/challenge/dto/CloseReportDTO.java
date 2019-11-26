package br.com.ibm.challenge.dto;

import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.model.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloseReportDTO {
    private String account;
    private Boolean status;
    private LocalDateTime date;
    private List<History> account_history;
}

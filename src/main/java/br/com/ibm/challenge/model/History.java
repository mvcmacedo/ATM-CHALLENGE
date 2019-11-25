package br.com.ibm.challenge.model;

import br.com.ibm.challenge.helper.DepositType;
import br.com.ibm.challenge.helper.HistoryType;

import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@Document(collection = "history")
public class History {
    @Id
    private String id;

    @NonNull
    private HistoryType type;

    @NonNull
    private Double amount;

    @NonNull
    private String account;

    @Nullable
    private DepositType deposit_type;

    @Nullable
    private String transfer_account;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}

package br.com.ibm.challenge.model;

import lombok.Getter;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@Document(collection = "account")
public class Account {
    @Id
    private String id;

    @NonNull
    private String digits;

    @Builder.Default
    private Double balance = 0d;

    @Builder.Default
    private Boolean active = true;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}

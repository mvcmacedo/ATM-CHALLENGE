package br.com.ibm.challenge.model;

import lombok.Getter;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "account")
public class Account {
    @Id
    private ObjectId id;

    @NonNull
    private String number;

    private Double balance = 0d;

    private Boolean active = false;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}

package com.example.nonprofitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailSentResponseDto {

    private long nonprofitId;
    private String nonprofitName;
    private String sender;
    private String recipient;
    private String message;
    private LocalDateTime dateSent;

    public EmailSentResponseDto(long nonprofitId, String nonprofitName, String sender,
                                String recipient, String message, LocalDateTime dateSent) {
        this.nonprofitId = nonprofitId;
        this.nonprofitName = nonprofitName;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.dateSent = dateSent;
    }
}
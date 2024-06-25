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
public class EmailSentDto {

    private long nonprofitId;
    private String sender;
    private String recipient;
    private String message;
    private LocalDateTime dateSent;

    public EmailSentDto(long nonprofitId, String sender, String recipient, String message, LocalDateTime dateSent) {
        this.nonprofitId = nonprofitId;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.dateSent = dateSent;
    }
}
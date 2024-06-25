package com.example.nonprofitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SendEmailDto {

    private Boolean sendEmail;
    private String templateMessage;

    public SendEmailDto(Boolean sendEmail, String message) {
        this.sendEmail = sendEmail;
        this.templateMessage = message;
    }
}
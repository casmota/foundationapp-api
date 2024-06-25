package com.example.nonprofitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMsgDto {

    private Integer status;
    private String message;

    public ResponseMsgDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
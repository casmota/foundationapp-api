package com.example.nonprofitapp.dto;

import com.example.nonprofitapp.model.EmailSent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class NonprofitDto {

    private long id;
    private String name;
    private String address;
    private String email;

    public NonprofitDto(long id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }
}
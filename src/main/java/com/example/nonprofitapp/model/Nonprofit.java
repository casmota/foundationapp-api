package com.example.nonprofitapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "nonprofits")
public class Nonprofit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy="nonprofit")
    private List<EmailSent> emails;

    public Nonprofit(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }
}
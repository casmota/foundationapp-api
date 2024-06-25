package com.example.nonprofitapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "emailsents")
public class EmailSent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "message")
    private String message;

    @Column(name = "date_sent")
    private LocalDateTime dateSent;

    @ManyToOne
    @JoinColumn(name="nonprofit_id", nullable=false)
    private Nonprofit nonprofit;

    public EmailSent(Nonprofit nonprofit, String sender, String recipient, String message, LocalDateTime dateSent) {
        this.nonprofit = nonprofit;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.dateSent = dateSent;
    }
}
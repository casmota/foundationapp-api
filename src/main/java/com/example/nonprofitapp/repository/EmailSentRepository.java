package com.example.nonprofitapp.repository;

import com.example.nonprofitapp.model.EmailSent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmailSentRepository extends JpaRepository<EmailSent, Long> {
    List<EmailSent> findByDateSent(LocalDate dateSent);
}

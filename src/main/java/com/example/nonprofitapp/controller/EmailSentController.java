package com.example.nonprofitapp.controller;

import com.example.nonprofitapp.dto.EmailSentDto;
import com.example.nonprofitapp.dto.EmailSentResponseDto;
import com.example.nonprofitapp.dto.ResponseMsgDto;
import com.example.nonprofitapp.dto.SendEmailDto;
import com.example.nonprofitapp.model.EmailSent;
import com.example.nonprofitapp.model.Nonprofit;
import com.example.nonprofitapp.repository.EmailSentRepository;
import com.example.nonprofitapp.repository.NonprofitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class EmailSentController {

    @Autowired
    EmailSentRepository emailSentRepository;

    @Autowired
    NonprofitRepository nonprofitRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String FOUNDATION_EMAIL = "foundation@email.com";

    @GetMapping("/emailsents")
    public ResponseEntity<List<EmailSentResponseDto>> getAllNonprofits(@RequestParam(required = false) LocalDate dateSent) {
        try {
            List<EmailSent> emailSents = new ArrayList<EmailSent>();

            if (dateSent == null)
                emailSents.addAll(emailSentRepository.findAll());
            else
                emailSents.addAll(emailSentRepository.findByDateSent(dateSent));

            if (emailSents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(emailSents.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/emailsents/{id}")
    public ResponseEntity<EmailSent> getEmailsentById(@PathVariable("id") long id) {
        Optional<EmailSent> emailsentData = emailSentRepository.findById(id);

        return emailsentData.map(emailsent -> new ResponseEntity<>(emailsent, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/emailsents")
    public ResponseEntity<EmailSentResponseDto> createEmailsents(@RequestBody EmailSentDto emailSentDto) {
        try {
            Optional<Nonprofit> nonprofit = nonprofitRepository.findById(emailSentDto.getNonprofitId());
            if (nonprofit.isPresent()) {
                EmailSent _emailSent = emailSentRepository
                        .save(new EmailSent(nonprofit.get(), emailSentDto.getSender(),
                                emailSentDto.getRecipient(), emailSentDto.getMessage(), emailSentDto.getDateSent()));

                EmailSentResponseDto emailSentResponseDto = convertToDto(_emailSent);

                return new ResponseEntity<>(emailSentResponseDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/emailsents/sendemails")
    public ResponseEntity<ResponseMsgDto> sendEmails(@RequestBody SendEmailDto sendEmailDto) {
        try {
            if (sendEmailDto.getSendEmail()) {
                List<Nonprofit> nonprofits = nonprofitRepository.findAll();
                if (!nonprofits.isEmpty()) {
                    for (Nonprofit nonprofit: nonprofits) {
                        String templateMsg = sendEmailDto.getTemplateMessage()
                                .replace("{name}", nonprofit.getName())
                                .replace("{address}", nonprofit.getAddress());

                        emailSentRepository.save(new EmailSent(nonprofit, FOUNDATION_EMAIL,
                                        nonprofit.getEmail(), templateMsg, LocalDateTime.now()));
                    }

                    return new ResponseEntity<>(new ResponseMsgDto(200, "Emails sent successfully."),
                            HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new ResponseMsgDto(200, "Emails not sent."),
                        HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/emailsents/{id}")
    public ResponseEntity<EmailSent> updateEmailsent(@PathVariable("id") long id, @RequestBody EmailSent emailSent) {
        Optional<EmailSent> emailSentlData = emailSentRepository.findById(id);

        if (emailSentlData.isPresent()) {
            EmailSent _emailSent = emailSentlData.get();
            _emailSent.setSender(emailSent.getSender());
            _emailSent.setRecipient(emailSent.getRecipient());
            _emailSent.setMessage(emailSent.getMessage());
            _emailSent.setDateSent(LocalDateTime.now());
            return new ResponseEntity<>(emailSentRepository.save(_emailSent), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/emailsents/{id}")
    public ResponseEntity<HttpStatus> deleteEmailsent(@PathVariable("id") long id) {
        try {
            emailSentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/emailsents")
    public ResponseEntity<HttpStatus> deleteAllNonprofits() {
        try {
            emailSentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private EmailSentResponseDto convertToDto(EmailSent emailSent) {
        return modelMapper.map(emailSent, EmailSentResponseDto.class);
    }

}

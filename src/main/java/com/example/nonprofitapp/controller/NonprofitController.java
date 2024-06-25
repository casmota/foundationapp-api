package com.example.nonprofitapp.controller;

import com.example.nonprofitapp.dto.NonprofitDto;
import com.example.nonprofitapp.model.Nonprofit;
import com.example.nonprofitapp.repository.NonprofitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class NonprofitController {

    @Autowired
    NonprofitRepository nonprofitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/nonprofits")
    @ResponseBody
    public ResponseEntity<List<NonprofitDto>> getAllNonprofits(@RequestParam(required = false) String email) {
        try {
            List<Nonprofit> nonprofits = new ArrayList<Nonprofit>();

            if (email == null)
                nonprofits.addAll(nonprofitRepository.findAll());
            else
                nonprofits.addAll(nonprofitRepository.findByEmailContaining(email));

            if (nonprofits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nonprofits.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nonprofits/{id}")
    public ResponseEntity<Nonprofit> getNonprofitById(@PathVariable("id") long id) {
        Optional<Nonprofit> nonprofitData = nonprofitRepository.findById(id);

        return nonprofitData.map(nonprofit -> new ResponseEntity<>(nonprofit, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/nonprofits")
    public ResponseEntity<NonprofitDto> createNonprofit(@RequestBody Nonprofit nonprofit) {
        try {
            Nonprofit _nonprofit = nonprofitRepository
                    .save(new Nonprofit(nonprofit.getName(), nonprofit.getAddress(), nonprofit.getEmail()));
            return new ResponseEntity<>(convertToDto(_nonprofit), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/nonprofits/{id}")
    public ResponseEntity<NonprofitDto> updateNonprofit(@PathVariable("id") long id, @RequestBody Nonprofit nonprofit) {
        Optional<Nonprofit> nonprofitlData = nonprofitRepository.findById(id);

        if (nonprofitlData.isPresent()) {
            Nonprofit _nonprofit = nonprofitlData.get();
            _nonprofit.setName(nonprofit.getName());
            _nonprofit.setAddress(nonprofit.getAddress());
            _nonprofit.setEmail(nonprofit.getEmail());
            return new ResponseEntity<>(convertToDto(nonprofitRepository.save(_nonprofit)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/nonprofits/{id}")
    public ResponseEntity<HttpStatus> deleteNonprofit(@PathVariable("id") long id) {
        try {
            nonprofitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/nonprofits")
    public ResponseEntity<HttpStatus> deleteAllNonprofits() {
        try {
            nonprofitRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private NonprofitDto convertToDto(Nonprofit nonprofit) {
        return modelMapper.map(nonprofit, NonprofitDto.class);
    }

}

package com.example.nonprofitapp.repository;

import com.example.nonprofitapp.model.Nonprofit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NonprofitRepository extends JpaRepository<Nonprofit, Long> {
    List<Nonprofit> findByEmailContaining(String title);
}

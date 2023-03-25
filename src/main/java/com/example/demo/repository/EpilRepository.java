package com.example.demo.repository;

import com.example.demo.model.Epil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpilRepository extends JpaRepository<Epil, Long> {
}

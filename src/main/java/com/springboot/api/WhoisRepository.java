package com.springboot.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WhoisRepository extends JpaRepository<WhoisResultado, Long> {
    Optional<WhoisResultado> findFirstByOrderByIdDesc();
}
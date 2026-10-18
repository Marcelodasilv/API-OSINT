package com.springboot.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShodanRepository extends JpaRepository<ShodanResultado, Long> {
    Optional<ShodanResultado> findFirstByOrderByIdDesc();
}
package com.springboot.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HarvesterRepository extends JpaRepository<HarvesterResultado, Long> {
    Optional<HarvesterResultado> findFirstByOrderByIdDesc();
}
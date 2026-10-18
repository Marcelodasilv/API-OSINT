package com.springboot.api;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shodan_resultado")
public class ShodanResultado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String organizacao;
    public String pais;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "shodan_portas", joinColumns = @JoinColumn(name = "shodan_id"))
    @Column(name = "porta")
    public List<Integer> portas = new ArrayList<>();

    public LocalDateTime criadoEm = LocalDateTime.now();
}
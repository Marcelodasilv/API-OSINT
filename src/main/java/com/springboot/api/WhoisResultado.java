package com.springboot.api;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "whois_resultado")
public class WhoisResultado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String registrador;
    public String criacao;
    public String expiracao;
    public String pais;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "whois_name_servers", joinColumns = @JoinColumn(name = "whois_id"))
    @Column(name = "name_server")
    public List<String> nameServers = new ArrayList<>();

    public LocalDateTime criadoEm = LocalDateTime.now();
}
package com.springboot.api;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "harvester_resultado")
public class HarvesterResultado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String dominio;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "harvester_emails", joinColumns = @JoinColumn(name = "harvester_id"))
    @Column(name = "email")
    public List<String> emails = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "harvester_hosts", joinColumns = @JoinColumn(name = "harvester_id"))
    @Column(name = "host")
    public List<String> hosts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "harvester_ips", joinColumns = @JoinColumn(name = "harvester_id"))
    @Column(name = "ip")
    public List<String> ips = new ArrayList<>();

    public LocalDateTime criadoEm = LocalDateTime.now();
}
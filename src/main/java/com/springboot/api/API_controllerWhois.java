package com.springboot.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/whois")
public class API_controllerWhois {
    public static class whoisDTO {
        public String rs;
        public String cd;
        public String ed;
        public List<String> ns;
        public String c;}

    private final WhoisRepository repo;

    public API_controllerWhois(WhoisRepository repo) {
        this.repo = repo;
    }

    @PutMapping(value = "/put")
    public String put(@RequestBody whoisDTO dto) {
        WhoisResultado r = new WhoisResultado();
        r.registrador = dto.rs;
        r.criacao = dto.cd;
        r.expiracao = dto.ed;
        r.nameServers = dto.ns;
        r.pais = dto.c;
        repo.save(r);
        return "Ok";
    }

    @GetMapping(value = "/get")
    public WhoisResultado get() {
        return repo.findFirstByOrderByIdDesc().orElse(null);
    }

    @GetMapping(value = "/todos")
    public List<WhoisResultado> todos() {
        return repo.findAll();
    }
}
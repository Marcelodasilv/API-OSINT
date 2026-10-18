package com.springboot.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/harvester")
public class API_controllerHarvester {
    public static class harvesterDTO {
        public String d;
        public List<String> em;
        public List<String> ho;
        public List<String> ip;}

    private final HarvesterRepository repo;

    public API_controllerHarvester(HarvesterRepository repo) {
        this.repo = repo;
    }

    @PutMapping(value = "/put")
    public String put(@RequestBody harvesterDTO dto) {
        HarvesterResultado r = new HarvesterResultado();
        r.dominio = dto.d;
        r.emails = dto.em;
        r.hosts = dto.ho;
        r.ips = dto.ip;
        repo.save(r);
        return "Ok";
    }

    @GetMapping(value = "/get")
    public HarvesterResultado get() {
        return repo.findFirstByOrderByIdDesc().orElse(null);
    }

    @GetMapping(value = "/todos")
    public List<HarvesterResultado> todos() {
        return repo.findAll();
    }
}
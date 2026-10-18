package com.springboot.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/shodan")
public class API_controllerShodan {
    public static class shodanDTO {
        public String org;
        public String ps;
        public List<Integer> pr;}

    private final ShodanRepository repo;

    public API_controllerShodan(ShodanRepository repo) {
        this.repo = repo;
    }

    @PutMapping(value = "/put")
    public String put(@RequestBody shodanDTO dto) {
        ShodanResultado r = new ShodanResultado();
        r.organizacao = dto.org;
        r.pais = dto.ps;
        r.portas = dto.pr;
        repo.save(r);
        return "Ok";
    }

    @GetMapping(value = "/get")
    public ShodanResultado get() {
        return repo.findFirstByOrderByIdDesc().orElse(null);
    }

    @GetMapping(value = "/todos")
    public List<ShodanResultado> todos() {
        return repo.findAll();
    }
}
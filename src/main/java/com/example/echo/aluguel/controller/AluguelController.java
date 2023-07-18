package com.example.echo.aluguel.controller;

import com.example.echo.aluguel.model.Aluguel;
import com.example.echo.aluguel.model.aluguelHelper;
import com.example.echo.aluguel.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    private final AluguelService aluguelService;

    @Autowired
    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @PostMapping("/Aluguel")
    public ResponseEntity<Aluguel> realizarAluguel(@RequestBody aluguelHelper aluguel) {
        ResponseEntity<Aluguel> novoAluguel = aluguelService.realizarAluguel(aluguel.getCiclista(), aluguel.getTranca());
        return novoAluguel;
    }

    @PostMapping("/Devolucao")
    public ResponseEntity<Aluguel> finalizarAluguel(@RequestBody aluguelHelper aluguel) {
        ResponseEntity<Aluguel> novoAluguel = aluguelService.finalizarAluguel(aluguel.getBicicleta(), aluguel.getTranca());
        return novoAluguel;
    }
}

package com.example.echo.client;

import com.example.echo.aluguel.model.Bicicleta;
import com.example.echo.aluguel.model.Tranca;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
//"http://localhost:8180"
@FeignClient(url = "https://pm-production.up.railway.app/", name = "equipamento")
public interface EquipamentoClient {

    @GetMapping("/bicicleta/{id}")
    Bicicleta recuperarBicicleta(@PathVariable int id);

    @GetMapping("/tranca/{id}")
    Tranca recuperarTranca(@PathVariable int id);

    @PostMapping("/bicicleta/{id}/status/{status}")
    Bicicleta alterarStatusBicicleta(@PathVariable int id, @PathVariable String status);

    @PutMapping("/tranca/{id}/destrancar")
    Tranca destrancarTranca(@PathVariable int id);

    @PutMapping("/tranca/{id}/trancar")
    Tranca trancarTranca(@PathVariable int id);

    @PutMapping("/tranca/{id}")
    Tranca alterarTranca(@PathVariable int id, @RequestBody Tranca tranca);
}

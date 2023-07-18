package com.example.echo.client;

import com.example.echo.aluguel.model.Cobranca;

import com.example.echo.aluguel.model.Email;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(url = "https://pm-production-74a4.up.railway.app/", name = "externo")
public interface ExternoClient {

  @PostMapping("/cobranca")
  Cobranca criarCobranca(@RequestBody Cobranca cobranca);

  @PostMapping("/email")
  Email emailCriado(@RequestBody Email email);

}

package com.example.echo.aluguel.service;

import com.example.echo.aluguel.model.*;
import com.example.echo.aluguel.repository.AluguelRepository;
import com.example.echo.client.EquipamentoClient;
import com.example.echo.client.ExternoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final EquipamentoClient equipamentoClient;
    private final CiclistaService ciclistaService;
    private final ExternoClient externoClient;

    @Autowired
    public AluguelService(AluguelRepository aluguelRepository, CiclistaService ciclistaService, EquipamentoClient equipamentoClient, ExternoClient externoClient) {
        this.aluguelRepository = aluguelRepository;
        this.ciclistaService = ciclistaService;
        this.equipamentoClient = equipamentoClient;
        this.externoClient = externoClient;
    }

    public ResponseEntity<Aluguel> realizarAluguel(int idCiclista, int trancaInicio) {

        //Valida o número da tranca.
        Tranca tranca = equipamentoClient.recuperarTranca(trancaInicio);
        Bicicleta bicicleta = tranca.getBicicleta();
        if (tranca == null) {
            //Número da tranca inválido.
            return ResponseEntity.badRequest().build();
        }

        //Lê o número da bicicleta presa na tranca.
        int numeroBicicleta = bicicleta.getNumero();

        //Verifica se o ciclista pode pegar bicicleta.
        Ciclista ciclista = ciclistaService.obterCiclistaPorId(idCiclista);
        if (ciclista == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!ciclistaService.verificarPermiteAluguelCiclista(idCiclista)) {
            //Ciclista já tem um aluguel.
            String corpoEmail = "Caro ciclista, você já tem um aluguel em andamento.";
            Email email = new Email("pmemail@gmail.com", "Novo Aluguel", corpoEmail);

            externoClient.emailCriado(email);
            return ResponseEntity.badRequest().build();
        }
        if (!bicicleta.getStatus().equals("DISPONÍVEL")) {
            //A bicicleta não está em condições de uso.
            return ResponseEntity.badRequest().build();
        }

        //Envia a cobrança para a Administradora CC.
        Cobranca cobranca = new Cobranca();
        cobranca.setPreco(10.00);
        cobranca.setCiclista(ciclista);
        Cobranca cobrancaCriada = externoClient.criarCobranca(cobranca); //** INTEGRAÇÃO **

        if (cobrancaCriada == null) {
            return ResponseEntity.badRequest().build();
        }

        //Registra os dados da retirada da bicicleta.
        LocalDateTime horaInicio = LocalDateTime.now();
        int trancaFim = 0;
        LocalDateTime horaFim = null;
        double valorCobranca = calcularCobranca();


        equipamentoClient.alterarStatusBicicleta(bicicleta.getId(), "em uso");
        equipamentoClient.alterarTranca(trancaInicio, tranca);
        equipamentoClient.destrancarTranca(trancaInicio);


        // Criação do objeto Aluguel
        Aluguel aluguel = new Aluguel();
        aluguel.setBicicleta(bicicleta.getId());
        aluguel.setHoraInicio(horaInicio);
        aluguel.setTrancaFim(trancaFim);
        aluguel.setHoraFim(horaFim);
        aluguel.setCobranca(valorCobranca);
        aluguel.setCiclista(idCiclista);
        aluguel.setTrancaInicio(trancaInicio);


        aluguel = aluguelRepository.salvarAluguel(aluguel);

        String corpoEmail = "Caro ciclista, seu aluguel custou: " + aluguel.getCobranca();
        Email email = new Email("pmemail@gmail.com", "Novo Aluguel", corpoEmail);

        //externoClient.emailCriado(email); //** INTEGRAÇÃO **

        return ResponseEntity.ok(aluguel);
    }


    private double calcularCobranca() {
        return 10.00;
    }

    public ResponseEntity<Aluguel> finalizarAluguel(int bicicleta, int tranca) {
        Tranca trancaRecuperada = equipamentoClient.recuperarTranca(tranca);
        Bicicleta bicicletaRecuperada = equipamentoClient.recuperarBicicleta(bicicleta);

        if (bicicletaRecuperada == null) {
            return ResponseEntity.badRequest().build();
        }

        Aluguel recuperado = aluguelRepository.obterTodosAlugueis().stream().filter(aluguel ->
            aluguel.getBicicleta() == bicicleta && aluguel.getHoraFim() == null
        ).findFirst().get();
        recuperado.setHoraFim(LocalDateTime.now());
        recuperado.setTrancaFim(tranca);

        long intervalo = ChronoUnit.MINUTES.between(recuperado.getHoraInicio(), recuperado.getHoraFim());
        double preco = 10.00;
        while(intervalo > 150) {
            preco+=5;
            intervalo -=30;
        }
        recuperado.setCobranca(preco);

        bicicletaRecuperada = equipamentoClient.alterarStatusBicicleta(bicicleta, "DISPONÍVEL");
        trancaRecuperada.setBicicleta(bicicletaRecuperada);

        equipamentoClient.trancarTranca(tranca);
        equipamentoClient.alterarTranca(tranca, trancaRecuperada);

        String corpoEmail = "Caro ciclista, seu aluguel custou: " + recuperado.getCobranca();
        Email email = new Email("pmemail@gmail.com", "Cobrança extra", corpoEmail);

        //externoClient.emailCriado(email); //** INTEGRAÇÃO **


        return ResponseEntity.ok(recuperado);
    }
}

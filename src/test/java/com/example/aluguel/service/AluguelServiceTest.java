package com.example.aluguel.service;

import com.example.echo.aluguel.model.*;
import com.example.echo.aluguel.repository.AluguelRepository;
import com.example.echo.aluguel.service.AluguelService;
import com.example.echo.aluguel.service.CiclistaService;
import com.example.echo.client.EquipamentoClient;
import com.example.echo.client.ExternoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AluguelServiceTest {

    @Mock
    private AluguelRepository aluguelRepository;

    @Mock
    private CiclistaService ciclistaService;

    @Mock
    private EquipamentoClient equipamentoClient;

    @Mock
    private ExternoClient externoClient;

    @InjectMocks
    private AluguelService aluguelService;

    private Aluguel aluguel;
    private Bicicleta bicicleta;
    private Tranca tranca;
    private Ciclista ciclista;

    @BeforeEach
    void setup() {
        // Inicializar objetos
        aluguel = new Aluguel();
        bicicleta = new Bicicleta();
        tranca = new Tranca();
        ciclista = new Ciclista();

        // Configurar os mocks e simular comportamento dos microsserviços externos
        when(equipamentoClient.recuperarTranca(anyInt())).thenReturn(tranca);
        when(equipamentoClient.alterarStatusBicicleta(anyInt(), anyString())).thenReturn(bicicleta);
        when(equipamentoClient.alterarTranca(anyInt(), any(Tranca.class))).thenReturn(tranca);
        when(equipamentoClient.destrancarTranca(anyInt())).thenReturn(tranca);

        when(ciclistaService.obterCiclistaPorId(anyInt())).thenReturn(ciclista);
        when(ciclistaService.verificarPermiteAluguelCiclista(anyInt())).thenReturn(true);

        when(externoClient.criarCobranca(any(Cobranca.class))).thenReturn(new Cobranca());


        when(aluguelRepository.salvarAluguel(any(Aluguel.class))).thenReturn(aluguel);
        when(aluguelRepository.obterTodosAlugueis()).thenReturn(Arrays.asList(aluguel));
    }

    @Test
    void testRealizarAluguel() {
        //Método de realizar aluguel
        ResponseEntity<Aluguel> response = aluguelService.realizarAluguel(1, 123);
        assertEquals(ResponseEntity.ok(aluguel), response);

        //Chamadas para métodos do equipamentoClient
        verify(equipamentoClient).recuperarTranca(123);
        verify(equipamentoClient).alterarStatusBicicleta(anyInt(), eq("em uso"));
        verify(equipamentoClient).alterarTranca(anyInt(), any(Tranca.class));
        verify(equipamentoClient).destrancarTranca(anyInt());

        //Chamada para métodos do ciclistaService
        verify(ciclistaService).obterCiclistaPorId(1);
        verify(ciclistaService).verificarPermiteAluguelCiclista(1);

        //Chamada para métodos do externoClient
        verify(externoClient).criarCobranca(any(Cobranca.class));

        //Chamada para método do AluguelRepository
        verify(aluguelRepository).salvarAluguel(any(Aluguel.class));
    }

    @Test
    void testFinalizarAluguel() {
        aluguel.setBicicleta(1);
        aluguel.setTrancaFim(123);
        aluguel.setHoraInicio(LocalDateTime.now().minusMinutes(160)); // Mais de 150 minutos de aluguel

        // Configurar os mocks para simular o comportamento dos microsserviços externos
        when(equipamentoClient.recuperarTranca(anyInt())).thenReturn(tranca);
        when(equipamentoClient.recuperarBicicleta(anyInt())).thenReturn(bicicleta);
        when(equipamentoClient.alterarStatusBicicleta(anyInt(), anyString())).thenReturn(bicicleta);
        when(equipamentoClient.trancarTranca(anyInt())).thenReturn(tranca);
        when(equipamentoClient.alterarTranca(anyInt(), any(Tranca.class))).thenReturn(tranca);

        //Chamar finalizar Aluguel
        ResponseEntity<Aluguel> response = aluguelService.finalizarAluguel(1, 123);
        assertEquals(ResponseEntity.ok(aluguel), response);

        // Verificar chamadas aos métodos do equipamentoClient
        verify(equipamentoClient).recuperarTranca(123);
        verify(equipamentoClient).recuperarBicicleta(1);
        verify(equipamentoClient).alterarStatusBicicleta(1, "DISPONÍVEL");
        verify(equipamentoClient).trancarTranca(123);
        verify(equipamentoClient).alterarTranca(123, tranca);

        // Verificar chamadas ao método do AluguelRepository
        verify(aluguelRepository).obterTodosAlugueis();
        verify(aluguelRepository).salvarAluguel(any(Aluguel.class));
    }
}

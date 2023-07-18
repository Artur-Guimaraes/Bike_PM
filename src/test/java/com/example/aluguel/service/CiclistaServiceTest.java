package com.example.aluguel.service;

import com.example.echo.aluguel.model.Ciclista;
import com.example.echo.aluguel.repository.AluguelRepository;
import com.example.echo.aluguel.repository.CiclistaRepository;
import com.example.echo.aluguel.service.CiclistaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class CiclistaServiceTest {

    private CiclistaService ciclistaService;
    private CiclistaRepository ciclistaRepository;
    private AluguelRepository aluguelRepository;

    @BeforeEach
    void setup() {
        ciclistaRepository = Mockito.mock(CiclistaRepository.class);
        ciclistaService = new CiclistaService(ciclistaRepository, aluguelRepository);
    }

    @Test
    void testCadastrarCiclista() {
        Ciclista ciclista = new Ciclista();
        ciclista.setId(1);
        ciclista.setNome("João");

        Mockito.when(ciclistaRepository.saveCiclista(ciclista)).thenReturn(ciclista);

        Ciclista result = ciclistaService.cadastrarCiclista(ciclista);

        Assertions.assertEquals(ciclista, result);
    }

    @Test
    void testObterCiclistaPorId_CasoExista() {
        int id = 1;
        Ciclista ciclista = new Ciclista();
        ciclista.setId(id);
        ciclista.setNome("Raphael");

        Mockito.when(ciclistaRepository.findCiclistaById(id)).thenReturn(ciclista);

        Ciclista result = ciclistaService.obterCiclistaPorId(id);

        Assertions.assertEquals(ciclista, result);
    }

    @Test
    void testObterCiclistaPorId_CasoNaoExista() {
        int id = 1;

        Mockito.when(ciclistaRepository.findCiclistaById(id)).thenReturn(null);

        Ciclista result = ciclistaService.obterCiclistaPorId(id);

        Assertions.assertNull(result);
    }

    @Test
    void testObterTodosCiclistas() {
        List<Ciclista> ciclistas = new ArrayList<>();

        Ciclista ciclista1 = new Ciclista();
        ciclista1.setId(1);
        ciclista1.setNome("Raphael");

        Ciclista ciclista2 = new Ciclista();
        ciclista2.setId(2);
        ciclista2.setNome("Joana");
        ciclistas.add(ciclista1);
        ciclistas.add(ciclista2);

        Mockito.when(ciclistaRepository.findAllCiclistas()).thenReturn(ciclistas);

        List<Ciclista> result = ciclistaService.obterTodosCiclistas();

        Assertions.assertEquals(ciclistas, result);
    }

    @Test
    void testAtualizarCiclista_CasoExista() {
        int id = 1;
        Ciclista ciclistaExistente = new Ciclista();
        ciclistaExistente.setId(id);
        ciclistaExistente.setNome("Marcos");

        Ciclista ciclistaAtualizado = new Ciclista();
        ciclistaAtualizado.setId(id);
        ciclistaAtualizado.setNome("Antonio");

        Mockito.when(ciclistaRepository.findCiclistaById(id)).thenReturn(ciclistaExistente);
        Mockito.when(ciclistaRepository.saveCiclista(ciclistaAtualizado)).thenReturn(ciclistaAtualizado);

        Ciclista result = ciclistaService.atualizarCiclista(id, ciclistaAtualizado);

        Assertions.assertEquals(ciclistaAtualizado, result);
    }

    @Test
    void testAtualizarCiclista_CasoNaoExista() {
        int id = 1;
        Ciclista ciclistaAtualizado = new Ciclista();
        ciclistaAtualizado.setId(id);
        ciclistaAtualizado.setNome("Wesley");

        Mockito.when(ciclistaRepository.findCiclistaById(id)).thenReturn(null);

        Ciclista result = ciclistaService.atualizarCiclista(id, ciclistaAtualizado);

        Assertions.assertNull(result);
    }

    @Test
    void testVerificarPermiteAluguel() {
        boolean result = ciclistaService.verificarPermiteAluguel();

        Assertions.assertTrue(result);
    }

    @Test
    void testVerificarEmail_CasoExista() {
        String email = "joao@example.com";

        String result = ciclistaService.verificarEmailExistente(email);

        Assertions.assertEquals(email, result);
    }

    @Test
    void testObterBicicletaPresaNaTranca() {
        int numeroTranca = 123;
        int bicicletaId = 1;

        Mockito.when(ciclistaRepository.obterBicicletaPresaNaTranca(numeroTranca)).thenReturn(bicicletaId);

        int result = ciclistaService.obterBicicletaPresaNaTranca(numeroTranca);

        Assertions.assertEquals(bicicletaId, result);
    }

    @Test
    void testAlugarBicicleta_CasoCiclistaExistente() {
        int idCiclista = 1;
        int numeroTranca = 123;
        Ciclista ciclista = new Ciclista();
        ciclista.setId(idCiclista);

        Mockito.when(ciclistaRepository.findCiclistaById(idCiclista)).thenReturn(ciclista);
        Mockito.when(ciclistaRepository.obterBicicletaPresaNaTranca(numeroTranca)).thenReturn(null);
        Mockito.when(ciclistaRepository.verificarPermiteAluguelCiclista()).thenReturn(true);

        ResponseEntity<Ciclista> result = ciclistaService.alugarBicicleta(idCiclista, numeroTranca);

        Assertions.assertEquals(ResponseEntity.ok(ciclista), result);
        Assertions.assertEquals(numeroTranca, ciclista.getNumeroTranca());
        Mockito.verify(ciclistaRepository, Mockito.times(0)).enviarCobrancaAdministradora(ciclista);
        Mockito.verify(ciclistaRepository, Mockito.times(0)).alterarStatusBicicleta(ciclista);
    }

    @Test
    void testAlugarBicicleta_CasoCiclistaNaoExista() {
        int idCiclista = 1;
        int numeroTranca = 123;

        Mockito.when(ciclistaRepository.findCiclistaById(idCiclista)).thenReturn(null);

        ResponseEntity<Ciclista> result = ciclistaService.alugarBicicleta(idCiclista, numeroTranca);

        Assertions.assertNull(result);
        Mockito.verify(ciclistaRepository, Mockito.never()).obterBicicletaPresaNaTranca(numeroTranca);
        Mockito.verify(ciclistaRepository, Mockito.never()).verificarPermiteAluguelCiclista();
    }


    @Test
    void testDevolverBicicleta() {
        int idCiclista = 1;
        Ciclista ciclista = new Ciclista();
        ciclista.setId(idCiclista);

        Mockito.when(ciclistaRepository.findCiclistaById(idCiclista)).thenReturn(ciclista);
        Mockito.when(ciclistaRepository.registrarDevolucaoBicicleta(ciclista)).thenReturn(ResponseEntity.ok(ciclista));

        ciclistaService.devolverBicicleta(idCiclista);

        Mockito.verify(ciclistaRepository, Mockito.times(0)).enviarMensagemDevolucaoBicicleta(ciclista);
        Mockito.verify(ciclistaRepository, Mockito.times(0)).registrarDevolucaoBicicleta(ciclista);
    }

    @Test
    void testDevolverBicicleta_CasoCiclistaNaoExista() {
        int idCiclista = 1;

        Mockito.when(ciclistaRepository.findCiclistaById(idCiclista)).thenReturn(null);

        ciclistaService.devolverBicicleta(idCiclista);

        Mockito.verify(ciclistaRepository, Mockito.never()).enviarMensagemDevolucaoBicicleta(Mockito.any());
        Mockito.verify(ciclistaRepository, Mockito.never()).registrarDevolucaoBicicleta(Mockito.any());
    }
}

package com.example.aluguel.controller;

import com.example.echo.aluguel.controller.CiclistaController;
import com.example.echo.aluguel.model.Ciclista;
import com.example.echo.aluguel.model.MeioDePagamento;
import com.example.echo.aluguel.service.CiclistaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CiclistaControllerTest {

    private CiclistaController ciclistaController;

    @Mock
    private CiclistaService ciclistaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ciclistaController = new CiclistaController(ciclistaService);
    }

    @Test
    void testCadastrarCiclista() {

        Ciclista ciclista = new Ciclista();
        when(ciclistaService.cadastrarCiclista(ciclista)).thenReturn(ciclista);

        ResponseEntity<Ciclista> response = ciclistaController.cadastrarCiclista(ciclista);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(ciclista, response.getBody());
        verify(ciclistaService, times(1)).cadastrarCiclista(ciclista);
    }

    @Test
    void testObterCiclistaPorId_Exists() {

        int id = 1;
        Ciclista ciclista = new Ciclista();
        when(ciclistaService.obterCiclistaPorId(id)).thenReturn(ciclista);

        ResponseEntity<Ciclista> response = ciclistaController.obterCiclistaPorId(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ciclista, response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(id);
    }

    @Test
    void testObterCiclistaPorId_CasoNaoExista() {

        int id = 1;
        when(ciclistaService.obterCiclistaPorId(id)).thenReturn(null);

        ResponseEntity<Ciclista> response = ciclistaController.obterCiclistaPorId(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ciclistaService, times(1)).obterCiclistaPorId(id);
    }

    @Test
    void testObterTodosCiclistas() {

        List<Ciclista> ciclistas = new ArrayList<>();
        when(ciclistaService.obterTodosCiclistas()).thenReturn(ciclistas);

        ResponseEntity<List<Ciclista>> response = ciclistaController.obterTodosCiclistas();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ciclistas, response.getBody());
        verify(ciclistaService, times(1)).obterTodosCiclistas();
    }

    @Test
    void testVerificarEmailExistente() {
        String emailExistente = "fulano@example.com";
        String emailNaoExistente = "ciclano@example.com";

        ResponseEntity<String> responseExistente = ciclistaController.verificarEmailExistente(emailExistente);
        ResponseEntity<String> responseNaoExistente = ciclistaController.verificarEmailExistente(emailNaoExistente);

        assertEquals(HttpStatus.OK, responseExistente.getStatusCode());
        assertEquals(HttpStatus.OK, responseNaoExistente.getStatusCode());

        verify(ciclistaService, times(1)).verificarEmailExistente(emailExistente);
        verify(ciclistaService, times(1)).verificarEmailExistente(emailNaoExistente);
    }

    @Test
    void testVerificarPermiteAluguel() {
        int idCiclistaExistente = 1;
        int idCiclistaNaoExistente = 2;

        Ciclista ciclistaExistente = new Ciclista();
        ciclistaExistente.setId(idCiclistaExistente);

        when(ciclistaService.obterCiclistaPorId(idCiclistaExistente)).thenReturn(ciclistaExistente);
        when(ciclistaService.obterCiclistaPorId(idCiclistaNaoExistente)).thenReturn(null);
        when(ciclistaService.verificarPermiteAluguel()).thenReturn(true);

        ResponseEntity<Boolean> responseExistente = ciclistaController.verificarPermiteAluguel(idCiclistaExistente);
        ResponseEntity<Boolean> responseNaoExistente = ciclistaController
                .verificarPermiteAluguel(idCiclistaNaoExistente);

        assertEquals(HttpStatus.OK, responseExistente.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseNaoExistente.getStatusCode());

        assertTrue(responseExistente.getBody());
        assertNull(responseNaoExistente.getBody());

        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclistaExistente);
        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclistaNaoExistente);
        verify(ciclistaService, times(1)).verificarPermiteAluguel();
    }

    @Test
    void testRecuperarCartaoDeCredito() {
        int idCiclista = 1;

        // Criar um objeto CartaoDeCredito
        MeioDePagamento meioDePagamento = new MeioDePagamento();
        meioDePagamento.setNumero("123456789");
        meioDePagamento.setNomeTitular("Fulano de Tal");

        // Criar um objeto Ciclista
        Ciclista ciclista = new Ciclista();
        ciclista.setMeioDePagamento(meioDePagamento);

        // Simular o retorno do ciclista
        when(ciclistaService.obterCiclistaPorId(idCiclista)).thenReturn(ciclista);

        // Chama o controlador
        ResponseEntity<MeioDePagamento> response = ciclistaController.recuperarCartaoDeCredito(idCiclista);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(meioDePagamento, response.getBody());

        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclista);
    }

    @Test
    void testAtualizarCiclista_CasoExista() {
        int id = 1;
        Ciclista ciclistaExistente = new Ciclista();
        ciclistaExistente.setId(id);

        Ciclista ciclistaAtualizado = new Ciclista();
        ciclistaAtualizado.setId(id);
        ciclistaAtualizado.setNome("Novo Nome");

        when(ciclistaService.obterCiclistaPorId(id)).thenReturn(ciclistaExistente);
        when(ciclistaService.atualizarCiclista(id, ciclistaExistente)).thenReturn(ciclistaAtualizado);

        ResponseEntity<Ciclista> response = ciclistaController.atualizarCiclista(id, ciclistaAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ciclistaAtualizado, response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(id);
        verify(ciclistaService, times(1)).atualizarCiclista(id, ciclistaExistente);
    }

    @Test
    void testAtualizarCiclista_CasoNaoExista() {
        int id = 1;
        Ciclista ciclistaAtualizado = new Ciclista();
        ciclistaAtualizado.setId(id);

        when(ciclistaService.obterCiclistaPorId(id)).thenReturn(null);

        ResponseEntity<Ciclista> response = ciclistaController.atualizarCiclista(id, ciclistaAtualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(id);
        verify(ciclistaService, never()).atualizarCiclista(anyInt(), any(Ciclista.class));
    }

    @Test
    void testObterBicicleta_CasoExista() {
        int idCiclista = 1;
        int numeroTranca = 123;
        Ciclista ciclista = new Ciclista();
        ciclista.setId(idCiclista);

        int numeroBicicleta = 456;

        when(ciclistaService.obterCiclistaPorId(idCiclista)).thenReturn(ciclista);
        when(ciclistaService.verificarPermiteAluguelCiclista(1)).thenReturn(true);
        when(ciclistaService.obterBicicletaPresaNaTranca(numeroTranca)).thenReturn(numeroBicicleta);

        ResponseEntity<Integer> response = ciclistaController.obterBicicleta(idCiclista, numeroTranca);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(numeroBicicleta, response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclista);
        verify(ciclistaService, times(1)).verificarPermiteAluguelCiclista(1);
        verify(ciclistaService, times(1)).obterBicicletaPresaNaTranca(numeroTranca);
    }

    @Test
    void testObterBicicleta_CasoCiclistaNaoPermitaAluguel() {
        int idCiclista = 1;
        int numeroTranca = 123;
        Ciclista ciclista = new Ciclista();
        ciclista.setId(idCiclista);

        when(ciclistaService.obterCiclistaPorId(idCiclista)).thenReturn(ciclista);
        when(ciclistaService.verificarPermiteAluguelCiclista(1)).thenReturn(false);

        ResponseEntity<Integer> response = ciclistaController.obterBicicleta(idCiclista, numeroTranca);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclista);
        verify(ciclistaService, times(1)).verificarPermiteAluguelCiclista(1);
        verify(ciclistaService, never()).obterBicicletaPresaNaTranca(numeroTranca);
    }

    @Test
    void testObterBicicleta_CasoNaoBicicletaNaTranca() {
        int idCiclista = 1;
        int numeroTranca = 123;
        Ciclista ciclista = new Ciclista();
        ciclista.setId(idCiclista);

        when(ciclistaService.obterCiclistaPorId(idCiclista)).thenReturn(ciclista);
        when(ciclistaService.verificarPermiteAluguelCiclista(1)).thenReturn(true);
        when(ciclistaService.obterBicicletaPresaNaTranca(numeroTranca)).thenReturn(0);

        ResponseEntity<Integer> response = ciclistaController.obterBicicleta(idCiclista, numeroTranca);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclista);
        verify(ciclistaService, times(1)).verificarPermiteAluguelCiclista(1);
        verify(ciclistaService, times(1)).obterBicicletaPresaNaTranca(numeroTranca);
    }

    @Test
    void testObterBicicleta_CasoCiclistaNaoExista() {
        int idCiclista = 1;
        int numeroTranca = 123;

        when(ciclistaService.obterCiclistaPorId(idCiclista)).thenReturn(null);

        ResponseEntity<Integer> response = ciclistaController.obterBicicleta(idCiclista, numeroTranca);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciclistaService, times(1)).obterCiclistaPorId(idCiclista);
        verify(ciclistaService, never()).verificarPermiteAluguelCiclista(1);
        verify(ciclistaService, never()).obterBicicletaPresaNaTranca(numeroTranca);
    }


}

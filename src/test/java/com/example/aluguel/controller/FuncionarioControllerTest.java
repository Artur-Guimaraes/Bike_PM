package com.example.aluguel.controller;

import com.example.echo.aluguel.controller.FuncionarioController;
import com.example.echo.aluguel.model.Funcionario;
import com.example.echo.aluguel.service.FuncionarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class FuncionarioControllerTest {

    @Mock
    private FuncionarioService funcionarioService;

    private FuncionarioController funcionarioController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        funcionarioController = new FuncionarioController(funcionarioService);
    }

    @Test
    void testObterTodosFuncionarios() {
        List<Funcionario> funcionarios = Arrays.asList(new Funcionario(), new Funcionario());

        Mockito.when(funcionarioService.obterTodosFuncionarios()).thenReturn(funcionarios);

        ResponseEntity<List<Funcionario>> response = funcionarioController.obterTodosFuncionarios();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(funcionarios, response.getBody());
    }

    @Test
    void testCadastrarFuncionario() {
        Funcionario novoFuncionario = new Funcionario();

        Mockito.when(funcionarioService.cadastrarFuncionario(Mockito.any(Funcionario.class))).thenReturn(novoFuncionario);

        ResponseEntity<Funcionario> response = funcionarioController.cadastrarFuncionario(novoFuncionario);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(novoFuncionario, response.getBody());
    }

    @Test
    void testObterFuncionarioPorId_CasoExista() {
        int idFuncionario = 1;
        Funcionario funcionarioExistente = new Funcionario();

        Mockito.when(funcionarioService.obterFuncionarioPorId(idFuncionario)).thenReturn(funcionarioExistente);

        ResponseEntity<Funcionario> response = funcionarioController.obterFuncionarioPorId(idFuncionario);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(funcionarioExistente, response.getBody());
    }

    @Test
    void testObterFuncionarioPorId_CasoNaoExista() {
        int idFuncionario = 1;

        Mockito.when(funcionarioService.obterFuncionarioPorId(idFuncionario)).thenReturn(null);

        ResponseEntity<Funcionario> response = funcionarioController.obterFuncionarioPorId(idFuncionario);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void testAtualizarFuncionario_CasoExista() {
        int idFuncionario = 1;
        Funcionario funcionarioExistente = new Funcionario();
        Funcionario funcionarioAtualizado = new Funcionario();

        Mockito.when(funcionarioService.obterFuncionarioPorId(idFuncionario)).thenReturn(funcionarioExistente);
        Mockito.when(funcionarioService.atualizarFuncionario(idFuncionario, funcionarioExistente)).thenReturn(funcionarioAtualizado);

        ResponseEntity<Funcionario> response = funcionarioController.atualizarFuncionario(idFuncionario, funcionarioExistente);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(funcionarioAtualizado, response.getBody());
    }

    @Test
    void testAtualizarFuncionario_CasoNaoExista() {
        int idFuncionario = 1;
        Funcionario funcionarioExistente = null;

        Mockito.when(funcionarioService.obterFuncionarioPorId(idFuncionario)).thenReturn(funcionarioExistente);

        ResponseEntity<Funcionario> response = funcionarioController.atualizarFuncionario(idFuncionario, new Funcionario());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void testRemoverFuncionario_CasoExista() {
        int idFuncionario = 1;

        Mockito.when(funcionarioService.removerFuncionario(idFuncionario)).thenReturn(true);

        ResponseEntity<Void> response = funcionarioController.removerFuncionario(idFuncionario);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoverFuncionario_CasoNaoExista() {
        int idFuncionario = 1;

        Mockito.when(funcionarioService.removerFuncionario(idFuncionario)).thenReturn(false);

        ResponseEntity<Void> response = funcionarioController.removerFuncionario(idFuncionario);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

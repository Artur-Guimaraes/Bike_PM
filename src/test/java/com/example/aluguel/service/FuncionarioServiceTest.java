package com.example.aluguel.service;

import com.example.echo.aluguel.model.Funcionario;
import com.example.echo.aluguel.repository.FuncionarioRepository;
import com.example.echo.aluguel.service.FuncionarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class FuncionarioServiceTest {

    private FuncionarioService funcionarioService;
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        funcionarioRepository = Mockito.mock(FuncionarioRepository.class);
        funcionarioService = new FuncionarioService(funcionarioRepository);
    }

    @Test
    void testObterTodosFuncionarios() {
        Funcionario funcionarioEsperado1 = new Funcionario();
        funcionarioEsperado1.setId(1);
        funcionarioEsperado1.setNome("Dimitri");

        Funcionario funcionarioEsperado2 = new Funcionario();
        funcionarioEsperado2.setId(2);
        funcionarioEsperado2.setNome("Raquel");

        List<Funcionario> funcionariosEsperados = new ArrayList<>();
        funcionariosEsperados.add(new Funcionario());
        funcionariosEsperados.add(new Funcionario());

        Mockito.when(funcionarioRepository.findAllFuncionarios()).thenReturn(funcionariosEsperados);

        List<Funcionario> funcionarios = funcionarioService.obterTodosFuncionarios();

        Assertions.assertEquals(funcionariosEsperados, funcionarios);
    }

    @Test
    void testCadastrarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Roberta");

        Mockito.when(funcionarioRepository.saveFuncionario(funcionario)).thenReturn(funcionario);

        Funcionario novoFuncionario = funcionarioService.cadastrarFuncionario(funcionario);

        Assertions.assertEquals(funcionario, novoFuncionario);
    }

    @Test
    void testObterFuncionarioPorId_CasoIdExista() {
        Funcionario funcionarioTeste = new Funcionario();
        funcionarioTeste.setId(3);
        funcionarioTeste.setNome("Raquel");

        Mockito.when(funcionarioRepository.findFuncionarioById(3)).thenReturn(funcionarioTeste);

        Funcionario funcionario = funcionarioService.obterFuncionarioPorId(3);

        Assertions.assertEquals(funcionarioTeste, funcionario);
    }

    @Test
    void testObterFuncionarioPorId_CasoIdNaoExista() {
        Mockito.when(funcionarioRepository.findFuncionarioById(1)).thenReturn(null);

        Funcionario funcionario = funcionarioService.obterFuncionarioPorId(1);

        Assertions.assertNull(funcionario);
    }

    @Test
    void testAtualizarFuncionario_CasoIdExista() {
        Funcionario funcionarioExistente = new Funcionario();
        funcionarioExistente.setId(1);
        funcionarioExistente.setNome("Gabriel");

        Funcionario funcionarioAtualizado = new Funcionario();
        funcionarioAtualizado.setId(2);
        funcionarioAtualizado.setNome("Fernanda");

        Mockito.when(funcionarioRepository.findFuncionarioById(1)).thenReturn(funcionarioExistente);
        Mockito.when(funcionarioRepository.saveFuncionario(funcionarioAtualizado)).thenReturn(funcionarioAtualizado);

        Funcionario funcionario = funcionarioService.atualizarFuncionario(1, funcionarioAtualizado);

        Assertions.assertEquals(funcionarioAtualizado, funcionario);
    }

    @Test
    void testAtualizarFuncionario_CasoIdNaoExista() {
        Mockito.when(funcionarioRepository.findFuncionarioById(1)).thenReturn(null);
        Funcionario funcionarioAtualizado = new Funcionario();
        funcionarioAtualizado.setId(1);
        funcionarioAtualizado.setNome("Bruna");

        Funcionario funcionario = funcionarioService.atualizarFuncionario(1, new Funcionario());

        Assertions.assertNull(funcionario);
    }

    @Test
    void testRemoverFuncionario_CasoIdExista() {
        Mockito.when(funcionarioRepository.deleteFuncionario(1)).thenReturn(true);

        boolean removido = funcionarioService.removerFuncionario(1);

        Assertions.assertTrue(removido);
    }

    @Test
    void testRemoverFuncionario_CasoIdNaoExista() {
        Mockito.when(funcionarioRepository.deleteFuncionario(1)).thenReturn(false);

        boolean removido = funcionarioService.removerFuncionario(1);

        Assertions.assertFalse(removido);
    }
}

package com.example.aluguel.repository;

import com.example.echo.aluguel.model.Funcionario;
import com.example.echo.aluguel.repository.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FuncionarioRepositoryTest {

    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    public void setUp() {
        funcionarioRepository = new FuncionarioRepository();
    }

    @Test
    void testSaveFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Fulano");

        Funcionario savedFuncionario = funcionarioRepository.saveFuncionario(funcionario);

        Assertions.assertNotNull(savedFuncionario);
        Assertions.assertEquals(1, savedFuncionario.getId());
        Assertions.assertEquals("Fulano", savedFuncionario.getNome());
    }

    @Test
    void testSaveFuncionario_GerandoId() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Ciclano");

        Funcionario savedFuncionario = funcionarioRepository.saveFuncionario(funcionario);

        Assertions.assertNotNull(savedFuncionario);
        Assertions.assertNotEquals(0, savedFuncionario.getId());
        Assertions.assertEquals("Ciclano", savedFuncionario.getNome());
    }

    @Test
    void testFindFuncionarioById_CasoIdExista() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1);
        funcionario1.setNome("Artur");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(2);
        funcionario2.setNome("Renan");

        funcionarioRepository.saveFuncionario(funcionario1);
        funcionarioRepository.saveFuncionario(funcionario2);

        Funcionario foundFuncionario = funcionarioRepository.findFuncionarioById(2);

        Assertions.assertNotNull(foundFuncionario);
        Assertions.assertEquals(2, foundFuncionario.getId());
        Assertions.assertEquals("Renan", foundFuncionario.getNome());
    }

    @Test
    void testFindFuncionarioById_CasoIdNaoExista() {
        Funcionario funcionario = funcionarioRepository.findFuncionarioById(1);

        Assertions.assertNull(funcionario);
    }

    @Test
    void testFindAllFuncionarios() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1);
        funcionario1.setNome("Joana");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(2);
        funcionario2.setNome("Maria");
        funcionarioRepository.saveFuncionario(funcionario1);
        funcionarioRepository.saveFuncionario(funcionario2);

        List<Funcionario> funcionarios = funcionarioRepository.findAllFuncionarios();

        Assertions.assertEquals(2, funcionarios.size());
        Assertions.assertTrue(funcionarios.contains(funcionario1));
        Assertions.assertTrue(funcionarios.contains(funcionario2));
    }

    @Test
    void testDeleteFuncionario_CasoIdExista() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Carol");
        funcionarioRepository.saveFuncionario(funcionario);

        boolean deleted = funcionarioRepository.deleteFuncionario(1);

        Assertions.assertTrue(deleted);
        Assertions.assertNull(funcionarioRepository.findFuncionarioById(1));
    }

    @Test
    void testDeleteFuncionario_CasoIdNaoExista() {
        boolean deleted = funcionarioRepository.deleteFuncionario(1);

        Assertions.assertFalse(deleted);
    }
}

package com.example.aluguel.Model;

import com.example.echo.aluguel.model.Funcionario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
    }

    @Test
    void testSetId() {
        funcionario.setId(1);
        Assertions.assertEquals(1, funcionario.getId());
    }

    @Test
    void testSetName() {
        funcionario.setNome("Fulano de tal");
        Assertions.assertEquals("Fulano de tal", funcionario.getNome());
    }

    @Test
    void testSetIdade() {
        funcionario.setIdade(30);
        Assertions.assertEquals(30, funcionario.getIdade());
    }

    @Test
    void testSetFuncao() {
        funcionario.setFuncao("Mecanico");
        Assertions.assertEquals("Mecanico", funcionario.getFuncao());
    }

    @Test
    void testSetCpf() {
        funcionario.setCpf("123456789");
        Assertions.assertEquals("123456789", funcionario.getCpf());
    }

    @Test
    void testSetEmail() {
        funcionario.setEmail("john.doe@example.com");
        Assertions.assertEquals("john.doe@example.com", funcionario.getEmail());
    }

    @Test
    void testSetSenha() {
        funcionario.setSenha("password");
        Assertions.assertEquals("password", funcionario.getSenha());
    }

    @Test
    void testSetConfirmaSenha() {
        //Se não for nulo nem vazio
        String confirmaSenha = "password";
        String result = funcionario.setConfirmaSenha(confirmaSenha);
        Assertions.assertEquals(confirmaSenha, result);
        Assertions.assertEquals(confirmaSenha, funcionario.getConfirmaSenha());

        //Se for nulo
        confirmaSenha = null;
        result = funcionario.setConfirmaSenha(confirmaSenha);
        Assertions.assertNull(result);
        Assertions.assertNull(funcionario.getConfirmaSenha());

        //Se for vazio
        confirmaSenha = "";
        result = funcionario.setConfirmaSenha(confirmaSenha);
        Assertions.assertNull(result);
        Assertions.assertNull(funcionario.getConfirmaSenha());
    }

}

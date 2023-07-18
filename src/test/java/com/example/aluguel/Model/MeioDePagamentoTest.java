package com.example.aluguel.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.echo.aluguel.model.MeioDePagamento;

class MeioDePagamentoTest {

    @Test
    void testGetNomeTitular() {
        String nomeTitular = "Fulano de Tal";
        MeioDePagamento meioDePagamento = new MeioDePagamento();

        meioDePagamento.setNomeTitular(nomeTitular);

        String resultado = meioDePagamento.getNomeTitular();

        Assertions.assertEquals(nomeTitular, resultado);
    }

    @Test
    void testGetNumero() {
        String numero = "123456789";
        MeioDePagamento meioDePagamento = new MeioDePagamento();
        meioDePagamento.setNumero(numero);

        String resultado = meioDePagamento.getNumero();

        Assertions.assertEquals(numero, resultado);
    }

    @Test
    void testGetValidade() {
        String validade = "12/24";
        MeioDePagamento meioDePagamento = new MeioDePagamento();
        meioDePagamento.setValidade(validade);

        String resultado = meioDePagamento.getValidade();

        Assertions.assertEquals(validade, resultado);
    }

    @Test
    void testGetCvv() {
        String cvv = "123";
        MeioDePagamento meioDePagamento = new MeioDePagamento();
        meioDePagamento.setCvv(cvv);

        String resultado = meioDePagamento.getCvv();

        Assertions.assertEquals(cvv, resultado);
    }


}

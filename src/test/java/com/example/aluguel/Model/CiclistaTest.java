package com.example.aluguel.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.echo.aluguel.model.Ciclista;
import com.example.echo.aluguel.model.MeioDePagamento;
import com.example.echo.aluguel.model.Passaporte;

class CiclistaTest {

    @Test
    void testGetId() {
        Ciclista ciclista = new Ciclista();

        int id = ciclista.getId();

        Assertions.assertEquals(0, id);
    }

    @Test
    void testSetNome() {
        Ciclista ciclista = new Ciclista();

        ciclista.setNome("Fulano de tal");

        Assertions.assertEquals("Fulano de tal", ciclista.getNome());
    }

    @Test
    void testSetEmail() {
        Ciclista ciclista = new Ciclista();

        ciclista.setEmail("fulanoDeTal@example.com");

        Assertions.assertEquals("fulanoDeTal@example.com", ciclista.getEmail());
    }

    @Test
    void testSetPassaporte() {
        Ciclista ciclista = new Ciclista();
        Passaporte passaporte = new Passaporte();

        ciclista.setPassaporte(passaporte);

        Assertions.assertEquals(passaporte, ciclista.getPassaporte());
    }

    @Test
    void testMeioDePagamento() {
        Ciclista ciclista = new Ciclista();
        MeioDePagamento pagamento = new MeioDePagamento();

        ciclista.setMeioDePagamento(pagamento);

        Assertions.assertEquals(pagamento, ciclista.getMeioDePagamento());
    }

    @Test
    void setNacionalidade() {
        Ciclista ciclista = new Ciclista();

        ciclista.setNacionalidade("Brasileiro");

        Assertions.assertEquals("Brasileiro", ciclista.getNacionalidade());
    }

    @Test
    void setNascimento() {
        Ciclista ciclista = new Ciclista();

        ciclista.setNascimento("2001-02-27");

        Assertions.assertEquals("2001-02-27", ciclista.getNascimento());
    }

    @Test
    void setSenha() {
        Ciclista ciclista = new Ciclista();

        ciclista.setSenha("senha de teste1");

        Assertions.assertEquals("senha de teste1", ciclista.getSenha());
    }

    @Test
    void seturlFotoDocumento() {
        Ciclista ciclista = new Ciclista();

        ciclista.seturlFotoDocumento("www.teste.com");

        Assertions.assertEquals("www.teste.com", ciclista.geturlFotoDocumento());
    }

    @Test
    void testGetNumeroTranca() {
        Ciclista ciclista = new Ciclista();
        ciclista.setNumeroTranca(123);
        Assertions.assertEquals(123, ciclista.getNumeroTranca());
    }

    @Test
    void testSetNumeroTranca() {
        Ciclista ciclista = new Ciclista();
        ciclista.setNumeroTranca(123);
        Assertions.assertEquals(123, ciclista.getNumeroTranca());
    }

    @Test
    void testGetNumeroBicicleta() {
        Ciclista ciclista = new Ciclista();
        ciclista.setNumeroBicicleta(456);
        Assertions.assertEquals(456, ciclista.getNumeroBicicleta());
    }

    @Test
    void testSetNumeroBicicleta() {
        Ciclista ciclista = new Ciclista();
        ciclista.setNumeroBicicleta(456);
        Assertions.assertEquals(456, ciclista.getNumeroBicicleta());
    }

    
}

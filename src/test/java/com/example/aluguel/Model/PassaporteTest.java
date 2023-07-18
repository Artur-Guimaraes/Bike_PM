package com.example.aluguel.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.echo.aluguel.model.Passaporte;

class PassaporteTest {

    @Test
    void testSetNumero() {
        Passaporte passaporte = new Passaporte();

        passaporte.setNumero("XYZ789");

        Assertions.assertEquals("XYZ789", passaporte.getNumero());
    }

    @Test
    void testSetPais() {
        Passaporte passaporte = new Passaporte();

        passaporte.setPais("US");

        Assertions.assertEquals("US", passaporte.getPais());
    }

    @Test
    void testSetValidade() {
        Passaporte passaporte = new Passaporte();

        passaporte.setValidade("2025-06-30");

        Assertions.assertEquals("2025-06-30", passaporte.getValidade());
    }
}

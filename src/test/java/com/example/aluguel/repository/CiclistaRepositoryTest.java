package com.example.aluguel.repository;

import com.example.echo.aluguel.model.Ciclista;
import com.example.echo.aluguel.repository.CiclistaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CiclistaRepositoryTest {

    private CiclistaRepository ciclistaRepository;

    @BeforeEach
    void setup() {
        ciclistaRepository = new CiclistaRepository();
    }

    @Test
    void testSaveCiclista() {
        // Arrange
        Ciclista ciclista = new Ciclista();
        ciclista.setId(1);

        // Act
        Ciclista savedCiclista = ciclistaRepository.saveCiclista(ciclista);

        // Assert
        Assertions.assertEquals(ciclista, savedCiclista);
    }

    @Test
    void testFindCiclistaById_NotExists() {
        // Act
        Ciclista foundCiclista = ciclistaRepository.findCiclistaById(1);

        // Assert
        Assertions.assertNull(foundCiclista);
    }

    @Test
    void testFindAllCiclistas() {
        // Arrange
        Ciclista ciclista1 = new Ciclista();
        ciclista1.setId(1);
        Ciclista ciclista2 = new Ciclista();
        ciclista2.setId(2);
        ciclistaRepository.saveCiclista(ciclista1);
        ciclistaRepository.saveCiclista(ciclista2);

        // Act
        List<Ciclista> ciclistas = ciclistaRepository.findAllCiclistas();

        // Assert
        Assertions.assertEquals(2, ciclistas.size());
        Assertions.assertTrue(ciclistas.contains(ciclista1));
        Assertions.assertTrue(ciclistas.contains(ciclista2));
    }

    @Test
    void testSetId() {
        // Arrange
        Ciclista ciclista = new Ciclista();
        int id = 0;
        // Act
        ciclistaRepository.setId(ciclista, id);

        // Assert
        Assertions.assertEquals(id, ciclista.getId());
    }
}

package com.example.echo.aluguel.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.echo.aluguel.model.Ciclista;
import org.springframework.stereotype.Component;

@Component
public class CiclistaRepository {
    // Armazena os ciclistas usando Map onde a chave é o ID do ciclista
    private final Map<Integer, Ciclista> ciclistas = new HashMap<>();

    private static int lastId = 0;

    // Salva um novo ciclista no Map e retorna o ciclista salvo
    public Ciclista saveCiclista(Ciclista ciclista) {
        lastId++;
        ciclista.setId(lastId);
        ciclistas.put(lastId, ciclista);
        return ciclista;
    }

    public Ciclista atualizarSaveCiclista(Ciclista ciclista) {
        ciclistas.put(ciclista.getId(), ciclista);
        return ciclista;
    }

    // Busca ciclista por ID e o retorna caso exista. Retorna null caso contrário
    public Ciclista findCiclistaById(int idCiclista) {
        return ciclistas.get(idCiclista);
    }

    // Busca e retorna todos os cilcistas salvos
    public List<Ciclista> findAllCiclistas() {
        return new ArrayList<>(ciclistas.values());
    }

    // "Seta" o ID de um ciclista
    public void setId(Ciclista ciclista, int id) {
        ciclista.setId(id);
    }

    public Object obterBicicletaPresaNaTranca(int numeroTranca) {
        if (numeroTranca == 1) {
            return "Bicicleta 1";
        } else if (numeroTranca == 2) {
            return "Bicicleta 2";
        } else {
            return null;
        }
    }

    public Object verificarPermiteAluguelCiclista() {
        return null;
    }

    public Ciclista enviarCobrancaAdministradora(Ciclista ciclista) {
        return ciclista;
    }

    public Ciclista alterarStatusBicicleta(Ciclista ciclista) {
        return ciclista;
    }

    public Object registrarDevolucaoBicicleta(Ciclista ciclista) {
        return ciclista;
    }

    public Object enviarMensagemDevolucaoBicicleta(Ciclista ciclista) {
        return ciclista;
    }
}

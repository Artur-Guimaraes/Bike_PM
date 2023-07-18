package com.example.echo.aluguel.repository;

import com.example.echo.aluguel.model.Aluguel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AluguelRepository {

    private final Map<Long, Aluguel> alugueis = new HashMap<>();
    private int nextId = 1;

    public Aluguel salvarAluguel(Aluguel aluguel) {
        aluguel.setId(nextId++);
        alugueis.put(aluguel.getId(), aluguel);
        return aluguel;
    }

    public Aluguel obterAluguelPorId(long id) {
        return alugueis.get(id);
    }

    public List<Aluguel> obterTodosAlugueis() {
        return new ArrayList<>(alugueis.values());
    }


}

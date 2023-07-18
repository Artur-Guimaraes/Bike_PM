package com.example.echo.aluguel.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.echo.aluguel.model.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioRepository {

    private final Map<Integer, Funcionario> funcionarios = new HashMap<>();
    private int nextId = 1;

    public Funcionario saveFuncionario(Funcionario funcionario) {
        if (funcionario.getId() == 0) {
            funcionario.setId(nextId++);
        }
        funcionarios.put(funcionario.getId(), funcionario);
        return funcionario;
    }

    public Funcionario findFuncionarioById(int idFuncionario) {
        return funcionarios.get(idFuncionario);
    }

    public List<Funcionario> findAllFuncionarios() {
        return new ArrayList<>(funcionarios.values());
    }

    public boolean deleteFuncionario(int idFuncionario) {
        return funcionarios.remove(idFuncionario) != null;
    }
}


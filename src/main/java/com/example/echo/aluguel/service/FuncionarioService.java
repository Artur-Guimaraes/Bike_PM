package com.example.echo.aluguel.service;

import com.example.echo.aluguel.model.Funcionario;
import com.example.echo.aluguel.repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> obterTodosFuncionarios() {
        return funcionarioRepository.findAllFuncionarios();
    }

    public Funcionario cadastrarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.saveFuncionario(funcionario);
    }

    public Funcionario obterFuncionarioPorId(int idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findFuncionarioById(idFuncionario);

        if (funcionario != null) {
            return funcionario;
        } else {
            return null;
        }
    }

    public Funcionario atualizarFuncionario(int idFuncionario, Funcionario funcionario) {
       Funcionario funcionarioExistente = funcionarioRepository.findFuncionarioById(idFuncionario);

       if (funcionarioExistente != null) {
           funcionario.setId(idFuncionario);
            return funcionarioRepository.saveFuncionario(funcionario);
        } else {
            return null;
        }
    }

    public boolean removerFuncionario(int idFuncionario) {
        return funcionarioRepository.deleteFuncionario(idFuncionario);
    }
}


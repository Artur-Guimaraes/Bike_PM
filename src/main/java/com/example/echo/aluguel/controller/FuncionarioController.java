package com.example.echo.aluguel.controller;

import com.example.echo.aluguel.model.Funcionario;
import com.example.echo.aluguel.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> obterTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.obterTodosFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @PostMapping
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario novoFuncionario = funcionarioService.cadastrarFuncionario(funcionario);
        return ResponseEntity.ok(novoFuncionario);
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> obterFuncionarioPorId(@PathVariable("idFuncionario") int idFuncionario) {
        Funcionario funcionario = funcionarioService.obterFuncionarioPorId(idFuncionario);
        if (funcionario != null) {
            return ResponseEntity.ok(funcionario);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable("idFuncionario") int idFuncionario, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioService.obterFuncionarioPorId(idFuncionario);

        if (funcionarioExistente != null) {
            if (funcionario.getIdade() != -1) {
                funcionarioExistente.setIdade(funcionario.getIdade());
            }
            if (funcionario.getFuncao() != null) {
                funcionarioExistente.setFuncao(funcionario.getFuncao());
            }
            if (funcionario.getConfirmaSenha() != null) {
                funcionarioExistente.setConfirmaSenha(funcionario.getConfirmaSenha());
            }
            if (funcionario.getCpf() != null) {
                funcionarioExistente.setCpf(funcionario.getCpf());
            }

            Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(idFuncionario, funcionarioExistente);

            if (funcionarioAtualizado != null) {
                return ResponseEntity.ok(funcionarioAtualizado);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable("idFuncionario") int idFuncionario) {
        boolean removido = funcionarioService.removerFuncionario(idFuncionario);
        if (removido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


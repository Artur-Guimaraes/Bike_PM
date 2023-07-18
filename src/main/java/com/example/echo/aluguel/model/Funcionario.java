package com.example.echo.aluguel.model;

public class Funcionario{

    private int id;
    private String nome;
    private String senha;
    private String email;
    private int idade;
    private String funcao;
    private String cpf;
    private String confirmaSenha;

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String setConfirmaSenha(String confirmaSenha) {
        if (confirmaSenha == null || confirmaSenha.isEmpty()) {
            this.confirmaSenha = null;
            return null;
        } else {
            this.confirmaSenha = confirmaSenha;
            return confirmaSenha;
        }
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

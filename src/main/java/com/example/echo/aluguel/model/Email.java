package com.example.echo.aluguel.model;

public class Email {
    private String enderecoEmail;
    private String assunto;
    private String mensagem;

    public Email(String enderecoEmail, String assunto, String mensagem) {
        this.enderecoEmail = enderecoEmail;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public String getEnderecoEmail() {
        return enderecoEmail;
    }

    public void setEnderecoEmail(String enderecoEmail) {
        this.enderecoEmail = enderecoEmail;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

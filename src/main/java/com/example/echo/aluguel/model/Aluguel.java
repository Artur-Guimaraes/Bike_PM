package com.example.echo.aluguel.model;

import java.time.LocalDateTime;

public class Aluguel {
    private int id;
    private int bicicleta;
    private LocalDateTime horaInicio;
    private int trancaFim;
    private LocalDateTime horaFim;
    private double cobranca;
    private int ciclista;
    private int trancaInicio;

    public int getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(int bicicleta) {
        this.bicicleta = bicicleta;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getTrancaFim() {
        return trancaFim;
    }

    public void setTrancaFim(int trancaFim) {
        this.trancaFim = trancaFim;
    }

    public LocalDateTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    public double getCobranca() {
        return cobranca;
    }

    public void setCobranca(double cobranca) {
        this.cobranca = cobranca;
    }

    public int getCiclista() {
        return ciclista;
    }

    public void setCiclista(int ciclista) {
        this.ciclista = ciclista;
    }

    public int getTrancaInicio() {
        return trancaInicio;
    }

    public void setTrancaInicio(int trancaInicio) {
        this.trancaInicio = trancaInicio;
    }

    public long getId() { return id; }

    public void setId(int id) { this.id = id; }
}

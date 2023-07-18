package com.example.echo.aluguel.service;

import com.example.echo.aluguel.model.Ciclista;
import com.example.echo.aluguel.repository.AluguelRepository;
import com.example.echo.aluguel.repository.CiclistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CiclistaService {

    private final CiclistaRepository ciclistaRepository;
    private final AluguelRepository aluguelRepository;

    @Autowired
    public CiclistaService(CiclistaRepository ciclistaRepository, AluguelRepository aluguelRepository) {
        this.ciclistaRepository = ciclistaRepository;
        this.aluguelRepository = aluguelRepository;
    }

    public Ciclista cadastrarCiclista(Ciclista ciclista) {
        return ciclistaRepository.saveCiclista(ciclista);
    }

    public Ciclista obterCiclistaPorId(int id) {
        return ciclistaRepository.findCiclistaById(id);
    }

    // Chama o método de obter todos os ciclistas
    public List<Ciclista> obterTodosCiclistas() {
        return ciclistaRepository.findAllCiclistas();
    }

    //
    public Ciclista atualizarCiclista(int id, Ciclista ciclista) {
        Ciclista ciclistaExistente = ciclistaRepository.findCiclistaById(id);

        if (ciclistaExistente != null) {
            ciclista.setId(id);
            return ciclistaRepository.atualizarSaveCiclista(ciclista);
        } else {
            return null;
        }

    }

    public boolean verificarPermiteAluguel() {
        return true;
    }

    public boolean verificarPermiteAluguelCiclista(int id) {
        AtomicBoolean retorno = new AtomicBoolean(true);
        aluguelRepository.obterTodosAlugueis().forEach(aluguel -> {
            if (aluguel.getCiclista() == id) retorno.set(false);
        });
        return retorno.get();
    }

    public String verificarEmailExistente(String email) {
        return email;
    }


    public int obterBicicletaPresaNaTranca(int numeroTranca) {
        if (numeroTranca != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public ResponseEntity<Ciclista> alugarBicicleta(int idCiclista, int numeroTranca) {
        Ciclista ciclista = obterCiclistaPorId(idCiclista);

        if (ciclista != null) {
            // Solicita o número da tranca.
            ciclista.setNumeroTranca(numeroTranca);

            // Caso já tenha um aluguel (não vai acontecer por enquanto)
            if (!verificarPermiteAluguelCiclista(idCiclista)) {
                return ResponseEntity.ok(ciclista);
            }

            // Envia a cobrança para a Administradora e atualiza status da bicicleta.
            enviarCobrancaAdministradora();
            alterarStatusBicicleta();
            return ResponseEntity.ok(ciclista);

        }
        return null;
    }

    private boolean alterarStatusBicicleta() {
        return true;
    }

    private boolean enviarCobrancaAdministradora() {
        return true;
    }

    // Devolver Bicicleta
    public void devolverBicicleta(int idCiclista) {
        Ciclista ciclista = ciclistaRepository.findCiclistaById(idCiclista);

        // Calcula o valor a pagar

        // Registra os dados da devolução da bicicleta

        // muda status de alugada para false

        // Envia uma mensagem para o ciclista informando os dados da devolução da bicicleta
        enviarMensagemDevolucaoBicicleta(ciclista);
        registrarDevolucaoBicicleta(ciclista);
    }

    private ResponseEntity<Ciclista> enviarMensagemDevolucaoBicicleta(Ciclista ciclista) {
        return ResponseEntity.ok(ciclista);
    }

    private ResponseEntity<Ciclista> registrarDevolucaoBicicleta(Ciclista ciclista) {
        return ResponseEntity.ok(ciclista);
    }
}

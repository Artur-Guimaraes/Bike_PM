package com.example.echo.aluguel.controller;

import com.example.echo.aluguel.model.Ciclista;
import com.example.echo.aluguel.model.MeioDePagamento;
import com.example.echo.aluguel.service.CiclistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ciclista")
public class CiclistaController {

    private final CiclistaService ciclistaService;

    @Autowired
    public CiclistaController(CiclistaService ciclistaService) {
        this.ciclistaService = ciclistaService;
    }

    // ** Endpoint de Cadastro de novo Ciclista **
    @PostMapping
    public ResponseEntity<Ciclista> cadastrarCiclista(@RequestBody Ciclista ciclista) {
        Ciclista novoCiclista = ciclistaService.cadastrarCiclista(ciclista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCiclista);
    }

    // ** Endpoint para obter Ciclista por ID **
    @GetMapping("/{id}")
    public ResponseEntity<Ciclista> obterCiclistaPorId(@PathVariable("id") int id) {
        Ciclista ciclista = ciclistaService.obterCiclistaPorId(id);
        if (ciclista != null) {
            return ResponseEntity.ok(ciclista);
        }
        return ResponseEntity.notFound().build();
    }

    // ** Endpoint para obter todos os Ciclistas **
    @GetMapping
    public ResponseEntity<List<Ciclista>> obterTodosCiclistas() {
        List<Ciclista> ciclistas = ciclistaService.obterTodosCiclistas();
        return ResponseEntity.ok(ciclistas);
    }

    // ** Endpoint atualizar os dados de um Ciclista -> atualiza todos os atributos
    // da classe Ciclista **
    @PutMapping("/{id}")
    public ResponseEntity<Ciclista> atualizarCiclista(@PathVariable("id") int id, @RequestBody Ciclista ciclista) {
        Ciclista ciclistaExistente = ciclistaService.obterCiclistaPorId(id);

        if (ciclistaExistente != null) {

            ciclistaExistente.setEmail(ciclista.getEmail());
            ciclistaExistente.setNacionalidade(ciclista.getNacionalidade());
            ciclistaExistente.setNascimento(ciclista.getNascimento());
            ciclistaExistente.setNome(ciclista.getNome());
            ciclistaExistente.setSenha(ciclista.getSenha());
            ciclistaExistente.setPassaporte(ciclista.getPassaporte());
            ciclistaExistente.setMeioDePagamento(ciclista.getMeioDePagamento());
            ciclistaExistente.seturlFotoDocumento(ciclista.geturlFotoDocumento());

            // Parte adicionada para simular comportamento - Caso de uso 3 e 4

            Ciclista ciclistaAtualizado = ciclistaService.atualizarCiclista(id, ciclistaExistente);

            if (ciclistaAtualizado != null) {
                return ResponseEntity.ok(ciclistaAtualizado);
            } else {
                return ResponseEntity.badRequest().build();
            }

        }

        return ResponseEntity.notFound().build();

    }

    // ** Endpoint para verificar se um Ciclista pode alugar uma bicicleta **
    @GetMapping("/{idCiclista}/permiteAluguel")
    public ResponseEntity<Boolean> verificarPermiteAluguel(@PathVariable("idCiclista") int idCiclista) {
        Ciclista ciclista = ciclistaService.obterCiclistaPorId(idCiclista);
        if (ciclista != null) {
            boolean permiteAluguel = ciclistaService.verificarPermiteAluguel();
            return ResponseEntity.ok(permiteAluguel);
        }
        return ResponseEntity.notFound().build();
    }

    // ** Endpoint para verificar se email já existe para aquele ciclista -> será
    // utilizado como verificação para cadastrar novo ciclista **
    @GetMapping("/ciclista/existeEmail/{email}")
    public ResponseEntity<String> verificarEmailExistente(@PathVariable("email") String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String existeEmail = ciclistaService.verificarEmailExistente(email);
        return ResponseEntity.ok(existeEmail);
    }

    // ** Endpoint para recuperar os dados do Cartão (meio de pagamento) de um
    // Ciclista **
    @GetMapping("/cartaoDeCredito/{idCiclista}")
    public ResponseEntity<MeioDePagamento> recuperarCartaoDeCredito(@PathVariable("idCiclista") int idCiclista) {

        Ciclista ciclista = ciclistaService.obterCiclistaPorId(idCiclista);
        if (ciclista == null) {
            return ResponseEntity.notFound().build();
        }

        MeioDePagamento cartaoDeCredito = ciclista.getMeioDePagamento();

        if (cartaoDeCredito == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cartaoDeCredito);
    }

    // ** Endpoint temporário para caso de uso 3 e 4, obtendo bicicleta pela tranca
    // **//
    @GetMapping("/{idCiclista}/obterBicicleta/{numeroTranca}")
    public ResponseEntity<Integer> obterBicicleta(@PathVariable("idCiclista") int idCiclista,
            @PathVariable("numeroTranca") int numeroTranca) {
        Ciclista ciclista = ciclistaService.obterCiclistaPorId(idCiclista);
        if (ciclista != null) {
            // Verificar se pode pegar a bicicleta
            boolean permiteAluguel = ciclistaService.verificarPermiteAluguelCiclista(idCiclista);
            if (permiteAluguel) {
                // Verificar se tem bicicleta presa na tranca
                int numeroBicicleta = ciclistaService.obterBicicletaPresaNaTranca(numeroTranca);
                if (numeroBicicleta != 0) {
                    return ResponseEntity.ok(numeroBicicleta);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // ** Endpoint temporário para caso de uso 3 e 4, alugando bicicleta
    // **//
    @PostMapping("/{idCiclista}/alugar")
    public ResponseEntity<String> alugarBicicleta(@PathVariable int idCiclista, @RequestParam int numeroTranca) {
        ciclistaService.alugarBicicleta(idCiclista, numeroTranca);
        return ResponseEntity.ok("Bicicleta alugada com sucesso!");
    }

    // ** Endpoint temporário para caso de uso 3 e 4, devolvendo bicicleta
    // **//
    @PostMapping("/{idCiclista}/devolver")
    public ResponseEntity<String> devolverBicicleta(@PathVariable int idCiclista) {
        ciclistaService.devolverBicicleta(idCiclista);
        return ResponseEntity.ok("Bicicleta devolvida com sucesso!");
    }

}

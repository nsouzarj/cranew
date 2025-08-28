package br.adv.cra.controller;

import br.adv.cra.entity.Correspondente;
import br.adv.cra.service.CorrespondenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/correspondentes")
@RequiredArgsConstructor
public class CorrespondenteController {
    
    private final CorrespondenteService correspondenteService;
    
    @PostMapping
    public ResponseEntity<Correspondente> criar(@Valid @RequestBody Correspondente correspondente) {
        try {
            if (correspondente.getCpfCnpj() != null && 
                correspondenteService.existeCpfCnpj(correspondente.getCpfCnpj())) {
                return ResponseEntity.badRequest().build();
            }
            
            if (correspondente.getOab() != null && 
                correspondenteService.existeOab(correspondente.getOab())) {
                return ResponseEntity.badRequest().build();
            }
            
            Correspondente novoCorrespondente = correspondenteService.salvar(correspondente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCorrespondente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Correspondente> atualizar(@PathVariable Long id, @Valid @RequestBody Correspondente correspondente) {
        try {
            if (!correspondenteService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            if (correspondente.getCpfCnpj() != null && 
                correspondenteService.existeCpfCnpjParaOutroCorrespondente(correspondente.getCpfCnpj(), id)) {
                return ResponseEntity.badRequest().build();
            }
            
            if (correspondente.getOab() != null && 
                correspondenteService.existeOabParaOutroCorrespondente(correspondente.getOab(), id)) {
                return ResponseEntity.badRequest().build();
            }
            
            correspondente.setId(id);
            Correspondente correspondenteAtualizado = correspondenteService.atualizar(correspondente);
            return ResponseEntity.ok(correspondenteAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Correspondente>> listarTodos() {
        try {
            List<Correspondente> correspondentes = correspondenteService.listarTodos();
            return ResponseEntity.ok(correspondentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Correspondente> buscarPorId(@PathVariable Long id) {
        try {
            return correspondenteService.buscarPorId(id)
                    .map(correspondente -> ResponseEntity.ok(correspondente))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/ativos")
    public ResponseEntity<List<Correspondente>> listarAtivos() {
        try {
            List<Correspondente> correspondentes = correspondenteService.listarAtivos();
            return ResponseEntity.ok(correspondentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Correspondente>> buscarPorNome(@RequestParam String nome) {
        try {
            List<Correspondente> correspondentes = correspondenteService.buscarPorNome(nome);
            return ResponseEntity.ok(correspondentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/cpfcnpj/{cpfCnpj}")
    public ResponseEntity<Correspondente> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        try {
            return correspondenteService.buscarPorCpfCnpj(cpfCnpj)
                    .map(correspondente -> ResponseEntity.ok(correspondente))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/oab/{oab}")
    public ResponseEntity<Correspondente> buscarPorOab(@PathVariable String oab) {
        try {
            return correspondenteService.buscarPorOab(oab)
                    .map(correspondente -> ResponseEntity.ok(correspondente))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/tipo/{tipo}")
    public ResponseEntity<List<Correspondente>> buscarPorTipo(@PathVariable String tipo) {
        try {
            List<Correspondente> correspondentes = correspondenteService.buscarPorTipo(tipo);
            return ResponseEntity.ok(correspondentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/regra1")
    public ResponseEntity<List<Correspondente>> listarComRegra1() {
        try {
            List<Correspondente> correspondentes = correspondenteService.listarComRegra1();
            return ResponseEntity.ok(correspondentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/regra2")
    public ResponseEntity<List<Correspondente>> listarComRegra2() {
        try {
            List<Correspondente> correspondentes = correspondenteService.listarComRegra2();
            return ResponseEntity.ok(correspondentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        try {
            correspondenteService.inativar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        try {
            correspondenteService.ativar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            correspondenteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
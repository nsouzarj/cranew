package br.adv.cra.controller;

import br.adv.cra.entity.Solicitacao;
import br.adv.cra.service.SolicitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SolicitacaoController {
    
    private final SolicitacaoService solicitacaoService;
    
    @PostMapping
    public ResponseEntity<Solicitacao> criar(@Valid @RequestBody Solicitacao solicitacao) {
        try {
            Solicitacao novaSolicitacao = solicitacaoService.salvar(solicitacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaSolicitacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Solicitacao> atualizar(@PathVariable Long id, @Valid @RequestBody Solicitacao solicitacao) {
        try {
            if (!solicitacaoService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            solicitacao.setId(id);
            Solicitacao solicitacaoAtualizada = solicitacaoService.atualizar(solicitacao);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Solicitacao>> listarTodas() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarTodas();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
        try {
            return solicitacaoService.buscarPorId(id)
                    .map(solicitacao -> ResponseEntity.ok(solicitacao))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/pendentes")
    public ResponseEntity<List<Solicitacao>> listarPendentes() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarPendentes();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/concluidas")
    public ResponseEntity<List<Solicitacao>> listarConcluidas() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarConcluidas();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/atrasadas")
    public ResponseEntity<List<Solicitacao>> listarAtrasadas() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarAtrasadas();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/pagas")
    public ResponseEntity<List<Solicitacao>> listarPagas() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarPagas();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/nao-pagas")
    public ResponseEntity<List<Solicitacao>> listarNaoPagas() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarNaoPagas();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Solicitacao>> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            // Note: In a real implementation, you would fetch the Usuario first
            // This is simplified for demonstration
            List<Solicitacao> solicitacoes = solicitacaoService.listarTodas()
                    .stream()
                    .filter(s -> s.getUsuario() != null && s.getUsuario().getId().equals(usuarioId))
                    .toList();
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/periodo")
    public ResponseEntity<List<Solicitacao>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.buscarPorPeriodo(inicio, fim);
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/texto")
    public ResponseEntity<List<Solicitacao>> buscarPorTexto(@RequestParam String texto) {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.buscarPorTexto(texto);
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/grupo/{grupo}")
    public ResponseEntity<List<Solicitacao>> buscarPorGrupo(@PathVariable String grupo) {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.buscarPorGrupo(grupo);
            return ResponseEntity.ok(solicitacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/concluir")
    public ResponseEntity<Solicitacao> concluir(@PathVariable Long id, @RequestBody(required = false) String observacao) {
        try {
            Solicitacao solicitacao = solicitacaoService.concluir(id, observacao);
            return ResponseEntity.ok(solicitacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/marcar-pago")
    public ResponseEntity<Solicitacao> marcarComoPago(@PathVariable Long id) {
        try {
            Solicitacao solicitacao = solicitacaoService.marcarComoPago(id);
            return ResponseEntity.ok(solicitacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/marcar-nao-pago")
    public ResponseEntity<Solicitacao> marcarComoNaoPago(@PathVariable Long id) {
        try {
            Solicitacao solicitacao = solicitacaoService.marcarComoNaoPago(id);
            return ResponseEntity.ok(solicitacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/estatisticas/pendentes")
    public ResponseEntity<Long> contarPendentes() {
        try {
            Long count = solicitacaoService.contarPendentes();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            solicitacaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
package br.adv.cra.controller;

import br.adv.cra.entity.Processo;
import br.adv.cra.service.ProcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos")
@RequiredArgsConstructor
public class ProcessoController {
    
    private final ProcessoService processoService;
    
    @PostMapping
    public ResponseEntity<Processo> criar(@Valid @RequestBody Processo processo) {
        try {
            if (processoService.existeNumeroProcesso(processo.getNumeroProcesso())) {
                return ResponseEntity.badRequest().build();
            }
            Processo novoProcesso = processoService.salvar(processo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizar(@PathVariable Long id, @Valid @RequestBody Processo processo) {
        try {
            if (!processoService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            if (processoService.existeNumeroProcessoParaOutroProcesso(processo.getNumeroProcesso(), id)) {
                return ResponseEntity.badRequest().build();
            }
            
            processo.setId(id);
            Processo processoAtualizado = processoService.atualizar(processo);
            return ResponseEntity.ok(processoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Processo>> listarTodos() {
        try {
            List<Processo> processos = processoService.listarTodos();
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscarPorId(@PathVariable Long id) {
        try {
            return processoService.buscarPorId(id)
                    .map(processo -> ResponseEntity.ok(processo))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/numero/{numeroProcesso}")
    public ResponseEntity<Processo> buscarPorNumeroProcesso(@PathVariable String numeroProcesso) {
        try {
            return processoService.buscarPorNumeroProcesso(numeroProcesso)
                    .map(processo -> ResponseEntity.ok(processo))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/numero-pesquisa")
    public ResponseEntity<List<Processo>> buscarPorNumeroProcessoPesquisa(@RequestParam String numero) {
        try {
            List<Processo> processos = processoService.buscarPorNumeroProcessoPesquisa(numero);
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/parte")
    public ResponseEntity<List<Processo>> buscarPorParte(@RequestParam String parte) {
        try {
            List<Processo> processos = processoService.buscarPorParte(parte);
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/adverso")
    public ResponseEntity<List<Processo>> buscarPorAdverso(@RequestParam String adverso) {
        try {
            List<Processo> processos = processoService.buscarPorAdverso(adverso);
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/status/{status}")
    public ResponseEntity<List<Processo>> buscarPorStatus(@PathVariable String status) {
        try {
            List<Processo> processos = processoService.buscarPorStatus(status);
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/assunto")
    public ResponseEntity<List<Processo>> buscarPorAssunto(@RequestParam String assunto) {
        try {
            List<Processo> processos = processoService.buscarPorAssunto(assunto);
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/comarca/{comarcaId}")
    public ResponseEntity<List<Processo>> buscarPorComarca(@PathVariable Long comarcaId) {
        try {
            // Note: In a real implementation, you would fetch the Comarca first
            // This is simplified for demonstration
            List<Processo> processos = processoService.listarTodos()
                    .stream()
                    .filter(p -> p.getComarca() != null && p.getComarca().getId().equals(comarcaId))
                    .toList();
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar/orgao/{orgaoId}")
    public ResponseEntity<List<Processo>> buscarPorOrgao(@PathVariable Long orgaoId) {
        try {
            // Note: In a real implementation, you would fetch the Orgao first
            // This is simplified for demonstration
            List<Processo> processos = processoService.listarTodos()
                    .stream()
                    .filter(p -> p.getOrgao() != null && p.getOrgao().getId().equals(orgaoId))
                    .toList();
            return ResponseEntity.ok(processos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/estatisticas/status/{status}")
    public ResponseEntity<Long> contarPorStatus(@PathVariable String status) {
        try {
            Long count = processoService.contarPorStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            processoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
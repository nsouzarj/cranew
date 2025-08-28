package br.adv.cra.service;

import br.adv.cra.entity.Correspondente;
import br.adv.cra.repository.CorrespondenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CorrespondenteService {
    
    private final CorrespondenteRepository correspondenteRepository;
    
    public Correspondente salvar(Correspondente correspondente) {
        if (correspondente.getDataCadastro() == null) {
            correspondente.setDataCadastro(LocalDateTime.now());
        }
        if (correspondente.getAtivo() == null) {
            correspondente.setAtivo(true);
        }
        return correspondenteRepository.save(correspondente);
    }
    
    public Correspondente atualizar(Correspondente correspondente) {
        if (!correspondenteRepository.existsById(correspondente.getId())) {
            throw new RuntimeException("Correspondente não encontrado");
        }
        return correspondenteRepository.save(correspondente);
    }
    
    public void deletar(Long id) {
        if (!correspondenteRepository.existsById(id)) {
            throw new RuntimeException("Correspondente não encontrado");
        }
        correspondenteRepository.deleteById(id);
    }
    
    public void inativar(Long id) {
        Correspondente correspondente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Correspondente não encontrado"));
        correspondente.setAtivo(false);
        correspondenteRepository.save(correspondente);
    }
    
    public void ativar(Long id) {
        Correspondente correspondente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Correspondente não encontrado"));
        correspondente.setAtivo(true);
        correspondenteRepository.save(correspondente);
    }
    
    @Transactional(readOnly = true)
    public Optional<Correspondente> buscarPorId(Long id) {
        return correspondenteRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> listarTodos() {
        return correspondenteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> listarAtivos() {
        return correspondenteRepository.findByAtivoTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> listarInativos() {
        return correspondenteRepository.findByAtivoFalse();
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> buscarPorNome(String nome) {
        return correspondenteRepository.findByNomeContaining(nome);
    }
    
    @Transactional(readOnly = true)
    public Optional<Correspondente> buscarPorCpfCnpj(String cpfCnpj) {
        return correspondenteRepository.findByCpfCnpj(cpfCnpj);
    }
    
    @Transactional(readOnly = true)
    public Optional<Correspondente> buscarPorOab(String oab) {
        return correspondenteRepository.findByOab(oab);
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> buscarPorTipo(String tipo) {
        return correspondenteRepository.findByTipoCorrespondente(tipo);
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> buscarPorEmail(String email) {
        return correspondenteRepository.findByAnyEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> listarComRegra1() {
        return correspondenteRepository.findByAplicaRegra1True();
    }
    
    @Transactional(readOnly = true)
    public List<Correspondente> listarComRegra2() {
        return correspondenteRepository.findByAplicaRegra2True();
    }
    
    @Transactional(readOnly = true)
    public boolean existeCpfCnpj(String cpfCnpj) {
        return correspondenteRepository.existsByCpfCnpj(cpfCnpj);
    }
    
    @Transactional(readOnly = true)
    public boolean existeOab(String oab) {
        return correspondenteRepository.existsByOab(oab);
    }
    
    @Transactional(readOnly = true)
    public boolean existeCpfCnpjParaOutroCorrespondente(String cpfCnpj, Long id) {
        Optional<Correspondente> correspondente = correspondenteRepository.findByCpfCnpj(cpfCnpj);
        return correspondente.isPresent() && !correspondente.get().getId().equals(id);
    }
    
    @Transactional(readOnly = true)
    public boolean existeOabParaOutroCorrespondente(String oab, Long id) {
        Optional<Correspondente> correspondente = correspondenteRepository.findByOab(oab);
        return correspondente.isPresent() && !correspondente.get().getId().equals(id);
    }
}
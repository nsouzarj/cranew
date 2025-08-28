package br.adv.cra.repository;

import br.adv.cra.entity.Comarca;
import br.adv.cra.entity.Processo;
import br.adv.cra.entity.Solicitacao;
import br.adv.cra.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    
    List<Solicitacao> findByUsuario(Usuario usuario);
    
    List<Solicitacao> findByProcesso(Processo processo);
    
    List<Solicitacao> findByComarca(Comarca comarca);
    
    @Query("SELECT s FROM Solicitacao s WHERE s.dataSolicitacao BETWEEN :inicio AND :fim")
    List<Solicitacao> findByDataSolicitacaoBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    @Query("SELECT s FROM Solicitacao s WHERE s.dataConclusao IS NULL")
    List<Solicitacao> findPendentes();
    
    @Query("SELECT s FROM Solicitacao s WHERE s.dataConclusao IS NOT NULL")
    List<Solicitacao> findConcluidas();
    
    List<Solicitacao> findByPagoTrue();
    
    List<Solicitacao> findByPagoFalse();
    
    @Query("SELECT s FROM Solicitacao s WHERE s.dataPrazo < :data AND s.dataConclusao IS NULL")
    List<Solicitacao> findAtrasadas(@Param("data") LocalDateTime data);
    
    @Query("SELECT s FROM Solicitacao s WHERE s.observacao LIKE %:texto% OR s.instrucoes LIKE %:texto%")
    List<Solicitacao> findByTextoContaining(@Param("texto") String texto);
    
    List<Solicitacao> findByGrupo(String grupo);
    
    List<Solicitacao> findByStatusExterno(String statusExterno);
    
    @Query("SELECT COUNT(s) FROM Solicitacao s WHERE s.usuario = :usuario")
    Long countByUsuario(@Param("usuario") Usuario usuario);
    
    @Query("SELECT COUNT(s) FROM Solicitacao s WHERE s.dataConclusao IS NULL")
    Long countPendentes();
}
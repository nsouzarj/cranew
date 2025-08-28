package br.adv.cra.repository;

import br.adv.cra.entity.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {
    
    @Query("SELECT o FROM Orgao o WHERE o.descricao LIKE %:descricao%")
    List<Orgao> findByDescricaoContaining(@Param("descricao") String descricao);
    
    @Query("SELECT o FROM Orgao o ORDER BY o.descricao")
    List<Orgao> findAllOrderByDescricao();
}
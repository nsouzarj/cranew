package br.adv.cra.repository;

import br.adv.cra.entity.Correspondente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CorrespondenteRepository extends JpaRepository<Correspondente, Long> {
    
    List<Correspondente> findByAtivoTrue();
    
    List<Correspondente> findByAtivoFalse();
    
    @Query("SELECT c FROM Correspondente c WHERE c.nome LIKE %:nome%")
    List<Correspondente> findByNomeContaining(@Param("nome") String nome);
    
    Optional<Correspondente> findByCpfCnpj(String cpfCnpj);
    
    Optional<Correspondente> findByOab(String oab);
    
    List<Correspondente> findByTipoCorrespondente(String tipoCorrespondente);
    
    @Query("SELECT c FROM Correspondente c WHERE c.emailPrimario = :email OR c.emailSecundario = :email")
    List<Correspondente> findByAnyEmail(@Param("email") String email);
    
    List<Correspondente> findByAplicaRegra1True();
    
    List<Correspondente> findByAplicaRegra2True();
    
    boolean existsByCpfCnpj(String cpfCnpj);
    
    boolean existsByOab(String oab);
}
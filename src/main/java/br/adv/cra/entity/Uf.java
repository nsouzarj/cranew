package br.adv.cra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "uf")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Uf implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduf")
    private Long id;
    
    @NotBlank(message = "Sigla é obrigatória")
    @Size(max = 2, message = "Sigla deve ter no máximo 2 caracteres")
    @Column(name = "sigla", length = 2, nullable = false, unique = true)
    private String sigla;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 60, message = "Nome deve ter no máximo 60 caracteres")
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;
}
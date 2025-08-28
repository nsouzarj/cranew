package br.adv.cra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idendereco")
    private Long id;
    
    @NotBlank(message = "Logradouro é obrigatório")
    @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
    @Column(name = "logradouro", nullable = false)
    private String logradouro;
    
    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    @Column(name = "bairro", nullable = false)
    private String bairro;
    
    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Column(name = "cidade", nullable = false)
    private String cidade;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uf_id", nullable = false)
    private Uf uf;
    
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve ter formato válido (00000-000)")
    @Column(name = "cep")
    private String cep;
    
    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    @Column(name = "observacao", length = 500)
    private String observacao;
}
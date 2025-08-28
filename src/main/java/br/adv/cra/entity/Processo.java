package br.adv.cra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "processo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprocesso")
    private Long id;
    
    @NotBlank(message = "Número do processo é obrigatório")
    @Size(max = 50, message = "Número do processo deve ter no máximo 50 caracteres")
    @Column(name = "numeroprocesso", nullable = false)
    private String numeroProcesso;
    
    @Size(max = 50, message = "Número do processo para pesquisa deve ter no máximo 50 caracteres")
    @Column(name = "numeroprocessopesq")
    private String numeroProcessoPesq;
    
    @Size(max = 255, message = "Parte deve ter no máximo 255 caracteres")
    @Column(name = "parte")
    private String parte;
    
    @Size(max = 255, message = "Adverso deve ter no máximo 255 caracteres")
    @Column(name = "adverso")
    private String adverso;
    
    @Size(max = 100, message = "Posição deve ter no máximo 100 caracteres")
    @Column(name = "posicao")
    private String posicao;
    
    @Size(max = 50, message = "Status deve ter no máximo 50 caracteres")
    @Column(name = "status")
    private String status;
    
    @Size(max = 100, message = "Cartório deve ter no máximo 100 caracteres")
    @Column(name = "cartorio")
    private String cartorio;
    
    @Size(max = 600, message = "Assunto deve ter no máximo 600 caracteres")
    @Column(name = "assunto", length = 600, columnDefinition = "TEXT")
    private String assunto;
    
    @Size(max = 255, message = "Localização deve ter no máximo 255 caracteres")
    @Column(name = "localizacao")
    private String localizacao;
    
    @Size(max = 50, message = "Número de integração deve ter no máximo 50 caracteres")
    @Column(name = "numerointegracao")
    private String numeroIntegracao;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comarca_id")
    private Comarca comarca;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orgao_id")
    private Orgao orgao;
    
    @Column(name = "numorgao")
    private Integer numeroOrgao;
    
    @Size(max = 20, message = "Processo eletrônico deve ter no máximo 20 caracteres")
    @Column(name = "proceletronico")
    private String processoEletronico;
    
    @Column(name = "quantsoli")
    private Integer quantidadeSolicitacoes;
    
    @Column(name = "totalfeita")
    private Integer totalFeita;
}
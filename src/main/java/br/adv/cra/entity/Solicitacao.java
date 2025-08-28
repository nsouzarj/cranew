package br.adv.cra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitacao implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsolicitacao")
    private Long id;
    
    @Column(name = "referenciasolicitacao")
    private Integer referenciaSolicitacao;
    
    @NotNull(message = "Data da solicitação é obrigatória")
    @Column(name = "datasolictacao", nullable = false)
    private LocalDateTime dataSolicitacao;
    
    @Column(name = "dataconclusao")
    private LocalDateTime dataConclusao;
    
    @Column(name = "dataprazo")
    private LocalDateTime dataPrazo;
    
    @Size(max = 600, message = "Observação deve ter no máximo 600 caracteres")
    @Column(name = "observacao", length = 600, columnDefinition = "TEXT")
    private String observacao;
    
    @Size(max = 600, message = "Instruções devem ter no máximo 600 caracteres")
    @Column(name = "instrucoes", length = 600, columnDefinition = "TEXT")
    private String instrucoes;
    
    @Size(max = 600, message = "Complemento deve ter no máximo 600 caracteres")
    @Column(name = "complemento", length = 600, columnDefinition = "TEXT")
    private String complemento;
    
    @Size(max = 600, message = "Justificativa deve ter no máximo 600 caracteres")
    @Column(name = "justificativa", length = 600, columnDefinition = "TEXT")
    private String justificativa;
    
    @Size(max = 600, message = "Tratamento pós audiência deve ter no máximo 600 caracteres")
    @Column(name = "tratposaudiencia", length = 600, columnDefinition = "TEXT")
    private String tratamentoPosAudiencia;
    
    @Size(max = 50, message = "Número de controle deve ter no máximo 50 caracteres")
    @Column(name = "numcontrole")
    private String numeroControle;
    
    @Column(name = "tempreposto")
    private Boolean temPreposto = false;
    
    @Column(name = "convolada")
    private Boolean convolada = false;
    
    @Column(name = "horaudiencia")
    private LocalDateTime horaAudiencia;
    
    @Size(max = 255, message = "Status externo deve ter no máximo 255 caracteres")
    @Column(name = "statusexterno")
    private String statusExterno;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id")
    private Processo processo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comarca_id")
    private Comarca comarca;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column(name = "valordaalcada", precision = 10, scale = 2)
    private BigDecimal valorDaAlcada;
    
    @Size(max = 255, message = "Email de envio deve ter no máximo 255 caracteres")
    @Column(name = "emailenvio")
    private String emailEnvio;
    
    @Column(name = "pago")
    private Boolean pago = false;
    
    @Size(max = 100, message = "Grupo deve ter no máximo 100 caracteres")
    @Column(name = "grupo")
    private String grupo;
    
    @Column(name = "propostaacordo")
    private Boolean propostaAcordo = false;
    
    @Column(name = "audinterna")
    private Boolean auditoriaInterna = false;
    
    @Size(max = 500, message = "Lide deve ter no máximo 500 caracteres")
    @Column(name = "lide")
    private String lide;
    
    @Column(name = "avaliacaonota")
    private Integer avaliacaoNota;
    
    @Size(max = 1000, message = "Texto de avaliação deve ter no máximo 1000 caracteres")
    @Column(name = "textoavaliacao", length = 1000)
    private String textoAvaliacao;
    
    @PrePersist
    protected void onCreate() {
        if (dataSolicitacao == null) {
            dataSolicitacao = LocalDateTime.now();
        }
        if (temPreposto == null) {
            temPreposto = false;
        }
        if (convolada == null) {
            convolada = false;
        }
        if (pago == null) {
            pago = false;
        }
        if (propostaAcordo == null) {
            propostaAcordo = false;
        }
        if (auditoriaInterna == null) {
            auditoriaInterna = false;
        }
    }
}
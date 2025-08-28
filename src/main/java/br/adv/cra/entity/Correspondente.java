package br.adv.cra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "correspondente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Correspondente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcorrespondente")
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Size(max = 255, message = "Responsável deve ter no máximo 255 caracteres")
    @Column(name = "responsavel")
    private String responsavel;
    
    @Size(max = 20, message = "CPF/CNPJ deve ter no máximo 20 caracteres")
    @Column(name = "cpfcnpj")
    private String cpfCnpj;
    
    @Size(max = 20, message = "OAB deve ter no máximo 20 caracteres")
    @Column(name = "oab")
    private String oab;
    
    @Size(max = 50, message = "Tipo correspondente deve ter no máximo 50 caracteres")
    @Column(name = "tipocorrepondente")
    private String tipoCorrespondente;
    
    @Size(max = 20, message = "Telefone primário deve ter no máximo 20 caracteres")
    @Column(name = "telefoneprimario")
    private String telefonePrimario;
    
    @Size(max = 20, message = "Telefone secundário deve ter no máximo 20 caracteres")
    @Column(name = "telefonesecundario")
    private String telefoneSecundario;
    
    @Size(max = 20, message = "Celular primário deve ter no máximo 20 caracteres")
    @Column(name = "telefonecelularprimario")
    private String telefonecelularPrimario;
    
    @Size(max = 20, message = "Celular secundário deve ter no máximo 20 caracteres")
    @Column(name = "telefonecelularsecundario")
    private String telefonecelularSecundario;
    
    @Email(message = "Email primário deve ser válido")
    @Column(name = "emailprimario")
    private String emailPrimario;
    
    @Email(message = "Email secundário deve ser válido")
    @Column(name = "emailsecundario")
    private String emailSecundario;
    
    @Column(name = "datacadastro")
    private LocalDateTime dataCadastro;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Size(max = 1000, message = "Observação deve ter no máximo 1000 caracteres")
    @Column(name = "observacao", length = 1000)
    private String observacao;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    
    @Column(name = "aplicaregra1")
    private Boolean aplicaRegra1 = false;
    
    @Column(name = "aplicaregra2")
    private Boolean aplicaRegra2 = false;
    
    @PrePersist
    protected void onCreate() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
        if (ativo == null) {
            ativo = true;
        }
        if (aplicaRegra1 == null) {
            aplicaRegra1 = false;
        }
        if (aplicaRegra2 == null) {
            aplicaRegra2 = false;
        }
    }
}
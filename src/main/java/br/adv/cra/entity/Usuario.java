package br.adv.cra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable, UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long id;
    
    @NotBlank(message = "Login é obrigatório")
    @Column(name = "login", unique = true, nullable = false)
    private String login;
    
    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "senha", nullable = false)
    private String senha;
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Column(name = "nomecompleto", nullable = false)
    private String nomeCompleto;
    
    @Email(message = "Email principal deve ser válido")
    @Column(name = "emailprincipal")
    private String emailPrincipal;
    
    @Email(message = "Email secundário deve ser válido")
    @Column(name = "emailsecundario")
    private String emailSecundario;
    
    @Email(message = "Email responsável deve ser válido")
    @Column(name = "emailresponsavel")
    private String emailResponsavel;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correspondente_id")
    private Correspondente correspondente;
    
    @NotNull(message = "Tipo é obrigatório")
    @Column(name = "tipo", nullable = false)
    private Integer tipo; // 1-Admin, 2-Advogado, 3-Correspondente
    
    @Column(name = "dataentrada")
    private LocalDateTime dataEntrada;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @PrePersist
    protected void onCreate() {
        if (dataEntrada == null) {
            dataEntrada = LocalDateTime.now();
        }
        if (ativo == null) {
            ativo = true;
        }
    }
    
    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch (tipo) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_ADVOGADO";
            case 3 -> "ROLE_CORRESPONDENTE";
            default -> "ROLE_USER";
        };
        return List.of(new SimpleGrantedAuthority(role));
    }
    
    @Override
    public String getPassword() {
        return senha;
    }
    
    @Override
    public String getUsername() {
        return login;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return ativo != null && ativo;
    }
}
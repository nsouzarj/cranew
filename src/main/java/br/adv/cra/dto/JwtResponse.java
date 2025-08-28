package br.adv.cra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String login;
    private String nomeCompleto;
    private String emailPrincipal;
    private Integer tipo;
    private List<String> roles;
    private LocalDateTime expiresAt;
    
    public JwtResponse(String token, String refreshToken, Long id, String login, 
                       String nomeCompleto, String emailPrincipal, Integer tipo, 
                       List<String> roles, LocalDateTime expiresAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.login = login;
        this.nomeCompleto = nomeCompleto;
        this.emailPrincipal = emailPrincipal;
        this.tipo = tipo;
        this.roles = roles;
        this.expiresAt = expiresAt;
    }
}
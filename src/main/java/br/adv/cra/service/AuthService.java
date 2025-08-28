package br.adv.cra.service;

import br.adv.cra.dto.JwtResponse;
import br.adv.cra.dto.LoginRequest;
import br.adv.cra.dto.RefreshTokenRequest;
import br.adv.cra.dto.RegisterRequest;
import br.adv.cra.entity.Usuario;
import br.adv.cra.repository.UsuarioRepository;
import br.adv.cra.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    
    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha())
        );
        
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(usuario);
        String refreshToken = jwtUtils.generateRefreshToken(usuario.getUsername());
        
        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        LocalDateTime expiresAt = jwtUtils.getExpirationDateFromToken(jwt)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        log.info("User {} authenticated successfully", usuario.getLogin());
        
        return new JwtResponse(
                jwt,
                refreshToken,
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNomeCompleto(),
                usuario.getEmailPrincipal(),
                usuario.getTipo(),
                roles,
                expiresAt
        );
    }
    
    public JwtResponse register(RegisterRequest registerRequest) {
        if (usuarioRepository.existsByLogin(registerRequest.getLogin())) {
            throw new RuntimeException("Erro: Login já está em uso!");
        }
        
        Usuario usuario = new Usuario();
        usuario.setLogin(registerRequest.getLogin());
        usuario.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
        usuario.setNomeCompleto(registerRequest.getNomeCompleto());
        usuario.setEmailPrincipal(registerRequest.getEmailPrincipal());
        usuario.setEmailSecundario(registerRequest.getEmailSecundario());
        usuario.setEmailResponsavel(registerRequest.getEmailResponsavel());
        usuario.setTipo(registerRequest.getTipo());
        usuario.setAtivo(true);
        usuario.setDataEntrada(LocalDateTime.now());
        
        usuario = usuarioRepository.save(usuario);
        
        String jwt = jwtUtils.generateJwtToken(usuario);
        String refreshToken = jwtUtils.generateRefreshToken(usuario.getUsername());
        
        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        LocalDateTime expiresAt = jwtUtils.getExpirationDateFromToken(jwt)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        log.info("User {} registered successfully", usuario.getLogin());
        
        return new JwtResponse(
                jwt,
                refreshToken,
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNomeCompleto(),
                usuario.getEmailPrincipal(),
                usuario.getTipo(),
                roles,
                expiresAt
        );
    }
    
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        
        if (!jwtUtils.validateJwtToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido!");
        }
        
        if (!jwtUtils.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Token fornecido não é um refresh token!");
        }
        
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        if (!usuario.isEnabled()) {
            throw new RuntimeException("Usuário está inativo!");
        }
        
        String newJwt = jwtUtils.generateJwtToken(usuario);
        String newRefreshToken = jwtUtils.generateRefreshToken(usuario.getUsername());
        
        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        LocalDateTime expiresAt = jwtUtils.getExpirationDateFromToken(newJwt)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        log.info("Token refreshed for user {}", usuario.getLogin());
        
        return new JwtResponse(
                newJwt,
                newRefreshToken,
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNomeCompleto(),
                usuario.getEmailPrincipal(),
                usuario.getTipo(),
                roles,
                expiresAt
        );
    }
    
    @Transactional(readOnly = true)
    public Usuario getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado!");
        }
        
        Usuario usuario = (Usuario) authentication.getPrincipal();
        // Reload from database to get fresh data
        return usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }
    
    @Transactional(readOnly = true)
    public Map<String, Object> testPasswordHash(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Usuario usuario = usuarioRepository.findByLogin(username)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
            
            String storedHash = usuario.getSenha();
            boolean matches = passwordEncoder.matches(password, storedHash);
            
            result.put("username", username);
            result.put("passwordProvided", password);
            result.put("storedHashPrefix", storedHash.substring(0, Math.min(20, storedHash.length())) + "...");
            result.put("passwordMatches", matches);
            result.put("userActive", usuario.getAtivo());
            result.put("userType", usuario.getTipo());
            result.put("hashLength", storedHash.length());
            result.put("hashValidFormat", storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$"));
            
            if (matches) {
                result.put("status", "SUCCESS");
                result.put("message", "Password matches stored hash");
            } else {
                result.put("status", "FAILED");
                result.put("message", "Password does not match stored hash");
            }
            
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
        }
        
        return result;
    }
    
    @Transactional(readOnly = true)
    public Map<String, Object> debugJwtGeneration(String username) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Generate a test JWT token
            String testToken = jwtUtils.generateTokenFromUsername(username);
            
            // Analyze the token structure
            String[] tokenParts = testToken.split("\\.");
            
            result.put("username", username);
            result.put("tokenGenerated", true);
            result.put("tokenParts", tokenParts.length);
            result.put("expectedParts", 3);
            result.put("token", testToken);
            result.put("tokenLength", testToken.length());
            
            if (tokenParts.length == 3) {
                result.put("header", tokenParts[0]);
                result.put("payload", tokenParts[1]);
                result.put("signature", tokenParts[2]);
                result.put("status", "SUCCESS");
                
                // Test token validation
                boolean isValid = jwtUtils.validateJwtToken(testToken);
                result.put("tokenValid", isValid);
                
                if (isValid) {
                    try {
                        String extractedUsername = jwtUtils.getUserNameFromJwtToken(testToken);
                        result.put("extractedUsername", extractedUsername);
                        result.put("usernameMatches", username.equals(extractedUsername));
                    } catch (Exception e) {
                        result.put("extractionError", e.getMessage());
                    }
                }
            } else {
                result.put("status", "ERROR");
                result.put("message", "Token has " + tokenParts.length + " parts instead of 3");
            }
            
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            result.put("exceptionClass", e.getClass().getSimpleName());
        }
        
        return result;
    }
}
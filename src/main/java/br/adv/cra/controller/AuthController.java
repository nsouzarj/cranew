package br.adv.cra.controller;

import br.adv.cra.dto.JwtResponse;
import br.adv.cra.dto.LoginRequest;
import br.adv.cra.dto.RefreshTokenRequest;
import br.adv.cra.dto.RegisterRequest;
import br.adv.cra.entity.Usuario;
import br.adv.cra.service.AuthService;
import br.adv.cra.service.DatabaseConnectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final DatabaseConnectionService databaseConnectionService;
    
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.authenticate(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciais inválidas");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            JwtResponse jwtResponse = authService.register(registerRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro no registro");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping(value = "/refresh", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            JwtResponse jwtResponse = authService.refreshToken(request);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Token inválido");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            Usuario usuario = authService.getCurrentUser(authentication);
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", usuario.getId());
            userInfo.put("login", usuario.getLogin());
            userInfo.put("nomeCompleto", usuario.getNomeCompleto());
            userInfo.put("emailPrincipal", usuario.getEmailPrincipal());
            userInfo.put("emailSecundario", usuario.getEmailSecundario());
            userInfo.put("emailResponsavel", usuario.getEmailResponsavel());
            userInfo.put("tipo", usuario.getTipo());
            userInfo.put("ativo", usuario.getAtivo());
            userInfo.put("dataEntrada", usuario.getDataEntrada());
            userInfo.put("authorities", usuario.getAuthorities());
            
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Usuário não encontrado");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<?> logoutUser() {
        // In a JWT stateless setup, logout is typically handled client-side
        // by removing the token from storage. However, we can provide a 
        // confirmation endpoint.
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout realizado com sucesso");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<?> validateToken(Authentication authentication) {
        try {
            Usuario usuario = authService.getCurrentUser(authentication);
            
            Map<String, Object> validation = new HashMap<>();
            validation.put("valid", true);
            validation.put("user", usuario.getLogin());
            validation.put("authorities", usuario.getAuthorities());
            
            return ResponseEntity.ok(validation);
        } catch (Exception e) {
            Map<String, Object> validation = new HashMap<>();
            validation.put("valid", false);
            validation.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(validation);
        }
    }
    
    @GetMapping(value = "/database-info", produces = "application/json")
    public ResponseEntity<?> getDatabaseInfo() {
        try {
            Map<String, Object> dbInfo = databaseConnectionService.getDatabaseInfo();
            return ResponseEntity.ok(dbInfo);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Database connection failed");
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    @PostMapping(value = "/test-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> testPassword(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            
            Map<String, Object> result = authService.testPasswordHash(username, password);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Test failed");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping(value = "/debug-jwt", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> debugJwt(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            if (username == null || username.trim().isEmpty()) {
                username = "testuser";
            }
            
            Map<String, Object> result = authService.debugJwtGeneration(username);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "JWT debug failed");
            error.put("message", e.getMessage());
            error.put("stackTrace", e.getStackTrace()[0].toString());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
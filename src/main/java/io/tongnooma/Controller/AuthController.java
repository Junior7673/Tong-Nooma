package io.tongnooma.Controller;

import io.tongnooma.Dto.AuthentificationRequest;
import io.tongnooma.Dto.AuthentificationResponse;
import io.tongnooma.Dto.RegisterRequest;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.UtilisateurJPARepository;
import io.tongnooma.Security.JwtService;
import io.tongnooma.Service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UtilisateurJPARepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /** Enregistrement d'un nouvel utilisateur */
    @PostMapping("/register")
    public ResponseEntity<AuthentificationResponse> register(@RequestBody RegisterRequest request) {
        UtilisateurJPAEntity user = UtilisateurJPAEntity.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .dateInscrip(request.getDateInscrip())
                .build();

        utilisateurRepository.save(user);

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));

        return ResponseEntity.ok(new AuthentificationResponse(jwtToken));
    }

    /** Connexion d'un utilisateur existant */
    @PostMapping("/login")
    public ResponseEntity<AuthentificationResponse> authenticate(@RequestBody AuthentificationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UtilisateurJPAEntity user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));

        return ResponseEntity.ok(new AuthentificationResponse(jwtToken));
    }
}

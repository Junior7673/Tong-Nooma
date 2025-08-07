package io.tongnooma.Config;

import io.tongnooma.Security.JwtAuthFilter;
import io.tongnooma.Service.UtilisateurService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UtilisateurService utilisateurService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/utilisateurs/**", "/enseignants/**", "/media/**", "/inscriptions/**", "/horaires/**", "/paiements/**","/actualite/**").permitAll() // <-- accessible sans token
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    /**
     *
     *     @Bean
     *     public SecurityFilterChain securityFilterChain(HttpSecurity http,
     *                                                    JwtAuthenticationFilter jwtAuthFilter) throws Exception {
     *         return http
     *                 .csrf(csrf -> csrf.disable())
     *                 .authorizeHttpRequests(auth -> auth
     *                         .requestMatchers("/auth/**").permitAll()
     *                         .requestMatchers("/produit/**", "/categorie/**", "/fournisseur/**", "/entree/**", "/sortie/**").permitAll() // <-- accessible sans token
     *                         .anyRequest().authenticated()
     *                 )
     *                 .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
     *                 .userDetailsService(utilisateurService)
     *                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
     *                 .build();
     *     }*/

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }// Pour hasher les mots de passe}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

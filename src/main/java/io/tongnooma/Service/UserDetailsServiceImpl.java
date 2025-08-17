package io.tongnooma.Service;

import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UtilisateurJPARepository utilisateurRepo;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UtilisateurJPAEntity utilisateur = utilisateurRepo.findByEmail(email)
                //.orElseThrow(() -> new UsernameNotFoundException("Email introuvable : " + email));
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec l’e-mail : " + email));

        return new CustomUserDetails(utilisateur);
    }
}

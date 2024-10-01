package kg.kutman.smanov.sumsarproject.wmsservice.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedUser implements OAuth2User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthday;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public static AuthorizedUser build(IntrospectionPrincipal principal) {
        if (principal == null) {
            return null;
        }
        List<GrantedAuthority> authorities = Collections.emptyList();
        if (principal.getAuthorities() != null) {
            authorities = principal.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        }
        return AuthorizedUser.builder()
            .id(principal.getId())
            .firstName(principal.getFirstName())
            .lastName(principal.getLastName())
            .middleName(principal.getMiddleName())
            .birthday(principal.getBirthday())
            .email(principal.getEmail())
            .authorities(authorities)
            .build();
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.email;
    }
}

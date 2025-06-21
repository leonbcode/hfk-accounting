package de.whka.hfk.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtAuthenticationConverter jwtConverter;

    public KeycloakJwtAuthenticationConverter() {
        jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        return jwtConverter.convert(jwt);
    }

    public static class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        private static final String ROLE_PREFIX = "ROLE_";

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> authorities = scopesConverter.convert(jwt);
            authorities.clear();
            authorities.addAll(extractResourceRoles(jwt));
            return authorities;
        }

        private Collection<SimpleGrantedAuthority> extractResourceRoles(Jwt jwt) {
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess == null) return Collections.emptyList();

            Map<String, Object> accountResource = (Map<String, Object>) resourceAccess.get("accounting");
            if (accountResource == null) return Collections.emptyList();

            List<String> roles = (List<String>) accountResource.get("roles");
            if(roles == null) return Collections.emptyList();
            return roles.stream().map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role)).toList();
        }
    }
}
